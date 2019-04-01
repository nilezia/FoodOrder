package nilezia.app.foodorder.ui.friend.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import nilezia.app.foodorder.R
import nilezia.app.foodorder.data.UserInfo


class FriendsAdapter(var listener: (UserInfo) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

    var users: MutableList<UserInfo>? = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_friend, parent, false)
        return ViewHolder(view).apply {
            tvDisplay = view.findViewById(R.id.tvDisplay)
            imgProfile = view.findViewById(R.id.imgProfile)


        }
    }

    override fun getItemCount(): Int = users?.size!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(users?.get(position)!!, listener)

    }

}