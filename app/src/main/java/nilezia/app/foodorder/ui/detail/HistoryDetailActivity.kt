package nilezia.app.foodorder.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail.*
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpActivity
import nilezia.app.foodorder.data.FoodItem
import nilezia.app.foodorder.data.HistoryItem
import nilezia.app.foodorder.ui.detail.adapter.DetailAdapter
import org.parceler.Parcels


class HistoryDetailActivity : BaseMvpActivity<HistoryDetailContract.View, HistoryDetailContract.Presenter>(), HistoryDetailContract.View {

    companion object {
        const val HISTORY_DETAIL_KEY = "history_detail"

    }

    private lateinit var mAdapter: DetailAdapter
    private var historyItems: HistoryItem? = null


    override fun setupLayout(): Int = R.layout.activity_detail


    override fun setupView() {
        setupAdapter()
        setupRecyclerView()
        imgClose.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
                    .apply {
                        orientation = LinearLayoutManager.VERTICAL
                    }
            adapter = mAdapter
        }
    }

    private fun setupAdapter() {

        mAdapter = DetailAdapter()

    }

    override fun setupInstance() {
        historyItems = Parcels.unwrap(intent.getParcelableExtra(HISTORY_DETAIL_KEY)) as HistoryItem?
        setupDisplay()
        onUpdateHistoryList(historyItems?.orders!!)
//        mPresenter.registerRepository(OrderRepository(applicationContext))
//        mPresenter.requestHistory()
    }

    @SuppressLint("SetTextI18n")
    private fun setupDisplay() {
        tvTotalPrice.text = "ราคารวม ${historyItems?.totalPrice}฿"
    }


    override fun initial() {


    }

    override fun bindView() {

    }

    override fun createPresenter(): HistoryDetailContract.Presenter = HistoryDetailPresenter.create()

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(HISTORY_DETAIL_KEY, Parcels.wrap(historyItems))
    }

    override fun onRestoreInstanceState(bundle: Bundle) {
        historyItems = Parcels.unwrap(bundle.getParcelable(HISTORY_DETAIL_KEY))
        onUpdateHistoryList(historyItems?.orders!!)
        setupDisplay()
    }

    private fun onUpdateHistoryList(items: MutableList<FoodItem>) {
        mAdapter.item = items
        mAdapter.notifyDataSetChanged()
    }


}
