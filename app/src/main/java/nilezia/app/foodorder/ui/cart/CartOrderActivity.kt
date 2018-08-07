package nilezia.app.foodorder.ui.cart

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.cart_order_activity.*
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpActivity
import nilezia.app.foodorder.dialog.DialogManager
import nilezia.app.foodorder.model.OrderItem
import nilezia.app.foodorder.ui.MainActivity
import nilezia.app.foodorder.ui.cart.adapter.CartAdapter
import nilezia.app.foodorder.ui.cart.adapter.CartViewHolder
import nilezia.app.foodorder.ui.repository.OrderRepository
import org.parceler.Parcels


class CartOrderActivity : BaseMvpActivity<CartOrderContract.View, CartOrderContract.Presenter>(), CartOrderContract.View {

    private lateinit var cardOrders: MutableList<OrderItem>
    private lateinit var loadingDialog: ProgressDialog
    lateinit var mAdapter: CartAdapter

    override var mPresenter: CartOrderContract.Presenter = CartOrderPresenter()

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
        cardOrders = Parcels.unwrap<MutableList<OrderItem>>(intent.getParcelableExtra(MainActivity.ORDER_INTENT_KEY))
        if (cardOrders.isEmpty()) cardOrders = mutableListOf()
        mPresenter.registerRepository(cardOrders, OrderRepository(this@CartOrderActivity))
        mPresenter.updateCardOrder(mPresenter.getCardOrder())
        mPresenter.updateCartView()
        updateTotalPrice()
    }

    override fun onUpdateCartAdapter(cartOrders: MutableList<OrderItem>?) {
        mAdapter.orders = cartOrders
        mAdapter.notifyDataSetChanged()
    }

    override fun onRestoreInstanceState(bundle: Bundle) {

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
            DialogManager.showQuestionDialog(this@CartOrderActivity).apply {

                setMessage("Discard shipping?")
                this.setPositiveButton("ОК") { _, _ ->
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                this.setNegativeButton("CANCEL") { dialog, _ -> dialog.dismiss() }
                show()
            }
        } else {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }


    }

    override fun hasItem(): Boolean = mAdapter.hasItem()

    private fun setupAdapter() {
        mAdapter = CartAdapter(onClickCartItem())
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
        override fun onClickIncreaseOrder(order: OrderItem, position: Int) {
            Log.d("itemClick", "${order.name} : ราคารวม ${order.amount * order.price}")
            mPresenter.updateCartView()
            updateTotalPrice()


        }

        override fun onClickDecreaseOrder(order: OrderItem, position: Int) {
            Log.d("itemClick", "${order.name} : ราคารวม ${order.amount * order.price}")
            mPresenter.updateCartView()
            updateTotalPrice()

        }

        override fun onClickDeleteOrder(order: OrderItem, position: Int) {
            mAdapter.removeOrder(order, position)
            mPresenter.updateCartView()
            updateTotalPrice()

        }
    }

    private fun onConfirmClick() {
        loadingDialog = ProgressDialog.show(this, "", "Loading...", true, false);
        mPresenter.confirmCartOrder(mAdapter.orders)


    }
}
