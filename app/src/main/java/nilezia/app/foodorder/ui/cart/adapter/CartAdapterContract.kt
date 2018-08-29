package nilezia.app.foodorder.ui.cart.adapter

import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.model.FoodItem

interface CartAdapterContract {

    interface View : BaseMvpView

    interface Presenter {

        fun getOrderItem(): MutableList<FoodItem>

        fun setOrderItem(orderItems: MutableList<FoodItem>)

        fun increaseOrder(order: FoodItem, position: Int)

        fun decreaseOrder(order: FoodItem, position: Int)

        fun deleteOrder(order: FoodItem, position: Int)

        fun getPriceTotal(): Double
    }
}