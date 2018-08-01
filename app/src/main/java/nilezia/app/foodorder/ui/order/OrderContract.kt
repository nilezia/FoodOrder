package nilezia.app.foodorder.ui.order

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.model.OrderItem
import nilezia.app.foodorder.ui.repository.OrderRepository

interface OrderContract {


    interface View : BaseMvpView {
        fun onAddOrderToCartEvent(order: OrderItem)

        fun onRemoveOrderFromCartEvent(order: OrderItem)
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun registerRepository(repository: OrderRepository)

        fun addOrderItemToCart(orderItem: OrderItem, position: Int)

        fun removeOrderFromCart(orderItem: OrderItem, position: Int)

        fun getOrders(): MutableList<OrderItem>?

    }


}