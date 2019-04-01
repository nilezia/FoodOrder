package nilezia.app.foodorder.ui.repository

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import nilezia.app.foodorder.data.Message
import nilezia.app.foodorder.data.UserInfo
import nilezia.app.foodorder.http.CallbackHttp
import nilezia.app.foodorder.model.UserAuth

class UserRepository : UserRepositoryContract {


    private var database = FirebaseDatabase.getInstance()
    private var userRef = database.getReference("users")
    private var messageReference: DatabaseReference = database.getReference("messages")
    private var mUserMessageDatabase: DatabaseReference = database.getReference("user-post")
    private var storageReference: StorageReference = FirebaseStorage.getInstance().reference


    override fun getAllUsers(callbackHttp: CallbackHttp<MutableList<UserInfo>>) {

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                callbackHttp.onFailed(p0.message)
                Log.d("Exception", p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {

                val users = mutableListOf<UserInfo>()
                p0.children.forEach {
                    val user = it.getValue(UserInfo::class.java)
                    user?._id = it.key!!
                    users.add(user!!)
                }


//                p0.children.mapNotNullTo(users) {
//                    it.getValue<UserInfo>(UserInfo::class.java)
//                }
                Log.d("Exception", users.toString())

                callbackHttp.onSuccess(users)
            }
        })
    }

/*    override fun getUserInfo(listener: () -> Unit) {
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
    }*/

    override fun sendMessageToFirebase(text: String, type: String, receiver: String, currentUser: UserInfo) {

        val databaseReference = messageReference.push()
        val mUserMessage = mUserMessageDatabase.child(currentUser._id)
        val message = Message(avatar = currentUser.avatar, message = text,
                type = type, senderId = currentUser._id,
                userName = currentUser.DisplayName,
                receiverId = receiver)

        mUserMessage.push().setValue(message)
        databaseReference.setValue(message)


        val mUserMessage2 = mUserMessageDatabase.child(receiver)
        mUserMessage2.push().setValue(message)


    }

    override fun getChatByUser(oldMessage: MutableList<Message>, receiver: String, messageCall: (MutableList<Message>) -> Unit) {

        val messageNode = mUserMessageDatabase.child(UserAuth.instance.getUserInfo()?._id!!)
        messageNode.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val messages = p0.getValue(Message::class.java)

                if (messages?.receiverId == receiver || messages?.senderId == receiver) {
                    oldMessage.add(messages)
                }
                messageCall.invoke(oldMessage)

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })


    }


}