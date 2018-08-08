package nilezia.app.foodorder.ui.food.adapter

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import nilezia.app.foodorder.R
import nilezia.app.foodorder.model.FoodItem

class MyHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    var tvOrderName: TextView? = null
    var tvOrderPrice: TextView? = null
    var tvOrderAmount: TextView? = null
    var tvOrderDescription: TextView? = null
    var btnAdd: Button? = null
    var imgView: ImageView? = null
    @SuppressLint("SetTextI18n")
    fun bindView(order: FoodItem?, position: Int, listener: MyHolder.OrderClickListener) = with(itemView) {
        tvOrderName?.text = order?.name
        tvOrderDescription?.text = order?.description

        if (order?.quantity == 0) {
            tvOrderAmount?.apply {
                setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                text = "สินค้าหมดแล้ว"
            }

        } else {
            tvOrderAmount?.apply {
                text = "มีอยู่ ${order?.quantity.toString()}"
            }
        }


        tvOrderPrice?.text = "${order?.price.toString()}฿"

        Glide.with(this).load(order?.image).into(imgView!!)

        if (order?.isAdded!!) {
            btnAdd?.apply {
                text = context.getString(R.string.item_added)
                setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
            }
        } else {
            btnAdd?.apply {
                text = context.getString(R.string.item_add)
                setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
            }
        }
        btnAdd?.setOnClickListener {

            onItemClick(order, position, listener)
        }
    }


    private fun onItemClick(order: FoodItem?, position: Int, listener: MyHolder.OrderClickListener) {

        if (!order?.isAdded!!) {
            if (order.quantity > 0) {
                btnAdd?.apply {
                    text = context.getString(R.string.item_added)
                    setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
                }
                order.isAdded = true
                listener.onClickOrderToCart(order, position)
            } else {
                listener.onItemEmpty()
            }
        } else {
            order.isAdded = false
            btnAdd?.apply {
                text = context.getString(R.string.item_add)
                setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
            }
            listener.onClickAdded(order, position)
        }
    }

    interface OrderClickListener {

        fun onClickAdded(order: FoodItem, position: Int)

        fun onClickOder(order: FoodItem, position: Int)

        fun onClickOrderToCart(order: FoodItem, position: Int)

        fun onItemEmpty()
    }
}