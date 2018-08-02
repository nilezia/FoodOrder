package nilezia.app.foodorder.ui.history

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView

interface OrderHistoryContract {

    interface View : BaseMvpView

    interface Presenter : BaseMvpPresenter<View> {
        fun onStart()
    }

}
