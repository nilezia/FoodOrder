package nilezia.app.foodorder.ui.cart.adapter

import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.model.OrderItem

interface CartAdapterContract {

    interface View : BaseMvpView

    interface Presenter {

        fun getOrderItem(): MutableList<OrderItem>?

        fun setOrderItem(orderItems: MutableList<OrderItem>?)

        fun increaseOrder(order: OrderItem, position: Int)

        fun decreaseOrder(order: OrderItem, position: Int)

        fun deleteOrder(order: OrderItem, position: Int)

        fun getPriceTotal(): Double

    }
}