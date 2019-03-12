package nilezia.app.foodorder.ui.detail

import nilezia.app.foodorder.base.BaseMvpPresenterImp
import nilezia.app.foodorder.ui.repository.OrderRepository


class HistoryDetailPresenter : BaseMvpPresenterImp<HistoryDetailContract.View>(), HistoryDetailContract.Presenter {

    companion object {

        fun create(): HistoryDetailContract.Presenter = HistoryDetailPresenter()

    }
    private lateinit var mRepository: OrderRepository
    override fun registerRepository(repository: OrderRepository) {
        this.mRepository = repository

    }

    override fun requestHistory() {


    }

}
