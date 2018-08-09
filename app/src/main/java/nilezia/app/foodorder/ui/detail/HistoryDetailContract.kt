package nilezia.app.foodorder.ui.detail

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.model.FoodItem
import nilezia.app.foodorder.model.HistoryItem
import nilezia.app.foodorder.ui.repository.OrderRepository

interface HistoryDetailContract {

    interface View : BaseMvpView {


    }

    interface Presenter : BaseMvpPresenter<View> {

        fun registerRepository(repository: OrderRepository)

        fun requestHistory()

    }
}
