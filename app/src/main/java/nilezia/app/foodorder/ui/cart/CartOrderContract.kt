package nilezia.app.foodorder.ui.cart

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.model.OrderItem
import nilezia.app.foodorder.ui.repository.OrderRepository

interface CartOrderContract {

    interface View : BaseMvpView {


        fun onUpdateCartAdapter(cartOrders: MutableList<OrderItem>?)

        fun onPaymentSuccess()

        fun showOrderInCart()

        fun hideOrderInCart()

        fun hasItem(): Boolean?

    }

    interface Presenter : BaseMvpPresenter<View> {

        fun registerRepository(cartOrders: MutableList<OrderItem>?, repository: OrderRepository)

        fun getCardOrder(): MutableList<OrderItem>?

        fun updateCardOrder(cartOrders: MutableList<OrderItem>?)

        fun confirmCartOrder(cartOrders: MutableList<OrderItem>?)

        fun updateCartView()
    }

}
