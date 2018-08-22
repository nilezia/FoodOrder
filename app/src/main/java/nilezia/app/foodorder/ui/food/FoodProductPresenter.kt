package nilezia.app.foodorder.ui.food

import nilezia.app.foodorder.base.BaseMvpPresenterImp
import nilezia.app.foodorder.http.CallbackHttp
import nilezia.app.foodorder.model.FoodItem
import nilezia.app.foodorder.ui.repository.OrderRepository

class FoodProductPresenter : BaseMvpPresenterImp<FoodProductContract.View>(), FoodProductContract.Presenter {


    private lateinit var mRepository: OrderRepository
    private lateinit var orderItems: MutableList<FoodItem>

    override fun registerRepository(repository: OrderRepository) {
        this.mRepository = repository
    }

    override fun getOrders(): MutableList<FoodItem>? = orderItems

    override fun addOrderItemToCart(orderItem: FoodItem, position: Int) {

        mView?.onAddOrderToCartEvent(orderItem)

    }

    override fun removeOrderFromCart(orderItem: FoodItem, position: Int) {
        mView?.onRemoveOrderFromCartEvent(orderItem)

    }

    override fun requestOrders() {
        orderItems = mutableListOf()
        mRepository.requestOrderFromFirebase(object : CallbackHttp<MutableList<FoodItem>> {
            override fun onSuccess(response: MutableList<FoodItem>) {
                mView?.updateOrderItemRequest(response)
            }

            override fun onFailed(txt: String) {
                mView?.onShowErrorDialog(txt)
            }
        })
    }
}