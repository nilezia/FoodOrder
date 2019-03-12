package nilezia.app.foodorder.ui.detail.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import nilezia.app.foodorder.data.FoodItem

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tvOrderPrice: TextView? = null
    var tvOrderAmount: TextView? = null
    var tvOrderName: TextView? = null

    @SuppressLint("SetTextI18n")
    fun bindView(item: FoodItem) = with(itemView) {

        tvOrderName?.text = item.name
        tvOrderAmount?.text = "x ${item.amount} ea"
        tvOrderPrice?.text = "${item.price}฿"

    }


}