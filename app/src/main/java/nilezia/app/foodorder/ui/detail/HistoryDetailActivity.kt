package nilezia.app.foodorder.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail.*
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpActivity
import nilezia.app.foodorder.model.FoodItem
import nilezia.app.foodorder.model.HistoryItem
import nilezia.app.foodorder.ui.MainActivity
import nilezia.app.foodorder.ui.detail.adapter.DetailAdapter
import org.parceler.Parcels


class HistoryDetailActivity : BaseMvpActivity<HistoryDetailContract.View, HistoryDetailContract.Presenter>(), HistoryDetailContract.View {

    override var mPresenter: HistoryDetailContract.Presenter = HistoryDetailPresenter()
    lateinit var mAdapter: DetailAdapter
    private var historyItems: HistoryItem? = null


    override fun setupLayout(): Int = R.layout.activity_detail


    override fun setupView() {
        setupAdapter()
        setupRecyclerView()
        imgClose.setOnClickListener{
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
        historyItems = Parcels.unwrap(intent.getParcelableExtra("history_detail")) as HistoryItem?
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

    override fun onRestoreInstanceState(bundle: Bundle) {

    }

    private fun onUpdateHistoryList(items: MutableList<FoodItem>) {
        mAdapter.item = items
        mAdapter.notifyDataSetChanged()
    }


}
