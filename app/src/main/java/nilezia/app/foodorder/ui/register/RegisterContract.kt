package nilezia.app.foodorder.ui.register

import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView

interface RegisterContract {

    interface View : BaseMvpView

    interface Presenter : BaseMvpPresenter<View>


}