package nilezia.app.foodorder.ui.cart.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import nilezia.app.foodorder.R
import nilezia.app.foodorder.model.OrderItem


class CartAdapter(onClickListener: CartViewHolder.CartClickListener) : RecyclerView.Adapter<CartViewHolder>() {
    var orders: MutableList<OrderItem>? = mutableListOf()
    val listener = onClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_cart, parent, false)
        return CartViewHolder(view).apply {
            tvOrderName = view.findViewById(R.id.tvOrderName)
            tvOrderAmount = view.findViewById(R.id.tvAmount)
            tvOrderPrice = view.findViewById(R.id.tvOrderPrice)
            tvOrderQuantity = view.findViewById(R.id.tvOrderQuantity)
            btnMinus = view.findViewById(R.id.btnMinus)
            btnPlus = view.findViewById(R.id.btnPlus)
            btnDelete = view.findViewById(R.id.btnDelete)
        }
    }

    override fun getItemCount(): Int = orders?.size!!

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bindView(orders?.get(position), position)

        holder.btnPlus?.setOnClickListener {

            val amount = orders?.get(position)?.amount
            val quantity = orders?.get(position)?.quantity
            if (amount!! < quantity!!) {
                orders?.get(position)?.amount = amount + 1
            }


            holder.tvOrderAmount?.text = "${orders?.get(position)?.amount}"
            listener.onClickIncreaseOrder(orders?.get(position)!!, position)
        }


        holder.btnMinus?.setOnClickListener {

            val amount = orders?.get(position)?.amount
            if (amount!! > 1) {
                orders?.get(position)?.amount = amount - 1
            }
            holder.tvOrderAmount?.text = "${orders?.get(position)?.amount}"
            listener.onClickDecreaseOrder(orders?.get(position)!!, position)
        }

        holder.btnDelete?.setOnClickListener {
            listener.onClickDeleteOrder(orders?.get(position)!!, position)

        }
    }

    fun deleteOrder(order: OrderItem, position: Int) {
        Log.d("itemClick", "${order.name} Remove")
        orders?.remove(orders?.get(position)!!)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, orders?.size!!)
    }

    fun getTotal(): Double = orders?.sumByDouble {
        it.price * it.amount
    }!!

    fun hasItem(): Boolean = orders?.size!! > 0
}