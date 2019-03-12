package nilezia.app.foodorder.ui.register

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.ui.repository.OrderRepository

interface RegisterContract {

    interface View : BaseMvpView {

        fun createUserWithEmail()

        fun showDialogFail(msg: String)

        fun showSuccessToast(msg: String)

        fun getSuccessText(): String
    }

    interface Presenter : BaseMvpPresenter<View> {

        fun registerRepository(repository: OrderRepository)

        fun onClickCreateAccount(userName: String, password: String, confirmPassword: String)

        fun onCompleteListener(task: Task<AuthResult>)
    }


}