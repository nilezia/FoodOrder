package nilezia.app.foodorder.ui.repository

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import nilezia.app.foodorder.data.Message
import nilezia.app.foodorder.data.UserInfo
import nilezia.app.foodorder.http.CallbackHttp
import nilezia.app.foodorder.model.UserAuth
import android.R
import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.UploadTask
import java.io.File


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

    override fun getChatByUser(oldMessage: MutableList<Message>, receiver: String, messageCall: (MutableList<Message>) -> Unit) {


        val messageNode = mUserMessageDatabase.child(UserAuth.instance.getUserInfo()?._id!!)

        messageReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataSnapshot.ref.removeEventListener(this)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


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

    override fun sendImageToFirebase(path: String, receiver: String, currentUser: UserInfo) {

        val file = Uri.fromFile(File(path))
        storageReference = FirebaseStorage.getInstance().reference
        val imageRef = storageReference.child(file.lastPathSegment)
        val storageRef = storageReference.child("image-chat/" + imageRef.name)
        val mUploadTask = storageRef.putFile(file)

        mUploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation storageRef.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                sendMessageToFirebase(downloadUri.toString(), Message.TYPE_IMAGE, receiver, currentUser)
            }
        }
    }
}