package nilezia.app.foodorder.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseMvpActivity<V : BaseMvpView, T : BaseMvpPresenter<V>> : AppCompatActivity(), BaseMvpView {

    protected abstract var mPresenter: T
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this as V)
        setContentView(setupLayout())
        setupView()
        setupInstance()
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState)
        } else {
            initial()
        }
    }

    protected abstract fun initial()

    protected abstract fun setupLayout(): Int

    protected abstract fun setupView()

    protected abstract fun setupInstance()

    abstract override fun onRestoreInstanceState(bundle: Bundle)

}