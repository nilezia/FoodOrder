package nilezia.app.foodorder.ui.food

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.model.FoodItem
import nilezia.app.foodorder.ui.repository.OrderRepository

interface FoodProductContract {


    interface View : BaseMvpView {
        fun addOrderToCartEvent(order: FoodItem)

        fun removeOrderFromCartEvent(order: FoodItem)

        fun updateOrderItemFromCart()

        fun updateOrderItemRequest(orders: MutableList<FoodItem>)

        fun showErrorDialog(message: String?)
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun registerRepository(repository: OrderRepository)

        fun requestOrders()

        fun onAddOrderItemToCart(orderItem: FoodItem, position: Int)

        fun onRemoveOrderFromCart(orderItem: FoodItem, position: Int)

        fun getOrders(): MutableList<FoodItem>?

    }


}