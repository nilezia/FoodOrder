package nilezia.app.foodorder.ui

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.model.FoodItem
import nilezia.app.foodorder.model.HistoryItem

interface MainActivityContract {

    interface View : BaseMvpView {

        fun onAddOrderToCartEvent(order: FoodItem)

        fun onRemoveOrderFromCartEvent(order: FoodItem)

        fun goToCartActivity()

        fun goToDetail(historyItem: HistoryItem)

    }

    interface Presenter : BaseMvpPresenter<View> {

        fun onClickMenuCart()

        fun addOrderToCart(order: FoodItem)

        fun removeOrderFromCart(order: FoodItem)

        fun getOrderFromCart(): MutableList<FoodItem>

        fun getOrderCount(): Int

        fun updateOrderFromCart(orders: MutableList<FoodItem>)

    }
}