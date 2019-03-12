package nilezia.app.foodorder.ui

import nilezia.app.foodorder.base.BaseMvpPresenterImp
import nilezia.app.foodorder.data.FoodItem

class MainActivityPresenter : BaseMvpPresenterImp<MainActivityContract.View>(), MainActivityContract.Presenter {

    companion object {
        fun create(): MainActivityContract.Presenter = MainActivityPresenter()
    }

    private var cartOrder: MutableList<FoodItem> = mutableListOf()

    override fun addOrderToCart(order: FoodItem) {
        cartOrder.add(order)
    }

    override fun removeOrderFromCart(order: FoodItem) {
        cartOrder.remove(order)
    }

    override fun getOrderFromCart(): MutableList<FoodItem> = cartOrder

    override fun getOrderCount(): Int = cartOrder.size

    override fun onClickMenuCart() {
        getView().goToCartActivity()
    }

    override fun updateOrderFromCart(orders: MutableList<FoodItem>) {
        this.cartOrder = orders
    }


}