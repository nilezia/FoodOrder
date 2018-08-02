package nilezia.app.foodorder.ui.cart.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import nilezia.app.foodorder.R
import nilezia.app.foodorder.model.OrderItem


class CartAdapter : RecyclerView.Adapter<CartViewHolder>() {
    var orders: MutableList<OrderItem>? = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_cart, parent, false)
        return CartViewHolder(view).apply {
            tvOrderName = view.findViewById(R.id.tvOrderName)
            tvOrderAmount = view.findViewById(R.id.tvAmount)
            tvOrderPrice = view.findViewById(R.id.tvOrderPrice)
            tvOrderQuantity = view.findViewById(R.id.tvOrderQuantity)
            btnMinus = view.findViewById(R.id.btnMinus)
            btnPlus = view.findViewById(R.id.btnPlus)
            btnRemove = view.findViewById(R.id.btnRemove)
        }
    }

    override fun getItemCount(): Int = orders?.size!!

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bindView(orders?.get(position), position)
    }


}