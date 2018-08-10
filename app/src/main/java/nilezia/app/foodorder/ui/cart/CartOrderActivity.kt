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
import nilezia.app.foodorder.model.FoodItem
import nilezia.app.foodorder.ui.MainActivity
import nilezia.app.foodorder.ui.cart.adapter.CartAdapter
import nilezia.app.foodorder.ui.cart.adapter.CartViewHolder
import nilezia.app.foodorder.ui.repository.OrderRepository
import org.parceler.Parcels


class CartOrderActivity : BaseMvpActivity<CartOrderContract.View, CartOrderContract.Presenter>(), CartOrderContract.View {
    private lateinit var loadingDialog: ProgressDialog
    private lateinit var mAdapter: CartAdapter

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
        var cardOrders = Parcels.unwrap<MutableList<FoodItem>>(intent.getParcelableExtra(MainActivity.ORDER_INTENT_KEY))
        if (cardOrders.isEmpty()) cardOrders = mutableListOf()
        mPresenter.registerRepository(cardOrders, OrderRepository(this@CartOrderActivity))
        mPresenter.updateCardOrder(mPresenter.getCardOrder())
        mPresenter.updateCartView()
        updateTotalPrice()
    }

    override fun onUpdateCartAdapter(cartOrders: MutableList<FoodItem>?) {
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
        mAdapter = CartAdapter( onClickCartItem())
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

/*    private fun onClickCartItem(): CartViewHolder.CartClickListener = object : CartViewHolder.CartClickListener {
        override fun onClickIncreaseOrder(order: FoodItem, position: Int) {
            Log.d("itemClick", "${order.name} : ราคารวม ${order.amount * order.price}")
            mPresenter.updateCartView()
            updateTotalPrice()
        }

        override fun onClickDecreaseOrder(order: FoodItem, position: Int) {
            Log.d("itemClick", "${order.name} : ราคารวม ${order.amount * order.price}")
            mPresenter.updateCartView()
            updateTotalPrice()
        }

        override fun onClickDeleteOrder(order: FoodItem, position: Int) {
            mAdapter.removeOrder(order, position)
            mPresenter.updateCartView()
            updateTotalPrice()
        }
    }*/

    private fun onClickCartItem(): CartViewHolder.CartClickListener = object : CartViewHolder.CartClickListener {
        override fun onClickIncreaseOrder(): (FoodItem, Int) -> Unit = { order, _ ->
            Log.d("itemClick", "${order.name} : ราคารวม ${order.amount * order.price}")
            mPresenter.updateCartView()
            updateTotalPrice()

        }

        override fun onClickDecreaseOrder(): (FoodItem, Int) -> Unit = { order, _ ->
            Log.d("itemClick", "${order.name} : ราคารวม ${order.amount * order.price}")
            mPresenter.updateCartView()
            updateTotalPrice()
        }

        override fun onClickDeleteOrder(): (FoodItem, Int) -> Unit = { order, position ->
            mAdapter.removeOrder(order, position)
            mPresenter.updateCartView()
            updateTotalPrice()
        }

    }

//    private fun onClickIncreaseOrder(): (FoodItem, Int) -> Unit = { order, _ ->
//        Log.d("itemClick", "${order.name} : ราคารวม ${order.amount * order.price}")
//        mPresenter.updateCartView()
//        updateTotalPrice()
//    }
//
//    private fun onClickDecreaseOrder(): (FoodItem, Int) -> Unit = { orders, _ ->
//        Log.d("itemClick", "${orders.name} : ราคารวม ${orders.amount * orders.price}")
//        mPresenter.updateCartView()
//        updateTotalPrice()
//    }
//
//    private fun onClickDeleteOrder(): (FoodItem, Int) -> Unit = { orders, position ->
//        mAdapter.removeOrder(orders, position)
//        mPresenter.updateCartView()
//        updateTotalPrice()
//    }

    private fun onConfirmClick() {
        loadingDialog = ProgressDialog.show(this, "", "Loading...", true, false);
        mPresenter.confirmCartOrder(mAdapter.orders)

    }
}
