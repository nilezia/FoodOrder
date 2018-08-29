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

class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    lateinit var tvOrderName: TextView
    lateinit var tvOrderPrice: TextView
    lateinit var tvOrderAmount: TextView
    lateinit var tvOrderDescription: TextView
    lateinit var btnAdd: Button
    lateinit var imgView: ImageView
    @SuppressLint("SetTextI18n")
    fun bindView(item: FoodItem?, listener: ViewHolder.OrderClickListener) = with(itemView) {
        tvOrderName.text = item?.name
        tvOrderDescription.text = item?.description

        if (item?.quantity == 0) {
            tvOrderAmount.apply {
                setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                text = "สินค้าหมดแล้ว"
            }

        } else {
            tvOrderAmount.apply {
                setTextColor(ContextCompat.getColor(context, R.color.soft_black))
                text = "มีอยู่ ${item?.quantity.toString()}"
            }
        }


        tvOrderPrice.text = "${item?.price.toString()}฿"

        Glide.with(this).load(item?.image).into(imgView)

        item?.isAdded?.let {
            if (it) {
                btnAdd.apply {
                    text = context.getString(R.string.item_added)
                    setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
                }
            } else {
                btnAdd.apply {
                    text = context.getString(R.string.item_add)
                    setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
                }
            }
        }
        btnAdd.setOnClickListener {

            onItemClick(item, adapterPosition, listener)
        }
    }

    private fun onItemClick(item: FoodItem?, position: Int, listener: ViewHolder.OrderClickListener) {

        if (!item?.isAdded!!) {
            if (item.quantity > 0) {
                btnAdd.apply {
                    text = context.getString(R.string.item_added)
                    setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
                }
                item.isAdded = true
                listener.onClickOrderToCart(item, position)
            } else {
                listener.onItemEmpty()
            }
        } else {
            item.isAdded = false
            btnAdd.apply {
                text = context.getString(R.string.item_add)
                setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
            }
            listener.onClickAdded(item, position)
        }
    }

    interface OrderClickListener {

        fun onClickAdded(item: FoodItem, position: Int)

        fun onClickOder(item: FoodItem, position: Int)

        fun onClickOrderToCart(item: FoodItem, position: Int)

        fun onItemEmpty()
    }
}