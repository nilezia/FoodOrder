package nilezia.app.foodorder.ui.history.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import nilezia.app.foodorder.model.HistoryItem

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tvReceiptNo: TextView? = null
    var tvDate: TextView? = null
    var tvTotalPrice: TextView? = null
    var tvOwner: TextView? = null

    @SuppressLint("SetTextI18n")
    fun bindView(item: HistoryItem, itemSelectListener: (HistoryItem) -> Unit) = with(itemView) {
        tvReceiptNo?.text = item.receiptNo
        tvDate?.text = item.date.toString()
        tvTotalPrice?.text = "${item.totalPrice}฿"
        tvOwner?.text = item.userName
        setOnClickListener {
            itemSelectListener.invoke(item)
        }
    }
}