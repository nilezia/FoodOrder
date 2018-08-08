package nilezia.app.foodorder.ui.history.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import nilezia.app.foodorder.model.HistoryItem

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

     var tvDate: TextView? = null
     var tvTotalPrice: TextView? = null

    @SuppressLint("SetTextI18n")
    fun bindView(item: HistoryItem) = with(itemView) {
        tvDate?.text = item.date
        tvTotalPrice?.text = "${item.totalPrice}฿"

    }

}