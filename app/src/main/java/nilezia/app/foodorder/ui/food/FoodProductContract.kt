package nilezia.app.foodorder.ui.food

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.model.FoodItem
import nilezia.app.foodorder.ui.repository.OrderRepository

interface FoodProductContract {


    interface View : BaseMvpView {
        fun onAddOrderToCartEvent(order: FoodItem)

        fun onRemoveOrderFromCartEvent(order: FoodItem)

        fun updateOrderItemFromCart()

        fun updateOrderItemRequest(orders: MutableList<FoodItem>)

        fun onShowErrorDialog(message: String?)
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun registerRepository(repository: OrderRepository)

        fun requestOrders()

        fun addOrderItemToCart(orderItem: FoodItem, position: Int)

        fun removeOrderFromCart(orderItem: FoodItem, position: Int)

        fun getOrders(): MutableList<FoodItem>?

    }


}