package nilezia.app.foodorder.ui.chat

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat_room.*
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpActivity
import nilezia.app.foodorder.data.Message
import nilezia.app.foodorder.data.UserInfo
import nilezia.app.foodorder.model.UserAuth
import nilezia.app.foodorder.ui.MainActivity.Companion.USER_INTENT_KEY
import nilezia.app.foodorder.ui.chat.adapter.ChatAdapter
import nilezia.app.foodorder.ui.repository.UserRepository
import org.parceler.Parcels

class ChatActivity : BaseMvpActivity<ChatContract.View, ChatContract.Presenter>(), ChatContract.View {

    private var database = FirebaseDatabase.getInstance()
    private var mUserMessageDatabase: DatabaseReference = database.getReference("user-post")
    private var chatAdapter: ChatAdapter? = null

    override fun initial() {

    }

    override fun setupLayout(): Int = R.layout.activity_chat_room

    override fun setupView() {

        btnSendMessage?.setOnClickListener {

            getPresenter().sendMessage(edtMessage.text.toString(), Message.TYPE_TEXT)
            edtMessage?.setText("")

        }

        btnAddPhoto?.setOnClickListener {

            // showBottomSheet()

        }
    }

    override fun setupInstance() {

        val userChat = Parcels.unwrap(intent.getParcelableExtra(USER_INTENT_KEY)) as UserInfo
        setupToolbar(userChat)
        setupRecyclerView()
        setupAdapter(UserAuth.instance.getUserInfo()?._id)
        getPresenter().registerUserRepository(UserRepository(), userChat)
        getPresenter().loadChat()

    }

    private fun setupToolbar(userChat: UserInfo) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = userChat.DisplayName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun createPresenter(): ChatContract.Presenter = ChatPresenter.create()

    override fun onRestoreInstanceState(bundle: Bundle) {

    }

    override fun updateMessage(messages: MutableList<Message>) {

        chatAdapter?.apply {
            setMessage(messages)
            notifyDataSetChanged()
        }
        rvChat?.smoothScrollToPosition(chatAdapter?.itemCount!!)
    }

    private fun setupRecyclerView() {
        rvChat?.layoutManager = LinearLayoutManager(this)

    }

    private fun setupAdapter(uid: String?) {
        chatAdapter = ChatAdapter(uid!!)
        rvChat?.adapter = chatAdapter

    }

    override fun showOldMessageByUser(oldMessage: MutableList<Message>, receiver: String) {

        val messageNode = mUserMessageDatabase.child(UserAuth.instance.getUserInfo()?._id!!)

        messageNode.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

                messageNode.ref.removeEventListener(this)

            }

            override fun onDataChange(p0: DataSnapshot) {

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

                this@ChatActivity.updateMessage(oldMessage)

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy", "Activity has onDestroy")
    }

}