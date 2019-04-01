package nilezia.app.foodorder.ui.friend.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import nilezia.app.foodorder.data.UserInfo
import com.bumptech.glide.Glide

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var imgProfile: ImageView? = null
    var tvDisplay: TextView? = null


    @SuppressLint("SetTextI18n")
    fun bindView(item: UserInfo, listener: (UserInfo) -> Unit) = with(itemView) {
        tvDisplay?.text = "${item.DisplayName}"
        Glide.with(itemView.context).load(item.avatar).into(imgProfile!!)
        setOnClickListener {

            listener.invoke(item)
        }
    }


}