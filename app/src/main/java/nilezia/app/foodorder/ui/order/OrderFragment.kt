package nilezia.app.foodorder.ui.order

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_order.*
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpFragment
import nilezia.app.foodorder.model.Order
import nilezia.app.foodorder.ui.adapter.OrderAdapter

class OrderFragment : BaseMvpFragment<OrderContract.View, OrderContract.Presenter>(), OrderContract.View {

    private lateinit var orderAdapter: OrderAdapter
    override var mPresenter: OrderContract.Presenter = OrderPresenter()

    companion object {
        fun newInstance() = OrderFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

    override fun setupLayout(): Int = R.layout.fragment_order

    override fun setupView(v: View) {


    }

    override fun setupInstance() {
        orderAdapter = OrderAdapter(onItemClick())
        val linLayoutManager = LinearLayoutManager(context)
        linLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.apply {
            layoutManager = linLayoutManager
            adapter = orderAdapter
        }
        orderAdapter.notifyDataSetChanged()
    }

    override fun onRestoreInstanceState(bundle: Bundle) {

    }

    override fun onItemClick(): (Order?) -> Unit = {

        Toast.makeText(context, it?.name, Toast.LENGTH_LONG).show()
    }


}