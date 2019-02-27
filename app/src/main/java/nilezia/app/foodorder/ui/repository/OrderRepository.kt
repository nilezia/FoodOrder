package nilezia.app.foodorder.ui.repository

//
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import nilezia.app.foodorder.http.CallbackHttp
import nilezia.app.foodorder.model.FoodItem
import nilezia.app.foodorder.model.HistoryItem
import nilezia.app.foodorder.util.format
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class OrderRepository : OrderRepositoryContract {

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.getReference("food")
    private var hisRef = database.getReference("order-history")
    private var userHisRef = database.getReference("user-order")

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

    private fun createHistory(cartOrders: MutableList<FoodItem>?) {
        try {
            val hisModel: HistoryItem
            val myDate = Date().format()
            val receiptNo = getReceiptNo()
            val displayname = FirebaseAuth.getInstance().currentUser?.displayName!!

            hisModel = if (displayname.isEmpty()) {
                HistoryItem(receiptNo, myDate, "", cartOrders?.sumBy { it.amount }!!,
                        cartOrders.sumByDouble { it.price * it.amount }, cartOrders,FirebaseAuth.getInstance().currentUser?.email
                )
            } else {
                HistoryItem(receiptNo, myDate, "", cartOrders?.sumBy { it.amount }!!,
                        cartOrders.sumByDouble { it.price * it.amount }, cartOrders,displayname
                )
            }

            userHisRef.child(FirebaseAuth.getInstance().currentUser?.uid!!).push().setValue(hisModel)
            hisRef.push().setValue(hisModel)
        } catch (e: Exception) {
            Log.e("fblog2", e.toString())
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