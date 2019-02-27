package nilezia.app.foodorder.ui.login

import android.util.Log
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInResult

import nilezia.app.foodorder.base.BaseMvpPresenterImp
import nilezia.app.foodorder.helper.FirebaseHelper
import nilezia.app.foodorder.ui.login.model.LoginData


class LoginPresenter : BaseMvpPresenterImp<LoginContract.View>(), LoginContract.Presenter {

    private lateinit var mFirebaseHelper: FirebaseHelper

    companion object {

        fun create(): LoginContract.Presenter = LoginPresenter()

    }

    override fun registerFirebase(firebaseHelper: FirebaseHelper) {
        this.mFirebaseHelper = firebaseHelper

    }

    override fun login(loginData: LoginData) {

        // getFirebase().login(loginData)

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

    override fun isValidateEmptyLogin(userName: String, password: String): Boolean {

        if (userName.isEmpty()) {
            return false
        }
        if (password.isEmpty()) {
            return false
        }
        return true

    }

    override fun onEmailLogin(userName: String, password: String) {

        getView().singinWithEmail(userName, password)

    }

}
