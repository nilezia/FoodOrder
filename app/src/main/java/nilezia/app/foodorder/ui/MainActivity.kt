package nilezia.app.foodorder.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpActivity
import nilezia.app.foodorder.data.FoodItem
import nilezia.app.foodorder.data.HistoryItem
import nilezia.app.foodorder.ui.cart.CartOrderActivity
import nilezia.app.foodorder.ui.detail.HistoryDetailActivity
import nilezia.app.foodorder.ui.food.FoodProductFragment
import nilezia.app.foodorder.ui.login.LoginActivity
import nilezia.app.foodorder.ui.pager.MainPagerAdapter
import org.parceler.Parcels
import com.bumptech.glide.Glide
import nilezia.app.foodorder.data.UserInfo
import nilezia.app.foodorder.model.UserAuth
import nilezia.app.foodorder.ui.chat.ChatActivity
import nilezia.app.foodorder.ui.repository.OrderRepository

class MainActivity : BaseMvpActivity<MainActivityContract.View, MainActivityContract.Presenter>(), MainActivityContract.View {

    companion object {
        const val ORDER_INTENT_KEY = "order_to_cart"
        const val HISTORY_INTENT_KEY = "history_detail"
        const val USER_INTENT_KEY = "user-info"
        const val ORDER_REQUEST_CODE = 100
        const val CURRENT_PAGE = "current_page"
    }

    private val ORDER_TAB = 0
    private val HISTORY_TAB = 1

    private lateinit var adapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTab()
        setupViewPager()
    }

    private fun setupTab() {

        tabLayout.apply {
            addTab(tabLayout.newTab().setText("Order"))
            addTab(tabLayout.newTab().setText("History"))
            addTab(tabLayout.newTab().setText("User"))
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

            getPresenter().onClickMenuCart()
        }

        imgProfile.setOnClickListener {

        }
        getPresenter().registerRepository(OrderRepository())

    }

    override fun updateProfile() {

        val imgUrl = FirebaseAuth.getInstance().currentUser?.photoUrl
        if (imgUrl != null) {
            loadImage(imgUrl)
        } else {
            val userInfo = UserAuth.instance.getUserInfo()
            loadImage(userInfo?.avatar)
        }
    }

    private fun loadImage(imgUrl: Any?) {
        Glide.with(this@MainActivity).load(imgUrl).into(imgProfile)
    }

    override fun createPresenter(): MainActivityContract.Presenter = MainActivityPresenter.create()

    override fun setupInstance() {
        setSupportActionBar((findViewById(R.id.toolbar)))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(ORDER_INTENT_KEY, Parcels.wrap(getPresenter().getOrderFromCart()))
        outState?.putInt(CURRENT_PAGE, viewPager.currentItem)

    }

    override fun onRestoreInstanceState(bundle: Bundle) {
        val cartOrder = Parcels.unwrap<MutableList<FoodItem>>(bundle.getParcelable(ORDER_INTENT_KEY))
        val currentPage = bundle.getInt(CURRENT_PAGE, 0)
        viewPager.currentItem = currentPage
        getPresenter().updateOrderFromCart(cartOrder)

        updateCartNotification()
    }

    override fun onAddOrderToCartEvent(order: FoodItem) {
        getPresenter().addOrderToCart(order)
        updateCartNotification()
        Toast.makeText(applicationContext, "${order.name} Add to cart", Toast.LENGTH_SHORT).show()

    }

    override fun onRemoveOrderFromCartEvent(order: FoodItem) {
        getPresenter().removeOrderFromCart(order)
        updateCartNotification()
        Toast.makeText(applicationContext, "${order.name} Remove to cart", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ORDER_REQUEST_CODE) {
            getPresenter().updateOrderFromCart(mutableListOf())
            if (resultCode == Activity.RESULT_OK) {
                val page = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.currentItem)
                updateCartNotification()
                if (viewPager.currentItem == 0 && page != null) {
                    (page as FoodProductFragment).updateOrderItemFromCart()
                }
            }
        }
    }

    override fun goToCartActivity() = startActivityForResult(Intent(this@MainActivity, CartOrderActivity::class.java).apply {
        putExtra(ORDER_INTENT_KEY, Parcels.wrap(getPresenter().getOrderFromCart()))
    }, 100)

    override fun goToChatActivity(user: UserInfo) = startActivity(Intent(this@MainActivity, ChatActivity::class.java).apply {
        putExtra(USER_INTENT_KEY, Parcels.wrap(user))
    })

    override fun goToDetail(historyItem: HistoryItem) = startActivity(Intent(this@MainActivity, HistoryDetailActivity::class.java).apply {
        putExtra(HISTORY_INTENT_KEY, Parcels.wrap(historyItem))
    })

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut()
            LoginManager.getInstance().logOut()
            goToLoginActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateCartNotification() {
        val count = getPresenter().getOrderCount()
        tvOrderCount.apply {
            visibility = if (count == 0) View.GONE else View.VISIBLE
            text = "$count"
        }
    }

    private fun goToLoginActivity() {
        finish()
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))

    }

    private var tabSelectedListener: TabLayout.OnTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            viewPager.currentItem = tab.position

            if (viewPager.currentItem == HISTORY_TAB) {
                toolbarCart.visibility = View.GONE
                imgProfile.visibility = View.GONE
            } else if (viewPager.currentItem == ORDER_TAB) {
                toolbarCart.visibility = View.VISIBLE
                imgProfile.visibility = View.VISIBLE
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {}
        override fun onTabReselected(tab: TabLayout.Tab) {}
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
