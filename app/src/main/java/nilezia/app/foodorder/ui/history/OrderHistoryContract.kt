package nilezia.app.foodorder.ui.history

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.model.HistoryItem
import nilezia.app.foodorder.ui.repository.OrderRepository

interface OrderHistoryContract {

    interface View : BaseMvpView {

        fun onUpdateHistoryList(items: MutableList<HistoryItem>)
    }

    interface Presenter : BaseMvpPresenter<View> {

        fun registerRepository(repository: OrderRepository)

        fun requestHistory()

    }

}
