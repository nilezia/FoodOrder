package nilezia.app.foodorder.ui.cart;

import nilezia.app.foodorder.base.BaseMvpPresenterImp
import nilezia.app.foodorder.model.OrderItem


class CartOrderPresenter : BaseMvpPresenterImp<CartOrderContract.View>(), CartOrderContract.Presenter {

    private var mOrderItem: MutableList<OrderItem> = mutableListOf()
    override fun updateCardOrder(cartOrders: MutableList<OrderItem>?) {

    }

    override fun registerRepository(cartOrders: MutableList<OrderItem>?) {
        mOrderItem = cartOrders!!
    }

    override fun getCardOrder(): MutableList<OrderItem>? = mOrderItem

}
