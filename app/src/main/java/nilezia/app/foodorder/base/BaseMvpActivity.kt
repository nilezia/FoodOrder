package nilezia.app.foodorder.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

@Suppress("UNCHECKED_CAST")
abstract class BaseMvpActivity<V : BaseMvpView, T : BaseMvpPresenter<V>> : AppCompatActivity(), BaseMvpView {

    private var presenter: T? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
        presenter?.attachView(this as V)
        setContentView(setupLayout())
        setupView()
        setupInstance()
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState)
        } else {
            initial()
        }
    }

    fun getPresenter(): T = presenter ?: throw Throwable("Presenter has null")


    protected abstract fun initial()

    protected abstract fun setupLayout(): Int

    protected abstract fun bindView()

    protected abstract fun setupView()

    protected abstract fun setupInstance()

    abstract fun createPresenter(): T

    abstract override fun onRestoreInstanceState(bundle: Bundle)

}