package nilezia.app.foodorder.ui.cart;

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.cart_order_activity.*
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpActivity
import nilezia.app.foodorder.model.OrderItem
import nilezia.app.foodorder.ui.MainActivity
import nilezia.app.foodorder.ui.cart.adapter.CartAdapter
import nilezia.app.foodorder.ui.cart.adapter.CartViewHolder
import org.parceler.Parcels

class CartOrderActivity : BaseMvpActivity<CartOrderContract.View, CartOrderContract.Presenter>(), CartOrderContract.View {

    lateinit var mAdapter: CartAdapter
    lateinit var cardOrders: MutableList<OrderItem>
    override var mPresenter: CartOrderContract.Presenter = CartOrderPresenter()

    override fun initial() {

    }

    override fun setupLayout(): Int = R.layout.cart_order_activity

    override fun bindView() {

    }

    override fun setupView() {
        mAdapter = CartAdapter()
        rvCart.apply {
            layoutManager = LinearLayoutManager(this@CartOrderActivity)
                    .apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            adapter = mAdapter
        }
    }

    override fun setupInstance() {
        cardOrders = Parcels.unwrap<MutableList<OrderItem>>(intent.getParcelableExtra(MainActivity.ORDER_INTENT_KEY))
        if(cardOrders.isEmpty()) cardOrders = mutableListOf()
        mPresenter.registerRepository(cardOrders)
        mAdapter.orders = mPresenter.getCardOrder()
        mAdapter.notifyDataSetChanged()
    }

    override fun onRestoreInstanceState(bundle: Bundle) {

    }

    override fun updateTotalPrice() {


    }

    fun onClickCartItem(): CartViewHolder.CartClickListener = object : CartViewHolder.CartClickListener {
        override fun onClickIncreaseOrder(order: OrderItem, position: Int) {

        }

        override fun onClickDecreaseOrder(order: OrderItem, position: Int) {

        }

        override fun onClickRemoveOrder(order: OrderItem, position: Int) {

        }
    }
}
