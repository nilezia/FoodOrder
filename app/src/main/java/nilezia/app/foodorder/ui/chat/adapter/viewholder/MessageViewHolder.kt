package nilezia.app.foodorder.ui.chat.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import nilezia.app.foodorder.R


class MessageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    var tvMessage: TextView? = null
    var tvName: TextView? = null
    var ivProfile: ImageView? = null
    var ivMessage: ImageView? = null

    init {

        tvMessage = itemView?.findViewById(R.id.tv_message) as TextView
        tvName = itemView.findViewById(R.id.tv_name) as TextView
        ivProfile = itemView.findViewById(R.id.iv_profile) as ImageView
        ivMessage = itemView.findViewById(R.id.iv_message) as ImageView
    }

}
