package nilezia.app.foodorder.ui.chat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.TextView
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
import siclo.com.ezphotopicker.api.EZPhotoPick
import siclo.com.ezphotopicker.api.EZPhotoPickStorage
import siclo.com.ezphotopicker.api.models.EZPhotoPickConfig
import siclo.com.ezphotopicker.api.models.PhotoSource
import java.io.IOException

class ChatActivity : BaseMvpActivity<ChatContract.View, ChatContract.Presenter>(), ChatContract.View {

    private var database = FirebaseDatabase.getInstance()
    private var mUserMessageDatabase: DatabaseReference = database.getReference("user-post")
    private var chatAdapter: ChatAdapter? = null
    private lateinit var bottomSheetView: View
    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun initial() {

    }

    override fun setupLayout(): Int = R.layout.activity_chat_room

    override fun setupView() {

        btnSendMessage?.setOnClickListener {

            getPresenter().sendMessage(edtMessage.text.toString(), Message.TYPE_TEXT)
            edtMessage?.setText("")

        }

        btnAddPhoto?.setOnClickListener {

             showBottomSheet()

        }
    }

    override fun setupInstance() {

        val userChat = Parcels.unwrap(intent.getParcelableExtra(USER_INTENT_KEY)) as UserInfo
        setupToolbar(userChat)
        setupRecyclerView()
        setupBottomSheet()
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
        chatAdapter = ChatAdapter(uid!!,applicationContext)
        rvChat?.adapter = chatAdapter

    }

    private fun setupBottomSheet() {
        bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        bottomSheetDialog = BottomSheetDialog(this@ChatActivity)

        bottomSheetDialog.apply {

            bottomSheetDialog.setContentView(bottomSheetView)
        }

    }



    private fun showBottomSheet() {


        bottomSheetView.findViewById<TextView>(R.id.menu_bottom_sheet_camera).setOnClickListener {
            EZPhotoPick.startPhotoPickActivity(this, chooseCamera())
            bottomSheetDialog.cancel()

        }

        bottomSheetView.findViewById<TextView>(R.id.menu_bottom_sheet_gallery).setOnClickListener {
            EZPhotoPick.startPhotoPickActivity(this, chooseImage())
            bottomSheetDialog.cancel()
        }

        bottomSheetDialog.show()
    }

    private fun chooseImage() = EZPhotoPickConfig().apply {
        photoSource = PhotoSource.GALLERY
        exportingSize = 900
        exportedPhotoName = "IMG_" + System.currentTimeMillis().toString()

    }

    private fun chooseCamera() = EZPhotoPickConfig().apply {

        photoSource = PhotoSource.CAMERA
        exportingSize = 900
        exportedPhotoName = "IMG_" + System.currentTimeMillis().toString()

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EZPhotoPick.PHOTO_PICK_GALLERY_REQUEST_CODE ||
                requestCode == EZPhotoPick.PHOTO_PICK_CAMERA_REQUEST_CODE &&
                resultCode == Activity.RESULT_OK) {
            try {

                //   val pickedPhoto: Bitmap = EZPhotoPickStorage(this).loadLatestStoredPhotoBitmap()


                var photoName = data?.getStringExtra(EZPhotoPick.PICKED_PHOTO_NAME_KEY)!!
                var photoPath = EZPhotoPickStorage(this).getAbsolutePathOfStoredPhoto("", photoName)

               // showProgressDialog()
                getPresenter().uploadFromFile(photoPath)

            } catch (e: IOException) {
                e.printStackTrace()
                // onChoosePhotoFailure(e.toString())
            }
        }
    }

}