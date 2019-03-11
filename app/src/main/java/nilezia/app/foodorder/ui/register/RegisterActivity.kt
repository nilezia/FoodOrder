package nilezia.app.foodorder.ui.register

import android.os.Bundle
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpActivity

class RegisterActivity : BaseMvpActivity<RegisterContract.View, RegisterContract.Presenter>(), RegisterContract.View {

    override fun initial() {


    }

    override fun setupLayout(): Int = R.layout.activity_register

    override fun bindView() {

    }

    override fun setupView() {

    }

    override fun setupInstance() {

    }

    override fun createPresenter(): RegisterContract.Presenter = RegisterPresenter()

    override fun onRestoreInstanceState(bundle: Bundle) {

    }

}