package nilezia.app.foodorder.ui.cart

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.data.FoodItem
import nilezia.app.foodorder.ui.repository.OrderRepository

interface CartOrderContract {

    interface View : BaseMvpView {


        fun onUpdateCartAdapter(cartOrders: MutableList<FoodItem>)

        fun onPaymentSuccess()

        fun showOrderInCart()

        fun hideOrderInCart()

        fun hasItem(): Boolean?

    }

    interface Presenter : BaseMvpPresenter<View> {

        fun registerRepository(cartOrders: MutableList<FoodItem>, repository: OrderRepository)

        fun getCardOrder(): MutableList<FoodItem>

        fun updateCardOrder(cartOrders: MutableList<FoodItem>)

        fun confirmCartOrder(cartOrders: MutableList<FoodItem>)

        fun updateCartView()
    }

}
