package nilezia.app.foodorder.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpActivity
import nilezia.app.foodorder.model.OrderItem
import nilezia.app.foodorder.ui.cart.CartOrderActivity
import nilezia.app.foodorder.ui.order.OrderFragment
import nilezia.app.foodorder.ui.pager.MainPagerAdapter
import org.parceler.Parcels


class MainActivity : BaseMvpActivity<MainActivityContract.View, MainActivityContract.Presenter>(), MainActivityContract.View {

    override var mPresenter: MainActivityContract.Presenter = MainActivityPresenter()

    companion object {
        const val ORDER_INTENT_KEY = "order_to_cart"
        const val ORDER_REQUEST_CODE = 100

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTab()
        setupViewPager()
    }

    private fun setupTab() {

        tabLayout.apply {
            addTab(tabLayout.newTab().setText("Order"))
            addTab(tabLayout.newTab().setText("History"))
            tabGravity = TabLayout.GRAVITY_FILL
        }
    }

    private fun setupViewPager() {
        val adapter = MainPagerAdapter(supportFragmentManager)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(tabSelectedListener)
        viewPager.adapter = adapter

    }

    override fun initial() {

        //  supportFragmentManager.beginTransaction().replace(R.id.container, OrderFragment.newInstance(), null).commit()
    }

    override fun setupLayout(): Int = R.layout.activity_main

    override fun setupView() {

        toolbar_cart.setOnClickListener {

            mPresenter.onClickMenuCart()
        }
    }

    override fun bindView() {

    }

    override fun setupInstance() {

    }

    override fun onRestoreInstanceState(bundle: Bundle) {

    }

    override fun onAddOrderToCartEvent(order: OrderItem) {
        mPresenter.addOrderToCart(order)
        updateCartNotification()
        Toast.makeText(applicationContext, "${order.name} Add to cart", Toast.LENGTH_SHORT).show()

    }

    override fun onRemoveOrderFromCartEvent(order: OrderItem) {
        mPresenter.removeOrderFromCart(order)
        updateCartNotification()
        Toast.makeText(applicationContext, "${order.name} Remove to cart", Toast.LENGTH_SHORT).show()
    }

    private fun updateCartNotification() {
        val count = mPresenter.getOrderCount()
        tv_product_count.apply {
            visibility = if (count == 0) View.GONE else View.VISIBLE
            text = "$count"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ORDER_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {
                val page = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.currentItem)
                mPresenter.updateOrderFromCart(mutableListOf())
                updateCartNotification()
                if (viewPager.currentItem == 0 && page != null) {
                    (page as OrderFragment).updateOrderItemFromCart()
                }
            }
        }
    }

    override fun goToCartActivity() = startActivityForResult(Intent(this@MainActivity, CartOrderActivity::class.java).apply {
        putExtra(ORDER_INTENT_KEY, Parcels.wrap(mPresenter.getOrderFromCart()))
    }, 100)

    private var tabSelectedListener: TabLayout.OnTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            viewPager.currentItem = tab.position
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {

        }

        override fun onTabReselected(tab: TabLayout.Tab) {

        }
    }


}
