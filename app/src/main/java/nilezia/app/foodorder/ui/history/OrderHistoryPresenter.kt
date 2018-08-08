package nilezia.app.foodorder.ui.history

import nilezia.app.foodorder.base.BaseMvpPresenterImp
import nilezia.app.foodorder.http.CallbackHttp
import nilezia.app.foodorder.model.FoodItem
import nilezia.app.foodorder.model.HistoryItem
import nilezia.app.foodorder.ui.repository.OrderRepository


class OrderHistoryPresenter : BaseMvpPresenterImp<OrderHistoryContract.View>(), OrderHistoryContract.Presenter {

    private lateinit var mRepository: OrderRepository
    override fun registerRepository(repository: OrderRepository) {
        this.mRepository = repository

    }

    override fun requestHistory() {
        mRepository.requestHistoryFromFirebase(object : CallbackHttp<MutableList<HistoryItem>> {
            override fun onSuccess(response: MutableList<HistoryItem>) {
                mView?.onUpdateHistoryList(response)

            }

            override fun onFailed(txt: String) {

            }
        })

    }

}
