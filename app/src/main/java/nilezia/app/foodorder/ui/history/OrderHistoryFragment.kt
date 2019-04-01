package nilezia.app.foodorder.ui.history

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_history.*
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpFragment
import nilezia.app.foodorder.data.HistoryItem
import nilezia.app.foodorder.ui.MainActivityContract
import nilezia.app.foodorder.ui.detail.HistoryDetailActivity.Companion.HISTORY_DETAIL_KEY
import nilezia.app.foodorder.ui.history.adapter.OrderHistoryAdapter
import nilezia.app.foodorder.ui.repository.OrderRepository
import org.parceler.Parcels


class OrderHistoryFragment : BaseMvpFragment<OrderHistoryContract.View, OrderHistoryContract.Presenter>(), OrderHistoryContract.View {


    private lateinit var mAdapter: OrderHistoryAdapter

    companion object {
        fun newInstance() = OrderHistoryFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

    override fun setupLayout(): Int = R.layout.fragment_history

    override fun bindView(view: View) {


    }

    override fun setupView() {
        setupAdapter()
        setupRecyclerView()
        setupSwiftRefresh()
    }

    private fun setupRecyclerView() {

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
                    .apply {
                        orientation = LinearLayoutManager.VERTICAL
                        reverseLayout = true
                        stackFromEnd = true
                    }
            adapter = mAdapter
        }
    }

    private fun setupAdapter() {
        mAdapter = OrderHistoryAdapter(onItemOrderHistorySelect())

    }

    override fun setupInstance() {
        getPresenter().registerRepository(OrderRepository())
        getPresenter().requestHistory()
    }

    private fun setupSwiftRefresh() {
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = true
            getPresenter().requestHistory()
        }
    }

    override fun createPresenter(): OrderHistoryContract.Presenter = OrderHistoryPresenter.create()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(HISTORY_DETAIL_KEY, Parcels.wrap(mAdapter.historyItems))
    }

    override fun onRestoreInstanceState(bundle: Bundle) {

        mAdapter.historyItems = Parcels.unwrap<MutableList<HistoryItem>>(bundle.getParcelable(HISTORY_DETAIL_KEY))
        mAdapter.notifyDataSetChanged()
    }

    override fun onUpdateHistoryList(items: MutableList<HistoryItem>) {
        swipeRefresh.isRefreshing = false
        mAdapter.historyItems = items
        mAdapter.notifyDataSetChanged()
    }

    private fun onItemOrderHistorySelect(): (HistoryItem) -> Unit = { item->

        val listener = activity as MainActivityContract.View
        listener.goToDetail(item)


    }


}
