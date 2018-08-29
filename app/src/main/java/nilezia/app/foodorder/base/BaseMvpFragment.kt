package nilezia.app.foodorder.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

@Suppress("UNCHECKED_CAST")
abstract class BaseMvpFragment<V : BaseMvpView, T : BaseMvpPresenter<V>> : Fragment(), BaseMvpView {

    private var presenter: T? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
        presenter?.attachView(this as V)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(setupLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
        setupInstance()
        setupView()
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState)
        }
    }

    override fun getPresenter(): T = presenter ?: throw Throwable("Presenter has null")

    protected abstract fun setupLayout(): Int

    protected abstract fun setupView()

    protected abstract fun setupInstance()

    protected abstract fun bindView(view: View)

    abstract fun createPresenter(): T

    abstract fun onRestoreInstanceState(bundle: Bundle)

}