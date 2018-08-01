package nilezia.app.foodorder.ui.order

import nilezia.app.foodorder.base.BaseMvpPresenterImp
import nilezia.app.foodorder.model.OrderItem
import nilezia.app.foodorder.ui.repository.OrderRepository

class OrderPresenter : BaseMvpPresenterImp<OrderContract.View>(), OrderContract.Presenter {

    private lateinit var mRepository: OrderRepository


    override fun registerRepository(repository: OrderRepository) {
        this.mRepository = repository
    }

    override fun getOrders(): MutableList<OrderItem>? = mRepository.getOrders()!!

    override fun addOrderItemToCart(orderItem: OrderItem, position: Int) {

        mView?.onAddOrderToCartEvent(orderItem)
    }

    override fun removeOrderFromCart(orderItem: OrderItem, position: Int) {
        mView?.onRemoveOrderFromCartEvent(orderItem)
    }

}