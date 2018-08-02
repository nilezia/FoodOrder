package nilezia.app.foodorder.ui.cart;

import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpActivity
import nilezia.app.foodorder.model.OrderItem
import nilezia.app.foodorder.ui.MainActivity
import org.parceler.Parcel
import org.parceler.Parcels

class CartOrderActivity : BaseMvpActivity<CartOrderContract.View, CartOrderContract.Presenter>(), CartOrderContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPresenter.onStart()

    }

    override var mPresenter: CartOrderContract.Presenter = CartOrderPresenter()

    override fun initial() {
        val obj = Parcels.unwrap<MutableList<OrderItem>>(intent.getParcelableExtra(MainActivity.ORDER_INTENT_KEY))
        Log.d("CartOrderActivity", Gson().toJson(obj))

    }

    override fun setupLayout(): Int = R.layout.activity_main

    override fun setupView() {

    }

    override fun setupInstance() {

    }

    override fun onRestoreInstanceState(bundle: Bundle) {

    }


}
