package nilezia.app.foodorder.ui.register

import nilezia.app.foodorder.base.BaseMvpPresenterImp

class RegisterPresenter : BaseMvpPresenterImp<RegisterContract.View>(), RegisterContract.Presenter {
    companion object {

        fun create(): RegisterContract.Presenter = RegisterPresenter()

    }
}