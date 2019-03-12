package nilezia.app.foodorder.ui.register

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import nilezia.app.foodorder.base.BaseMvpPresenterImp
import nilezia.app.foodorder.ui.repository.OrderRepository

class RegisterPresenter : BaseMvpPresenterImp<RegisterContract.View>(), RegisterContract.Presenter {

    private lateinit var mRepository: OrderRepository

    companion object {

        fun create(): RegisterContract.Presenter = RegisterPresenter()

    }

    override fun registerRepository(repository: OrderRepository) {

        this.mRepository = repository
    }

    override fun onCompleteListener(task: Task<AuthResult>) {

        if (!task.isSuccessful) {
            getView().showDialogFail(task.exception?.message!!)
        } else {

            mRepository.addAccountToFirebase {

                getView().showSuccessToast(getView().getSuccessText())
            }
        }
    }

    override fun onClickCreateAccount(userName: String, password: String, confirmPassword: String) {

        if (userName.isEmpty()) {
            getView().showDialogFail("Email is empty")
        } else if (password.isEmpty()) {
            getView().showDialogFail("Password is empty")
        } else if (confirmPassword.isEmpty()) {

            getView().showDialogFail("Confirm Password is empty")

        } else {

            if (password != confirmPassword) {

                getView().showDialogFail("Password not match!!")

            } else {

                getView().createUserWithEmail()
            }
        }
    }
}