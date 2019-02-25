package nilezia.app.foodorder.ui.history.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import nilezia.app.foodorder.R
import nilezia.app.foodorder.model.HistoryItem


class OrderHistoryAdapter(private val itemSelectListener: (HistoryItem) -> Unit) : RecyclerView.Adapter<ViewHolder>() {
    var historyItems: MutableList<HistoryItem>? = mutableListOf<HistoryItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_history_order, parent, false)
        return ViewHolder(view).apply {
            tvReceiptNo = view.findViewById(R.id.tvReceiptNo)
            tvDate = view.findViewById(R.id.tvDate)
            tvTotalPrice = view.findViewById(R.id.tvTotalPrice)
            tvOwner = view.findViewById(R.id.tvOwner)

        }
    }

    override fun getItemCount(): Int = historyItems?.size!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(historyItems?.get(position)!!, itemSelectListener)
    }
}