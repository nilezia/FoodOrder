package nilezia.app.foodorder.ui.login

import android.util.Log
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import nilezia.app.foodorder.base.BaseMvpPresenterImp
import nilezia.app.foodorder.helper.FirebaseHelper

class LoginPresenter : BaseMvpPresenterImp<LoginContract.View>(), LoginContract.Presenter {

    private lateinit var mFirebaseHelper: FirebaseHelper

    companion object {

        fun create(): LoginContract.Presenter = LoginPresenter()

    }

    override fun registerFirebase(firebaseHelper: FirebaseHelper) {
        this.mFirebaseHelper = firebaseHelper

    }

    override fun onGoogleSignIn() {
        getView().showLoadingDialog()
    }

    override fun onConnectGoogleResult(result: GoogleSignInResult?) {
        if (result!!.isSuccess) {
            val acct = result.signInAccount
            getView().firebaseAuthWithGoogle(acct!!)
        } else {

            Log.d("Test", "handleSignInResult:" + result.isSuccess + " " + result.status)
        }
    }

    override fun onConnectFacebook(accessToken: AccessToken) {

        getView().firebaseAuthWithFacebook(accessToken)
    }

    override fun onSinginWithEmail(userName: String, password: String) {
        getView().showLoadingDialog()
        getView().signinWithEmail(userName, password)

    }

    override fun onCompleteFacebook(task: Task<AuthResult>) {
        if (!task.isSuccessful) {
            getView().showDialogLoginFail(task.exception?.message!!)
        }else{

            getView().showSendForgotPasswordSuccess()
        }
    }

    override fun onCompleteGoogle(task: Task<AuthResult>) {
        if (!task.isSuccessful) {
            getView().showDialogLoginFail(task.exception?.message!!)
        }
    }

    override fun onCompleteSigninWithEmail(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            getView().goToMainActivity()
        } else {
            getView().showDialogLoginFail(task.exception?.message!!)
        }
    }

    override fun onAuthStateListener(firebaseAuth: FirebaseAuth) {

        val user = firebaseAuth.currentUser
        if (user != null) {
            getView().goToMainActivity()
        } else {
            getView().setupGoogleSignIn()
        }

    }

    override fun onCompleteChangePassword(task: Task<Void>) {
        getView().hideLoadingDialog()
        if (!task.isSuccessful) {
            getView().showDialogLoginFail(task.exception?.message!!)
        }
    }

    override fun onClickForgotPassword() {

        getView().showForgotPasswordDialog()

    }

    override fun onClickSignup() {

        getView().goToRegisterActivity()
    }

    override fun isValidateEmptyLogin(userName: String, password: String): Boolean {

        if (userName.isEmpty()) {
            return false
        }
        if (password.isEmpty()) {
            return false
        }
        return true

    }


}
