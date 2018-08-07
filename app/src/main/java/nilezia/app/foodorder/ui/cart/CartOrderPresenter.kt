package nilezia.app.foodorder.ui.cart;

import nilezia.app.foodorder.base.BaseMvpPresenterImp
import nilezia.app.foodorder.model.OrderItem
import nilezia.app.foodorder.ui.repository.OrderRepository


class CartOrderPresenter : BaseMvpPresenterImp<CartOrderContract.View>(), CartOrderContract.Presenter {


    private lateinit var mRepository: OrderRepository
    private var mOrderItem: MutableList<OrderItem> = mutableListOf()

    override fun registerRepository(cartOrders: MutableList<OrderItem>?, repository: OrderRepository) {
        this.mOrderItem = cartOrders!!
        this.mRepository = repository
    }

    override fun updateCardOrder(cartOrders: MutableList<OrderItem>?) {
        mView?.onUpdateCartAdapter(cartOrders)
    }

    override fun getCardOrder(): MutableList<OrderItem>? = mOrderItem

    override fun confirmCartOrder(cartOrders: MutableList<OrderItem>?) {
        mRepository.updateCartOrderToFirebase(updateRepo(), cartOrders)
    }

    override fun updateCartView() {

        if (mView?.hasItem()!!) {

            mView?.showOrderInCart()

        } else {
            mView?.hideOrderInCart()
        }
    }

    private fun updateRepo(): () -> Unit = {

        mView?.onPaymentSuccess()
    }

}
