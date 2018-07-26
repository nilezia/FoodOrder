package nilezia.app.foodorder.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import nilezia.app.foodorder.model.Order

class MyHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    fun bindView(order: Order?, listener: (Order?) -> Unit) = with(itemView) {

        listener.invoke(order)

    }

}