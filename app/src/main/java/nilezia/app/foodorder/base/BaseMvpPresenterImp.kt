package nilezia.app.foodorder.base

import java.lang.ref.WeakReference

open class BaseMvpPresenterImp<V : BaseMvpView> : BaseMvpPresenter<V> {

    private var wView: WeakReference<V>? = null

    override fun attachView(view: V) {

        this.wView = WeakReference(view)

    }

    override fun detachView() {
        this.wView = null
    }

    override fun getView(): V = wView?.get() ?: throw(Throwable("View has null"))


}