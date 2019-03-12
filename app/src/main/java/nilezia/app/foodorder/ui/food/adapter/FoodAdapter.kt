package nilezia.app.foodorder.ui.food.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import nilezia.app.foodorder.R
import nilezia.app.foodorder.data.FoodItem

class FoodAdapter(private val listener: ViewHolder.OrderClickListener) : RecyclerView.Adapter<ViewHolder>() {

    var foodOrders: MutableList<FoodItem>? = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_food, parent, false)
        return ViewHolder(view).apply {
            tvOrderName = view.findViewById(R.id.order_name)
            tvOrderDescription = view.findViewById(R.id.order_description)
            tvOrderAmount = view.findViewById(R.id.order_amount)
            tvOrderPrice = view.findViewById(R.id.order_price)
            btnAdd = view.findViewById(R.id.btnAdd)
            imgView = view.findViewById(R.id.imgView)
        }
    }

    override fun getItemCount(): Int = foodOrders?.size!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindView(foodOrders?.get(position)!!, listener)

    }

}

