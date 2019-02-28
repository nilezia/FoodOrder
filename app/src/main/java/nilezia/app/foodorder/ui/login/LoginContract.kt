package nilezia.app.foodorder.ui.login

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import nilezia.app.foodorder.base.BaseMvpPresenter
import nilezia.app.foodorder.base.BaseMvpView
import nilezia.app.foodorder.helper.FirebaseHelper

interface LoginContract {

    interface View : BaseMvpView {

        fun showLoginSuccess()

        fun showDialogLoginFail(msg: String)

        fun showLoadingDialog()

        fun hideLoadingDialog()

        fun setupGoogleSignIn()

        fun firebaseAuthWithGoogle(acct: GoogleSignInAccount)

        fun firebaseAuthWithFacebook(accessToken: AccessToken?)

        fun signinWithEmail(username: String, password: String)

        fun goToMainActivity()

    }

    interface Presenter : BaseMvpPresenter<View> {

        fun registerFirebase(firebaseHelper: FirebaseHelper)

        fun onGoogleSignIn()

        fun onConnectGoogleResult(result: GoogleSignInResult?)

        fun onConnectFacebook(accessToken: AccessToken)

        fun onSinginWithEmail(userName: String, password: String)

        fun onCompleteGoogle(task: Task<AuthResult>)

        fun onCompleteFacebook(task: Task<AuthResult>)

        fun onCompleteSigninWithEmail(task: Task<AuthResult>)

        fun onAuthStateListener(firebaseAuth: FirebaseAuth)

        fun isValidateEmptyLogin(userName: String, password: String): Boolean

    }

}
