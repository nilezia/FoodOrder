package nilezia.app.foodorder.ui.chat.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import nilezia.app.foodorder.ui.chat.adapter.viewholder.MessageViewHolder

import com.bumptech.glide.Glide
import nilezia.app.foodorder.R
import nilezia.app.foodorder.data.Message


class ChatAdapter(uid: String, context: Context) : RecyclerView.Adapter<MessageViewHolder>() {


    private var messageList: MutableList<Message>? = mutableListOf()
    private val mUid: String = uid
    private val mContext = context

    companion object {
        private val TYPE_YOUR_MESSAGE = 0
        private val TYPE_OTHER_MESSAGE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        return if (viewType == TYPE_YOUR_MESSAGE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_your_message_item, parent, false)
            MessageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_other_message_item, parent, false)
            MessageViewHolder(view)
        }

    }

    override fun getItemCount(): Int = messageList?.size!!

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {

        val message = messageList!![position]
        if (holder is MessageViewHolder) {
            Glide.with(mContext).load(message.avatar).into(holder.ivProfile!!)
            holder.tvName!!.text = message.userName
            holder.tvMessage!!.visibility = View.GONE
            holder.ivMessage!!.visibility = View.GONE
            if (message.type.equals(Message.TYPE_TEXT)) {
                holder.tvMessage!!.text = message.message
                holder.tvMessage!!.visibility = View.VISIBLE
            } else if (message.type.equals(Message.TYPE_IMAGE)) {
                Glide.with(holder.itemView.context).load(message.message).into(holder.ivMessage!!)
                holder.ivMessage!!.visibility = View.VISIBLE
                holder.ivMessage!!.setOnClickListener {
                    //                    if (onMessageClickListener != null) {
//                        onMessageClickListener.onImageClick(message)
//                    }
                }
            }
        }
    }

    fun setMessage(messages: MutableList<Message>?) {

        this.messageList = messages

    }

    override fun getItemViewType(position: Int): Int {
        val message = messageList?.get(position)
        return if (message?.senderId == mUid) {
            TYPE_YOUR_MESSAGE
        } else {
            TYPE_OTHER_MESSAGE
        }
    }


}