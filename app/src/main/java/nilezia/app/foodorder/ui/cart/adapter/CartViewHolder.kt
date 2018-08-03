package nilezia.app.foodorder.ui.cart.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import nilezia.app.foodorder.model.OrderItem

class CartViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    var tvOrderName: TextView? = null
    var tvOrderPrice: TextView? = null
    var tvOrderAmount: TextView? = null
    var tvOrderQuantity: TextView? = null
    var btnMinus: ImageView? = null
    var btnPlus: ImageView? = null
    var btnDelete: ImageView? = null

    @SuppressLint("SetTextI18n")
    fun bindView(order: OrderItem?, position: Int) = with(itemView) {

        tvOrderName?.text = order?.name
        tvOrderPrice?.text = "${order?.price}"
        tvOrderQuantity?.text = "เหลือ ${order?.quantity}"
        tvOrderAmount?.text = "${order?.amount}"
    }

    interface CartClickListener {

        fun onClickIncreaseOrder(order: OrderItem, position: Int)

        fun onClickDecreaseOrder(order: OrderItem, position: Int)

        fun onClickDeleteOrder(order: OrderItem, position: Int)
    }
}