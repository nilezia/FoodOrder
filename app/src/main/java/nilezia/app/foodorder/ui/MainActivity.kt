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
import nilezia.app.foodorder.model.FoodItem
import nilezia.app.foodorder.model.HistoryItem
import nilezia.app.foodorder.ui.cart.CartOrderActivity
import nilezia.app.foodorder.ui.detail.HistoryDetailActivity
import nilezia.app.foodorder.ui.food.FoodProductFragment
import nilezia.app.foodorder.ui.pager.MainPagerAdapter
import org.parceler.Parcels


class MainActivity : BaseMvpActivity<MainActivityContract.View, MainActivityContract.Presenter>(), MainActivityContract.View {

    companion object {
        const val ORDER_INTENT_KEY = "order_to_cart"
        const val ORDER_REQUEST_CODE = 100
        const val CURRENT_PAGE = "current_page"
    }

    private lateinit var adapter: MainPagerAdapter

    override var mPresenter: MainActivityContract.Presenter = MainActivityPresenter()

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
        adapter = MainPagerAdapter(supportFragmentManager)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(tabSelectedListener)
        viewPager.adapter = adapter

    }

    override fun initial() {

    }

    override fun setupLayout(): Int = R.layout.activity_main

    override fun setupView() {

        imgCartMenu.setOnClickListener {

            mPresenter.onClickMenuCart()
        }
    }

    override fun bindView() {

    }

    override fun setupInstance() {

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(ORDER_INTENT_KEY, Parcels.wrap(mPresenter.getOrderFromCart()))
        outState?.putInt(CURRENT_PAGE, viewPager.currentItem)

    }

    override fun onRestoreInstanceState(bundle: Bundle) {
        val cartOrder = Parcels.unwrap<MutableList<FoodItem>>(bundle.getParcelable(ORDER_INTENT_KEY))
        val currentPage = bundle.getInt(CURRENT_PAGE, 0)
        viewPager.currentItem = currentPage
        mPresenter.updateOrderFromCart(cartOrder)

        updateCartNotification()
    }

    override fun onAddOrderToCartEvent(order: FoodItem) {
        mPresenter.addOrderToCart(order)
        updateCartNotification()
        Toast.makeText(applicationContext, "${order.name} Add to cart", Toast.LENGTH_SHORT).show()

    }

    override fun onRemoveOrderFromCartEvent(order: FoodItem) {
        mPresenter.removeOrderFromCart(order)
        updateCartNotification()
        Toast.makeText(applicationContext, "${order.name} Remove to cart", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ORDER_REQUEST_CODE) {
            mPresenter.updateOrderFromCart(mutableListOf())
            if (resultCode == Activity.RESULT_OK) {
                val page = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.currentItem)
                updateCartNotification()
                if (viewPager.currentItem == 0 && page != null) {
                    (page as FoodProductFragment).updateOrderItemFromCart()
                }
            } else {
                updateCartNotification()
            }
        }
    }

    override fun goToCartActivity() = startActivityForResult(Intent(this@MainActivity, CartOrderActivity::class.java).apply {
        putExtra(ORDER_INTENT_KEY, Parcels.wrap(mPresenter.getOrderFromCart()))
    }, 100)

    override fun goToDetail(historyItem: HistoryItem) = startActivity(Intent(this@MainActivity, HistoryDetailActivity::class.java).apply {
        putExtra("history_detail", Parcels.wrap(historyItem))
    })

    private fun updateCartNotification() {
        val count = mPresenter.getOrderCount()
        tvOrderCount.apply {
            visibility = if (count == 0) View.GONE else View.VISIBLE
            text = "$count"
        }
    }

    private var tabSelectedListener: TabLayout.OnTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            viewPager.currentItem = tab.position

            if (viewPager.currentItem == 1) {
                toolbarCart.visibility = View.GONE
            } else {
                toolbarCart.visibility = View.VISIBLE
            }

        }

        override fun onTabUnselected(tab: TabLayout.Tab) {

        }

        override fun onTabReselected(tab: TabLayout.Tab) {

        }
    }


}
