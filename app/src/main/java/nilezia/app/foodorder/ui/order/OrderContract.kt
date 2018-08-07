package nilezia.app.foodorder.ui.order

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.model.OrderItem
import nilezia.app.foodorder.ui.repository.OrderRepository

interface OrderContract {


    interface View : BaseMvpView {
        fun onAddOrderToCartEvent(order: OrderItem)

        fun onRemoveOrderFromCartEvent(order: OrderItem)

        fun updateOrderItemFromCart()

        fun updateOrderItemRequest(orders: MutableList<OrderItem>)

        fun onShowErrorDialog(message: String?)
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun registerRepository(repository: OrderRepository)

        fun requestOrders()

        fun addOrderItemToCart(orderItem: OrderItem, position: Int)

        fun removeOrderFromCart(orderItem: OrderItem, position: Int)

        fun getOrders(): MutableList<OrderItem>?

    }


}