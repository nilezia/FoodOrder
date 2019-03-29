package nilezia.app.foodorder.ui.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import nilezia.app.foodorder.data.UserInfo
import nilezia.app.foodorder.http.CallbackHttp
import nilezia.app.foodorder.model.UserAuth

class UserRepository : UserRepositoryContract {

    private var database = FirebaseDatabase.getInstance()
    private var userRef = database.getReference("users")


    override fun getAllUsers(callbackHttp: CallbackHttp<MutableList<UserInfo>>) {

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                callbackHttp.onFailed(p0.message)
                Log.d("Exception", p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {

                val users = mutableListOf<UserInfo>()

                p0.children.mapNotNullTo(users) {
                    it.getValue<UserInfo>(UserInfo::class.java)
                }
                Log.d("Exception", users.toString())

                callbackHttp.onSuccess(users)
            }
        })
    }

    override fun getUserInfo(listener: () -> Unit) {
        val userNode = userRef.child(FirebaseAuth.getInstance().currentUser?.uid!!)
        userNode.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("Exception", p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {

                val myUser = p0.getValue(UserInfo::class.java)
                UserAuth.instance.setUserInfo(myUser!!)
                listener.invoke()
            }
        })
    }
}