package nilezia.app.foodorder.ui.cart;

import nilezia.app.foodorder.base.BaseMvpPresenterImp
import nilezia.app.foodorder.http.CallbackHttp
import nilezia.app.foodorder.model.OrderItem
import nilezia.app.foodorder.ui.repository.CartRepository


class CartOrderPresenter : BaseMvpPresenterImp<CartOrderContract.View>(), CartOrderContract.Presenter {


    private lateinit var mRepository: CartRepository
    private var mOrderItem: MutableList<OrderItem> = mutableListOf()

    override fun registerRepository(cartOrders: MutableList<OrderItem>?, cartRepository: CartRepository) {

        this.mOrderItem = cartOrders!!
        this.mRepository = cartRepository
    }


    override fun updateCardOrder(cartOrders: MutableList<OrderItem>?) {
        mOrderItem = cartOrders!!


    }


    override fun getCardOrder(): MutableList<OrderItem>? = mOrderItem

    override fun confirmCartOrder(cartOrders: MutableList<OrderItem>?) {


        mRepository.updateCartOrderToFirebase(cartOrders)


    }

}
