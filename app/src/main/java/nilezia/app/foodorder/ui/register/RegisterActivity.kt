package nilezia.app.foodorder.ui.register

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpActivity

import com.google.firebase.auth.FirebaseAuth
import nilezia.app.foodorder.ui.repository.OrderRepository


class RegisterActivity : BaseMvpActivity<RegisterContract.View, RegisterContract.Presenter>(), RegisterContract.View {

    private lateinit var mAuth: FirebaseAuth
    override fun initial() {
        setSupportActionBar((findViewById(R.id.toolbar)))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
        getPresenter().registerRepository(OrderRepository())
        btnCreateAccount.setOnClickListener {
            getPresenter().onClickCreateAccount(edtUsername.text.toString(), edtPassword.text.toString(), edtConfirmPassword.text.toString())

        }

    }

    override fun setupLayout(): Int = R.layout.activity_register

    override fun bindView() {

    }

    override fun setupView() {

    }

    override fun setupInstance() {

    }

    override fun createPresenter(): RegisterContract.Presenter = RegisterPresenter()

    override fun onRestoreInstanceState(bundle: Bundle) {

    }

    override fun createUserWithEmail() {
        mAuth.createUserWithEmailAndPassword(edtUsername.text.toString(), edtPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    getPresenter().onCompleteListener(task)

                }
    }

    override fun showSuccessToast(msg: String) {

        Toast.makeText(this@RegisterActivity, msg, Toast.LENGTH_LONG).show()
        finish()
    }

    override fun showDialogFail(msg: String) {
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getSuccessText(): String = "Create Account Success!"


    private fun setupFirebase() {
        mAuth = FirebaseAuth.getInstance()

    }
}