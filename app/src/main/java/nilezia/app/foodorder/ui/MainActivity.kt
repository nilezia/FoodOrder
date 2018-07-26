package nilezia.app.foodorder.ui

import android.os.Bundle
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpActivity
import nilezia.app.foodorder.ui.order.OrderFragment

class MainActivity : BaseMvpActivity<MainActivityContract.View, MainActivityContract.Presenter>(), MainActivityContract.View {

    override fun initial() {

        supportFragmentManager.beginTransaction().replace(R.id.container, OrderFragment.newInstance(),null).commit()
    }

    override var mPresenter: MainActivityContract.Presenter = MainActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setupLayout(): Int = R.layout.activity_main


    override fun setupView() {

    }

    override fun setupInstance() {

    }

    override fun onRestoreInstanceState(bundle: Bundle) {

    }

}
