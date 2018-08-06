package nilezia.app.foodorder.ui.cart

import android.annotation.SuppressLint
import android.app.Activity
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
import nilezia.app.foodorder.ui.repository.CartRepository
import org.parceler.Parcels


class CartOrderActivity : BaseMvpActivity<CartOrderContract.View, CartOrderContract.Presenter>(), CartOrderContract.View {
    private lateinit var cardOrders: MutableList<OrderItem>
    lateinit var mAdapter: CartAdapter
    override var mPresenter: CartOrderContract.Presenter = CartOrderPresenter()

    override fun initial() {

    }

    override fun setupLayout(): Int = R.layout.cart_order_activity

    override fun bindView() {

    }

    override fun setupView() {
        mAdapter = CartAdapter(onClickCartItem())
        rvCart.apply {
            layoutManager = LinearLayoutManager(this@CartOrderActivity)
                    .apply {
                        orientation = LinearLayoutManager.VERTICAL
                    }
            adapter = mAdapter
        }

        btnConfirm.setOnClickListener {
            onConfirmClick()
        }
    }


    override fun setupInstance() {
        cardOrders = Parcels.unwrap<MutableList<OrderItem>>(intent.getParcelableExtra(MainActivity.ORDER_INTENT_KEY))
        if (cardOrders.isEmpty()) cardOrders = mutableListOf()
        mPresenter.registerRepository(cardOrders, CartRepository(this@CartOrderActivity))
        mAdapter.orders = mPresenter.getCardOrder()
        mAdapter.notifyDataSetChanged()
        updateTotalPrice()
    }

    override fun onRestoreInstanceState(bundle: Bundle) {

    }

    @SuppressLint("SetTextI18n")
    override fun updateTotalPrice() {

        tvTotalPrice.text = "${mAdapter.getTotal()} ฿"

    }


    private fun onClickCartItem(): CartViewHolder.CartClickListener = object : CartViewHolder.CartClickListener {
        override fun onClickIncreaseOrder(order: OrderItem, position: Int) {
            Log.d("itemClick", "${order.name} : ราคารวม ${order.amount * order.price}")

            updateTotalPrice()

        }

        override fun onClickDecreaseOrder(order: OrderItem, position: Int) {
            Log.d("itemClick", "${order.name} : ราคารวม ${order.amount * order.price}")
            updateTotalPrice()

        }

        override fun onClickDeleteOrder(order: OrderItem, position: Int) {
            mAdapter.deleteOrder(order, position)
            updateTotalPrice()

        }
    }


    private fun onConfirmClick() {

        mPresenter.confirmCartOrder(mAdapter.orders)


    }

    override fun onBackPressed() {


        DialogManager.showQuestionDialog(this@CartOrderActivity).apply {

            setMessage("Discard shipping?")
            this.setPositiveButton("ОК") { _, _ ->
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
            this.setNegativeButton("CANCEL") { dialog, _ -> dialog.dismiss() }
            show()
        }

    }

}
