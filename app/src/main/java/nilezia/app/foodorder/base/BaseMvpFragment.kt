package nilezia.app.foodorder.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseMvpFragment<V : BaseMvpView, T : BaseMvpPresenter<V>> : Fragment(), BaseMvpView {


    protected abstract var mPresenter: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this as V)
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(setupLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupInstance()

    }

    protected abstract fun setupLayout(): Int

    protected abstract fun setupView(v: View)

    protected abstract fun setupInstance()

    abstract fun onRestoreInstanceState(bundle: Bundle)

}