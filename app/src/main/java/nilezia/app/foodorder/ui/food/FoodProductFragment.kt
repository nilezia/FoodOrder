package nilezia.app.foodorder.ui.food

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_food.*
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpFragment
import nilezia.app.foodorder.dialog.DialogManager
import nilezia.app.foodorder.model.FoodItem
import nilezia.app.foodorder.ui.MainActivity
import nilezia.app.foodorder.ui.MainActivityContract
import nilezia.app.foodorder.ui.food.adapter.ViewHolder
import nilezia.app.foodorder.ui.food.adapter.FoodAdapter
import nilezia.app.foodorder.ui.repository.OrderRepository
import org.parceler.Parcels

class FoodProductFragment : BaseMvpFragment<FoodProductContract.View, FoodProductContract.Presenter>(), FoodProductContract.View {
    private lateinit var orderAdapter: FoodAdapter
    override var mPresenter: FoodProductContract.Presenter = FoodProductPresenter()

    companion object {
        fun newInstance() = FoodProductFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

    override fun setupLayout(): Int = R.layout.fragment_food

    override fun bindView(view: View) {


    }

    override fun setupView() {


    }

    override fun setupInstance() {
        setupAdapter()
        setupRecyclerView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.registerRepository(OrderRepository(context!!))
        if (orderAdapter.foodOrders?.size == 0) {
            mPresenter.requestOrders()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(MainActivity.ORDER_INTENT_KEY, Parcels.wrap(orderAdapter.foodOrders))
    }

    override fun onRestoreInstanceState(bundle: Bundle) {
        val foodOrders = Parcels.unwrap<MutableList<FoodItem>>(bundle.getParcelable(MainActivity.ORDER_INTENT_KEY))
        orderAdapter.foodOrders = foodOrders
        orderAdapter.notifyDataSetChanged()


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

    private fun onOrderItemClick(): ViewHolder.OrderClickListener =
            object : ViewHolder.OrderClickListener {

                override fun onClickAdded(item: FoodItem, position: Int) {
                    mPresenter.removeOrderFromCart(item, position)
                }

                override fun onClickOder(item: FoodItem, position: Int) {
                    Toast.makeText(context, "Item ${item.name}", Toast.LENGTH_SHORT).show()
                }

                override fun onClickOrderToCart(item: FoodItem, position: Int) {
                    mPresenter.addOrderItemToCart(item, position)
                }

                override fun onItemEmpty() {
                    Toast.makeText(context, "Item not enough", Toast.LENGTH_SHORT).show()
                }
            }

    override fun updateOrderItemFromCart() {
        mPresenter.requestOrders()

    }

    override fun updateOrderItemRequest(orders: MutableList<FoodItem>) {
        orderAdapter.foodOrders = orders
        orderAdapter.notifyDataSetChanged()

    }
}