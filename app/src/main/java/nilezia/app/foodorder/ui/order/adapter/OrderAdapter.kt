package nilezia.app.foodorder.ui.order.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import nilezia.app.foodorder.R
import nilezia.app.foodorder.model.OrderItem

class OrderAdapter(private val listener: MyHolder.OrderClickListener) : RecyclerView.Adapter<MyHolder>() {

    var orders: MutableList<OrderItem>? = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_order, parent, false)
        return MyHolder(view).apply {
            tvOrderName = view.findViewById(R.id.order_name)
            tvOrderDescription = view.findViewById(R.id.order_description)
            tvOrderAmount = view.findViewById(R.id.order_amount)
            tvOrderPrice = view.findViewById(R.id.order_price)
            btnAdd = view.findViewById(R.id.btnAdd)
            imgView = view.findViewById(R.id.imgView)
        }
    }

    override fun getItemCount(): Int = orders?.size!!

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        holder.bindView(orders?.get(position)!!, position, listener)

    }


}