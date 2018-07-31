package nilezia.app.foodorder.ui.cart;

import android.os.Bundle
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpActivity

class CartOrder : BaseMvpActivity<CartOrderContract.View, CartOrderContract.Presenter>(), CartOrderContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPresenter.onStart()

    }

    override var mPresenter: CartOrderContract.Presenter = CartOrderPresenter()

    override fun initial() {


    }

    override fun setupLayout(): Int = R.layout.activity_main

    override fun setupView() {

    }

    override fun setupInstance() {

    }

    override fun onRestoreInstanceState(bundle: Bundle) {

    }


}
