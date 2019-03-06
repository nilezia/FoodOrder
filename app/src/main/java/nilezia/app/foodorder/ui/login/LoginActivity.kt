package nilezia.app.foodorder.ui.login


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpActivity
import nilezia.app.foodorder.dialog.DialogManager
import nilezia.app.foodorder.dialog.ForgetPasswordDialog
import nilezia.app.foodorder.helper.FirebaseHelper
import nilezia.app.foodorder.ui.MainActivity


class LoginActivity : BaseMvpActivity<LoginContract.View, LoginContract.Presenter>(),
        LoginContract.View, View.OnClickListener,
        ForgetPasswordDialog.OnDialogListener {

    private var mGoogleApiClient: GoogleApiClient? = null
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    private lateinit var mAuth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager
    private lateinit var forgotDialog: ForgetPasswordDialog

    companion object {
        private const val RC_SIGN_IN = 1100
    }

    override fun setupView() {

    }

    override fun initial() {
        setupFirebase()
        setupFacebookLogin()
        getPresenter().registerFirebase(FirebaseHelper(applicationContext))
        btnSignInWithGoogle.setOnClickListener {
            getPresenter().onGoogleSignIn()
            startActivityForResult(googleSignIn(), RC_SIGN_IN)

        }

        fb.setOnClickListener(this)
        btnEmailLogin.setOnClickListener(this)
        tvForgetPassword.setOnClickListener(this)
    }

    private fun emailLogin() {
        val email = edtUsername.text.toString()
        val password = edtPassword.text.toString()

        if (getPresenter().isValidateEmptyLogin(email, password)) {
            getPresenter().onSinginWithEmail(email, password)
        } else {
            showDialogLoginFail(getString(R.string.warning_please_enter_data))
        }
    }

    override fun setupLayout(): Int = R.layout.activity_login

    override fun bindView() {

    }

    override fun setupInstance() {

    }

    override fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this) { }
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }

    override fun createPresenter(): LoginContract.Presenter = LoginPresenter.create()

    override fun onRestoreInstanceState(bundle: Bundle) {

    }

    override fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    getPresenter().onCompleteGoogle(task)
                }
    }

    override fun firebaseAuthWithFacebook(accessToken: AccessToken?) {

        val credential = FacebookAuthProvider.getCredential(accessToken?.token!!)
        mAuth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            getPresenter().onCompleteFacebook(task)
        }
    }

    override fun signinWithEmail(username: String, password: String) {
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener { task ->
            getPresenter().onCompleteSigninWithEmail(task)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppCompatActivity.RESULT_CANCELED && resultCode == AppCompatActivity.RESULT_OK) {
            finish()
        } else if (requestCode == RC_SIGN_IN) { // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            getPresenter().onConnectGoogleResult(result)
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.fb -> {
                buttonFacebookLogin.performClick()
            }
            R.id.btnEmailLogin -> {
                emailLogin()
            }
            R.id.tvForgetPassword -> {
                getPresenter().onClickForgotPassword()
            }
        }
    }

    override fun onNegativeButtonClick() {
        forgotDialog.dismiss()
    }

    override fun onPositiveButtonClick() {

        mAuth.sendPasswordResetEmail("")
                .addOnCompleteListener { task ->

                    getPresenter().onCompleteChangePassword(task)

                }
    }

    override fun onStart() {
        super.onStart()
        mGoogleApiClient?.connect()
        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()

        if (mGoogleApiClient != null && mGoogleApiClient?.isConnected!!) {
            mGoogleApiClient?.stopAutoManage(this@LoginActivity)
            mGoogleApiClient?.disconnect()
        }
        mAuth.removeAuthStateListener(mAuthListener)
    }

    override fun onPause() {
        super.onPause()
        mGoogleApiClient?.stopAutoManage(this)
        mGoogleApiClient?.disconnect()
    }

    override fun onDestroy() {
        super.onDestroy()
        mGoogleApiClient?.disconnect()
    }

    override fun showForgotPasswordDialog() {

        forgotDialog = ForgetPasswordDialog.Builder().apply {
            this.setPositive(R.string.txt_btn_reset_password)
            this.setNegative(R.string.txt_btn_cancel)
        }.run {
            build()
        }
        forgotDialog.show(fragmentManager, "")
    }

    override fun showDialogLoginFail(msg: String) {
        DialogManager.showMessageDialog(this@LoginActivity, msg)
    }

    override fun showSendForgotPasswordSuccess() {

        DialogManager.showMessageDialog(this@LoginActivity, "Please validate Email!!")

    }

    override fun showLoadingDialog() {
        progressLogin.visibility = View.VISIBLE
    }

    override fun hideLoadingDialog() {
        progressLogin.visibility = View.GONE
    }

    override fun goToMainActivity() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }

    private fun googleSignIn() = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)

    private fun setupFirebase() {
        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            getPresenter().onAuthStateListener(firebaseAuth)
        }
    }

    private fun setupFacebookLogin() {
        callbackManager = CallbackManager.Factory.create()
        buttonFacebookLogin.setReadPermissions("email", "public_profile")
        buttonFacebookLogin.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d("fbLog", "facebook:onSuccess:$loginResult")
                getPresenter().onConnectFacebook(loginResult.accessToken)
            }

            override fun onCancel() {
                showDialogLoginFail("facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                showDialogLoginFail("facebook:onError")
            }
        })
    }

}
