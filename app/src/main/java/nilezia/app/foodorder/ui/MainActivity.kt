package nilezia.app.foodorder.ui

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpActivity
import nilezia.app.foodorder.model.OrderItem
import nilezia.app.foodorder.ui.order.OrderFragment

class MainActivity : BaseMvpActivity<MainActivityContract.View, MainActivityContract.Presenter>(), MainActivityContract.View {


    override fun initial() {

        supportFragmentManager.beginTransaction().replace(R.id.container, OrderFragment.newInstance(), null).commit()
    }

    override var mPresenter: MainActivityContract.Presenter = MainActivityPresenter()


    override fun setupLayout(): Int = R.layout.activity_main


    override fun setupView() {

    }

    override fun setupInstance() {

        setupCart()
    }

    private fun setupCart() {


    }

    override fun onRestoreInstanceState(bundle: Bundle) {

    }

    var orders: MutableList<OrderItem> = mutableListOf()
    override fun onAddOrderToCartEvent(order: OrderItem) {
        orders.add(order)
        updateCartNotification()
        Toast.makeText(applicationContext, "${order.name} Add to cart", Toast.LENGTH_SHORT).show()

    }

    override fun onRemoveOrderFromCartEvent(order: OrderItem) {
        orders.remove(order)
        updateCartNotification()
        Toast.makeText(applicationContext, "${order.name} Remove to cart", Toast.LENGTH_SHORT).show()
    }

    private fun updateCartNotification() {
        val count = orders.size
        tv_product_count.apply {
            visibility = if (count == 0) View.GONE else View.VISIBLE
            text = "$count"
        }

    }

}
