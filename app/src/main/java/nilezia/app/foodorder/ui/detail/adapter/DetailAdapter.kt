package nilezia.app.foodorder.ui.detail.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import nilezia.app.foodorder.R
import nilezia.app.foodorder.data.FoodItem


class DetailAdapter : RecyclerView.Adapter<ViewHolder>() {

    var item: MutableList<FoodItem> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_history_detail, parent, false)

        return ViewHolder(view).apply {
            tvOrderAmount = view.findViewById(R.id.tvOrderAmount)
            tvOrderName = view.findViewById(R.id.tvOrderName)
            tvOrderPrice = view.findViewById(R.id.tvOrderPrice)
        }
    }

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindView(item[position])

    }

}