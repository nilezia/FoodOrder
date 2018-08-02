package nilezia.app.foodorder.ui

import nilezia.app.foodorder.base.BaseMvpPresenterImp
import nilezia.app.foodorder.model.OrderItem

class MainActivityPresenter : BaseMvpPresenterImp<MainActivityContract.View>(), MainActivityContract.Presenter {

    private var cartOrder: MutableList<OrderItem> = mutableListOf()

    override fun addOrderToCart(order: OrderItem) {
        cartOrder.add(order)
    }

    override fun removeOrderFromCart(order: OrderItem) {
        cartOrder.remove(order)
    }

    override fun getOrderFromCart(): MutableList<OrderItem> = cartOrder

    override fun getOrderCount(): Int = cartOrder.size

    override fun onClickMenuCart() {
        mView?.goToCartActivity()
    }
}