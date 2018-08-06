package nilezia.app.foodorder.ui.cart;

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.model.OrderItem
import nilezia.app.foodorder.ui.repository.CartRepository

interface CartOrderContract {

    interface View : BaseMvpView {
        fun updateTotalPrice()
    }

    interface Presenter : BaseMvpPresenter<View> {

        fun registerRepository(cartOrders: MutableList<OrderItem>?, cartRepository: CartRepository)

        fun getCardOrder(): MutableList<OrderItem>?

        fun updateCardOrder(cartOrders: MutableList<OrderItem>?)

        fun confirmCartOrder(cartOrders: MutableList<OrderItem>?)
    }

}
