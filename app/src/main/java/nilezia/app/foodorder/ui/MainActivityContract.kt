package nilezia.app.foodorder.ui

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView

interface MainActivityContract {

    interface View : BaseMvpView {


    }

    interface Presenter : BaseMvpPresenter<View> {


    }


}