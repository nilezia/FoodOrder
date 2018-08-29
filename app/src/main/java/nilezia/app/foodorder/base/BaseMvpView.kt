package nilezia.app.foodorder.base

interface BaseMvpView{

    fun getPresenter(): BaseMvpPresenter<*>

}