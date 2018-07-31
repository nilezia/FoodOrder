package nilezia.app.foodorder.ui.cart;

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView

interface CartOrderContract {

    interface View : BaseMvpView

    interface Presenter : BaseMvpPresenter<View> {
        fun onStart()
    }

}
