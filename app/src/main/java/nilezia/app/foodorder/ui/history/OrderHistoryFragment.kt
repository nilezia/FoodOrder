package nilezia.app.foodorder.ui.history

import android.os.Bundle
import android.view.View
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpFragment

class OrderHistoryFragment : BaseMvpFragment<OrderHistoryContract.View, OrderHistoryContract.Presenter>(), OrderHistoryContract.View {
    override var mPresenter: OrderHistoryContract.Presenter = OrderHistoryPresenter()


    companion object {
        fun newInstance() = OrderHistoryFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }


    override fun setupLayout(): Int = R.layout.fragment_order

    override fun setupView(v: View) {

    }

    override fun setupInstance() {

    }

    override fun onRestoreInstanceState(bundle: Bundle) {

    }


}
