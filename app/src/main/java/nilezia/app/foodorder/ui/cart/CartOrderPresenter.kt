package nilezia.app.foodorder.ui.cart;

import nilezia.app.foodorder.base.BaseMvpPresenterImp
import nilezia.app.foodorder.data.FoodItem
import nilezia.app.foodorder.ui.repository.OrderRepository


class CartOrderPresenter : BaseMvpPresenterImp<CartOrderContract.View>(), CartOrderContract.Presenter {

    private lateinit var mRepository: OrderRepository

    private var mOrderItem: MutableList<FoodItem> = mutableListOf()

    companion object {

        fun create(): CartOrderContract.Presenter = CartOrderPresenter()

    }

    override fun registerRepository(cartOrders: MutableList<FoodItem>, repository: OrderRepository) {
        this.mOrderItem = cartOrders
        this.mRepository = repository
    }

    override fun updateCardOrder(cartOrders: MutableList<FoodItem>) {
        getView().onUpdateCartAdapter(cartOrders)
    }

    override fun getCardOrder(): MutableList<FoodItem> = mOrderItem

    override fun confirmCartOrder(cartOrders: MutableList<FoodItem>) {
        cartOrders.forEach {
           it.isAdded = false


        }
        mRepository.updateCartOrderToFirebase({

            getView().onPaymentSuccess()

        },cartOrders)
    }

    override fun updateCartView() {

        if (getView().hasItem()!!) {

            getView().showOrderInCart()

        } else {
            getView().hideOrderInCart()
        }
    }

}
