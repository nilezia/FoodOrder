package nilezia.app.foodorder.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import nilezia.app.foodorder.R
import nilezia.app.foodorder.model.Order

class OrderAdapter(private val listener: (Order?) -> Unit) : RecyclerView.Adapter<MyHolder>() {

    var orders: MutableList<Order>? = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_order, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int = orders?.size!!

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
       holder.bindView(orders?.get(position)!!, listener)

    }
}