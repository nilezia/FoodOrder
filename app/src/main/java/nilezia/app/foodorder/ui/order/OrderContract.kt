package nilezia.app.foodorder.ui.order

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.model.Order
import nilezia.app.foodorder.ui.repository.OrderRepository

interface OrderContract {


    interface View : BaseMvpView {

        fun onItemClick(): (Order) -> Unit
    }

    interface Presenter : BaseMvpPresenter<View>{
        fun registerRepository(repository: OrderRepository)

        fun getOrders(): MutableList<Order>?

        fun getOrderInCart(): MutableList<Order>?

        fun confirmPay()
    }


}