package nilezia.app.foodorder.ui.food

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_order.*
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpFragment
import nilezia.app.foodorder.dialog.DialogManager
import nilezia.app.foodorder.model.FoodItem
import nilezia.app.foodorder.ui.MainActivityContract
import nilezia.app.foodorder.ui.food.adapter.MyHolder
import nilezia.app.foodorder.ui.food.adapter.FoodAdapter
import nilezia.app.foodorder.ui.repository.OrderRepository

class FoodProductFragment : BaseMvpFragment<FoodProductContract.View, FoodProductContract.Presenter>(), FoodProductContract.View {
    private lateinit var orderAdapter: FoodAdapter
    override var mPresenter: FoodProductContract.Presenter = FoodProductPresenter()

    companion object {
        fun newInstance() = FoodProductFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

    override fun setupLayout(): Int = R.layout.fragment_order

    override fun bindView(view: View) {


    }

    override fun setupView() {


    }

    override fun setupInstance() {
        setupAdapter()
        setupRecyclerView()
        setupSwiftRefresh()
        mPresenter.registerRepository(OrderRepository(context!!))
        mPresenter.requestOrders()

    }

    private fun setupSwiftRefresh() {
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = true
            mPresenter.requestOrders()

        }

    }

    private fun setupRecyclerView() {
        val layout = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.apply {
            layoutManager = layout
            adapter = orderAdapter
        }
    }

    private fun setupAdapter() {
        orderAdapter = FoodAdapter(onOrderItemClick())
    }

    override fun onRestoreInstanceState(bundle: Bundle) {

    }

    override fun onAddOrderToCartEvent(order: FoodItem) {
        val listener = activity as (MainActivityContract.View)
        listener.onAddOrderToCartEvent(order)

    }

    override fun onRemoveOrderFromCartEvent(order: FoodItem) {

        val listener = activity as (MainActivityContract.View)
        listener.onRemoveOrderFromCartEvent(order)
    }

    override fun onShowErrorDialog(message: String?) {
        DialogManager.showAlertDialog(context!!, message!!).show()
    }

    private fun onOrderItemClick(): MyHolder.OrderClickListener =
            object : MyHolder.OrderClickListener {

                override fun onClickAdded(order: FoodItem, position: Int) {

                    mPresenter.removeOrderFromCart(order, position)
                }

                override fun onClickOder(order: FoodItem, position: Int) {
                    Toast.makeText(context, "Item ${order.name}", Toast.LENGTH_SHORT).show()
                }

                override fun onClickOrderToCart(order: FoodItem, position: Int) {
                    mPresenter.addOrderItemToCart(order, position)

                }

                override fun onItemEmpty() {
                    Toast.makeText(context, "Item not enough", Toast.LENGTH_SHORT).show()
                }
            }

    override fun updateOrderItemFromCart() {
        mPresenter.requestOrders()

    }

    override fun updateOrderItemRequest(orders: MutableList<FoodItem>) {
        swipeRefresh.isRefreshing = false
        orderAdapter.orders = orders
        orderAdapter.notifyDataSetChanged()

    }
}