package nilezia.app.foodorder.ui.cart.adapter

import nilezia.app.foodorder.base.adapter.BaseMvpAdapterView
import nilezia.app.foodorder.data.FoodItem

interface CartAdapterContract {

    interface View : BaseMvpAdapterView

    interface Presenter {

        fun getOrderItem(): MutableList<FoodItem>

        fun setOrderItem(orderItems: MutableList<FoodItem>)

        fun increaseOrder(order: FoodItem, position: Int)

        fun decreaseOrder(order: FoodItem, position: Int)

        fun deleteOrder(order: FoodItem, position: Int)

        fun getPriceTotal(): Double
    }
}