package nilezia.app.foodorder.ui.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import nilezia.app.foodorder.model.OrderItem

class MyHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    var tvOrderName: TextView? = null
    var tvOrderPrice: TextView? = null
    var tvOrderAmount: TextView? = null
    var tvOrderDescription: TextView? = null
    var btnAdd: Button? = null
    var imgView: ImageView? = null
    @SuppressLint("SetTextI18n")
    fun bindView(order: OrderItem?, position: Int, listener: MyHolder.OrderClickListener) = with(itemView) {
        tvOrderName?.text = order?.name
        tvOrderDescription?.text = order?.description
        tvOrderAmount?.text = "จำนวน ${order?.amount.toString()}"
        tvOrderPrice?.text = "${order?.price.toString()}฿"
      //  Glide.with(itemView.context).load(order?.image).into(imgView!!)
        Glide.with(this).load(order?.image).into(imgView!!)

        btnAdd?.setOnClickListener {

            onItemClick(order, position, listener)
        }
    }

    private fun onItemClick(order: OrderItem?, position: Int, listener: MyHolder.OrderClickListener) {

        if (!order?.isAdded!!) {
            if (order.amount > 0) {
                btnAdd?.text = "Added"
                order.isAdded = true
                listener.onClickOrderToCart(order, position)
            } else {
                listener.onItemEmpty()
            }
        } else {
            order.isAdded = false
            btnAdd?.text = "Add"
            listener.onClickAdded(order, position)
        }
    }

    interface OrderClickListener {

        fun onClickAdded(order: OrderItem, position: Int)

        fun onClickOder(order: OrderItem, position: Int)

        fun onClickOrderToCart(order: OrderItem, position: Int)

        fun onItemEmpty()
    }
}