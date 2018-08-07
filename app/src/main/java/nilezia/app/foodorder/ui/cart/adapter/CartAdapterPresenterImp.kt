package nilezia.app.foodorder.ui.cart.adapter

import nilezia.app.foodorder.base.BaseMvpPresenter
import java.lang.ref.WeakReference

open class CartAdapterPresenterImp {

    protected var mView: CartAdapterContract.View? = null
        get() = wView?.get()
    private var wView: WeakReference<CartAdapterContract.View>? = null

    fun attachView(view: CartAdapterContract.View) {
        this.mView = view
    }
    fun detachView() {
        this.mView = null

    }
}