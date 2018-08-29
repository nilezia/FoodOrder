package nilezia.app.foodorder.ui.cart

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.cart_order_activity.*
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpActivity
import nilezia.app.foodorder.dialog.DialogManager
import nilezia.app.foodorder.model.FoodItem
import nilezia.app.foodorder.ui.MainActivity
import nilezia.app.foodorder.ui.cart.adapter.CartAdapter
import nilezia.app.foodorder.ui.cart.adapter.CartViewHolder
import nilezia.app.foodorder.ui.repository.OrderRepository
import org.parceler.Parcels


class CartOrderActivity : BaseMvpActivity<CartOrderContract.View, CartOrderContract.Presenter>(), CartOrderContract.View {


    private lateinit var loadingDialog: ProgressDialog
    private lateinit var mAdapter: CartAdapter

    override fun initial() {

    }

    override fun setupLayout(): Int = R.layout.cart_order_activity

    override fun bindView() {

    }

    override fun setupView() {
        setupAdapter()
        btnConfirm.setOnClickListener {
            onConfirmClick()
        }
    }

    override fun setupInstance() {
        var cardOrders = Parcels.unwrap<MutableList<FoodItem>>(intent.getParcelableExtra(MainActivity.ORDER_INTENT_KEY))
        if (cardOrders.isEmpty()) cardOrders = mutableListOf()
        getPresenter().registerRepository(cardOrders, OrderRepository())
        getPresenter().registerRepository(cardOrders, OrderRepository())
        getPresenter().updateCardOrder(getPresenter().getCardOrder())
        getPresenter().updateCartView()
        updateTotalPrice()
    }

    override fun createPresenter(): CartOrderContract.Presenter = CartOrderPresenter.create()

    override fun onUpdateCartAdapter(cartOrders: MutableList<FoodItem>) {
        mAdapter.orders = cartOrders
        mAdapter.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(MainActivity.ORDER_INTENT_KEY, Parcels.wrap(mAdapter.orders))
    }

    override fun onRestoreInstanceState(bundle: Bundle) {
        mAdapter.orders = Parcels.unwrap(bundle.getParcelable(MainActivity.ORDER_INTENT_KEY))
    }

    override fun showOrderInCart() {
        btnConfirm.isEnabled = true
    }

    override fun hideOrderInCart() {
        btnConfirm.isEnabled = false
    }

    override fun onPaymentSuccess() {
        loadingDialog.dismiss()
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onBackPressed() {

        if (mAdapter.hasItem()) {
            DialogManager.showQuestionDialog(this@CartOrderActivity, positiveListener).show()
        } else {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private var positiveListener = DialogInterface.OnClickListener { _, _ ->
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun hasItem(): Boolean = mAdapter.hasItem()

    private fun setupAdapter() {
        mAdapter = CartAdapter()
        mAdapter.listener = onClickCartItem()
        rvCart.apply {
            layoutManager = LinearLayoutManager(this@CartOrderActivity)
                    .apply {
                        orientation = LinearLayoutManager.VERTICAL
                    }
            adapter = mAdapter
        }

    }

    @SuppressLint("SetTextI18n")
    private fun updateTotalPrice() {

        tvTotalPrice.text = "${mAdapter.getPriceTotal()} ฿"

    }

    private fun onClickCartItem(): CartViewHolder.CartClickListener = object : CartViewHolder.CartClickListener {
        override fun onClickIncreaseOrder(): (FoodItem, Int) -> Unit = { order, _ ->
            Log.d("itemClick", "${order.name} : ราคารวม ${order.amount * order.price}")
            getPresenter().updateCartView()
            updateTotalPrice()

        }

        override fun onClickDecreaseOrder(): (FoodItem, Int) -> Unit = { order, _ ->
            Log.d("itemClick", "${order.name} : ราคารวม ${order.amount * order.price}")
            getPresenter().updateCartView()
            updateTotalPrice()
        }

        override fun onClickDeleteOrder(): (FoodItem, Int) -> Unit = { order, position ->
            mAdapter.removeOrder(order, position)
            getPresenter().updateCartView()
            updateTotalPrice()
        }

    }

    private fun onConfirmClick() {
        loadingDialog = ProgressDialog.show(this, "", "Loading...", true, false);
        getPresenter().confirmCartOrder(mAdapter.orders)

    }
}
