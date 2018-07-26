package nilezia.app.foodorder.ui.order

import nilezia.app.foodorder.base.BaseMvpPresenterImp
import nilezia.app.foodorder.model.Order
import nilezia.app.foodorder.ui.repository.OrderRepository

class OrderPresenter : BaseMvpPresenterImp<OrderContract.View>(),OrderContract.Presenter{

    lateinit var mRepository: OrderRepository

    override fun registerRepository(repository: OrderRepository) {
        this.mRepository = repository
    }

    override fun getOrders(): MutableList<Order>? = null

    override fun getOrderInCart(): MutableList<Order>? = null

    override fun confirmPay() {

    }
}