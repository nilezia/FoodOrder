package nilezia.app.foodorder.ui

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.model.OrderItem

interface MainActivityContract {

    interface View : BaseMvpView {

         fun onAddOrderToCartEvent(order: OrderItem)

         fun onRemoveOrderFromCartEvent(order: OrderItem)
    }

    interface Presenter : BaseMvpPresenter<View> {


    }
}