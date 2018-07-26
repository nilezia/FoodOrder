package nilezia.app.foodorder.base

import java.lang.ref.WeakReference

open class BaseMvpPresenterImp<V : BaseMvpView> : BaseMvpPresenter<V> {

    protected var mView: V? = null
        get() = wView?.get()
    private var wView: WeakReference<V>? = null

    override fun attachView(view: V) {

        this.wView = WeakReference(view)

    }

    override fun detachView() {
        this.wView = null
    }


}