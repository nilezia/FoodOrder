package nilezia.app.foodorder.ui

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.data.FoodItem
import nilezia.app.foodorder.data.HistoryItem
import nilezia.app.foodorder.data.UserInfo
import nilezia.app.foodorder.ui.repository.OrderRepository

interface MainActivityContract {

    interface View : BaseMvpView {

        fun onAddOrderToCartEvent(order: FoodItem)

        fun onRemoveOrderFromCartEvent(order: FoodItem)

        fun goToCartActivity()

        fun goToDetail(historyItem: HistoryItem)

        fun updateProfile()

        fun goToChatActivity(user: UserInfo)

    }

    interface Presenter : BaseMvpPresenter<View> {

        fun registerRepository(orderRepository: OrderRepository)

        fun onClickMenuCart()

        fun addOrderToCart(order: FoodItem)

        fun removeOrderFromCart(order: FoodItem)

        fun getOrderFromCart(): MutableList<FoodItem>

        fun getOrderCount(): Int

        fun updateOrderFromCart(orders: MutableList<FoodItem>)

    }
}