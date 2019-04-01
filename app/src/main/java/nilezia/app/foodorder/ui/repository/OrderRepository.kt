package nilezia.app.foodorder.ui.repository

//
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import nilezia.app.foodorder.http.CallbackHttp
import nilezia.app.foodorder.data.FoodItem
import nilezia.app.foodorder.data.HistoryItem
import nilezia.app.foodorder.data.UserInfo
import nilezia.app.foodorder.model.UserAuth
import nilezia.app.foodorder.ui.register.model.RegisterModel
import nilezia.app.foodorder.util.format
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class OrderRepository : OrderRepositoryContract {

    private var database = FirebaseDatabase.getInstance()
    private var mStorageRef: StorageReference? = FirebaseStorage.getInstance().reference
    private var myRef = database.getReference("food")
    private var hisRef = database.getReference("order-history")
    private var userHisRef = database.getReference("user-order")
    private var userRef = database.getReference("users")

    override fun requestOrderFromServer(callbackHttp: CallbackHttp<MutableList<FoodItem>>) {


    }

    override fun requestOrders(callbackHttp: CallbackHttp<MutableList<FoodItem>>) {
        requestOrderFromFirebase(callbackHttp)
    }

    override fun requestOrderFromFirebase(callbackHttp: CallbackHttp<MutableList<FoodItem>>) {

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val orders = mutableListOf<FoodItem>()

                dataSnapshot.children.mapNotNullTo(orders) {
                    it.getValue(FoodItem::class.java)
                }.forEach {
                    it.amount = 1
                    Log.d("FirebaseRead", "Value is: ${it.name}")

                }

                callbackHttp.onSuccess(orders)

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("FirebaseRead", "Failed to read value.", error.toException()!!)
            }
        })
    }

    override fun requestHistoryFromFirebase(callbackHttp: CallbackHttp<MutableList<HistoryItem>>) {
        val hisUser = userHisRef.child(FirebaseAuth.getInstance().currentUser?.uid!!)
        hisUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.w("FirebaseRead", "Failed to read value.", p0.toException())
                callbackHttp.onFailed(p0.toException().toString())

            }

            override fun onDataChange(p0: DataSnapshot) {
                val history = mutableListOf<HistoryItem>()

                p0.children.mapNotNullTo(history) {
                    it.getValue<HistoryItem>(HistoryItem::class.java)
                }
                callbackHttp.onSuccess(history)

            }
        })
    }

    override fun requestOrderFromLocal(callbackHttp: CallbackHttp<MutableList<FoodItem>>) {

        val orders = mutableListOf<FoodItem>()
        for (i in 0..6) {
            val order: FoodItem? = when (i) {
                0 -> FoodItem()
                1 -> FoodItem()
                2 -> FoodItem()
                3 -> FoodItem()
                4 -> FoodItem()
                5 -> FoodItem()
                else -> {
                    FoodItem()
                }

            }
            orders.add(order!!)
        }
        callbackHttp.onSuccess(orders)

    }

    override fun updateCartOrderToFirebase(listener: () -> Unit, cartOrders: MutableList<FoodItem>?) {
        createHistory(cartOrders)
        cartOrders?.forEach {
            it.quantity = it.quantity - it.amount
            myRef.child(it._id.toString()).child("quantity").setValue(it.quantity)
        }
        listener.invoke()
    }

    override fun addAccountToFirebase(listener: () -> Unit, registerModel: RegisterModel) {
        try {

            if (registerModel.imgPath.isNullOrEmpty()) {
                createAccountToDatabase(registerModel)
            } else {
                val folderRef: StorageReference? = mStorageRef?.child("profile")
                val file = Uri.fromFile(File(registerModel.imgPath))
                val imageRef = folderRef?.child(file.lastPathSegment)
                val uploadTask = imageRef?.putFile(file)
                val storageRef = mStorageRef?.child("profile/" + imageRef?.name)

                uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation storageRef?.downloadUrl
                })?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        registerModel.imageLink = downloadUri.toString()
                        createAccountToDatabase(registerModel)
                        listener.invoke()
                    }
                }
            }

        } catch (e: Exception) {

            Log.d("Exception", e.toString())
        }
    }

    override fun createAccountToDatabase(registerModel: RegisterModel) {
        val mUser = FirebaseAuth.getInstance().currentUser
        if (mUser != null) {
            val mapUser = HashMap<String, String>().apply {
                this["email"] = registerModel.userName
                this["avatar"] = registerModel.imageLink!!
                this["DisplayName"] = registerModel.displayName!!
            }
            userRef.child(mUser.uid).setValue(mapUser)
        }
    }

    override fun createAccountFromSocialToDatabase() {
        val mUser = FirebaseAuth.getInstance().currentUser
        if (mUser != null) {
            val mapUser = HashMap<String, String>().apply {
                this["email"] = mUser.email!!
                this["avatar"] = mUser.photoUrl.toString()
                this["DisplayName"] = mUser.displayName!!
            }
            userRef.child(mUser.uid).setValue(mapUser)
        }
    }

    override fun getUserInfo(listener: () -> Unit) {
        val userNode = userRef.child(FirebaseAuth.getInstance().currentUser?.uid!!)
        userNode.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("Exception", p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {

                val myUser = p0.getValue(UserInfo::class.java)
                myUser?._id = p0.key!!
                UserAuth.instance.setUserInfo(myUser!!)
                listener.invoke()
            }
        })

    }

    private fun createHistory(cartOrders: MutableList<FoodItem>?) {
        try {
            val hisModel: HistoryItem
            val myDate = Date().format()
            val receiptNo = getReceiptNo()
            val displayname = FirebaseAuth.getInstance().currentUser?.displayName
            val result = if (UserAuth.instance.getUserInfo()?.DisplayName == null)
                UserAuth.instance.getUserInfo()?.email
            else UserAuth.instance.getUserInfo()?.DisplayName


            hisModel = if (displayname == null || displayname.isEmpty()) {
                HistoryItem(receiptNo, myDate, "", cartOrders?.sumBy { it.amount }!!,
                        cartOrders.sumByDouble { it.price * it.amount }, cartOrders, result
                )
            } else {
                HistoryItem(receiptNo, myDate, "", cartOrders?.sumBy { it.amount }!!,
                        cartOrders.sumByDouble { it.price * it.amount }, cartOrders, displayname
                )
            }

            userHisRef.child(FirebaseAuth.getInstance().currentUser?.uid!!).push().setValue(hisModel)
            hisRef.push().setValue(hisModel)
        } catch (e: Exception) {
            Log.e("Exception", e.toString())
        }


    }

    private fun getReceiptNo(): String {
        val myDate = Date().format()
        val outputFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = inputFormat.parse(myDate)
        return outputFormat.format(date)

    }


}