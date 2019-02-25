package nilezia.app.foodorder.ui.login


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
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
import nilezia.app.foodorder.helper.FirebaseHelper
import nilezia.app.foodorder.ui.MainActivity


class LoginActivity : BaseMvpActivity<LoginContract.View, LoginContract.Presenter>(), LoginContract.View {

    private var mGoogleApiClient: GoogleApiClient? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private var mAuth: FirebaseAuth? = null

    var fb: Button? = null

    private lateinit var callbackManager: CallbackManager
    private lateinit var auth: FirebaseAuth
    private var loginButton: LoginButton? = null

    companion object {
        private const val RC_SIGN_IN = 1100
    }


    override fun setupView() {
        fb = findViewById<Button>(R.id.fb)
        loginButton = findViewById<LoginButton>(R.id.buttonFacebookLogin)
    }

    override fun initial() {
        setupFirebase()
        initFacebook()
        getPresenter().registerFirebase(FirebaseHelper(applicationContext))
        btnSignInWithGoogle.setOnClickListener {
            getPresenter().onGoogleSignIn()
            startActivityForResult(googleSignIn(), RC_SIGN_IN)
        }

        fb?.setOnClickListener {

            loginButton?.performClick()
        }
    }

    private fun initFacebook() {
        auth = FirebaseAuth.getInstance()
        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create()

        loginButton?.setReadPermissions("email", "public_profile")
        loginButton?.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d("fbLog", "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d("fbLog", "facebook:onCancel")
                // ...
            }

            override fun onError(error: FacebookException) {
                Log.d("fbLog", "facebook:onError", error)
                // ...
            }
        })
        // ...


    }

    private fun handleFacebookAccessToken(accessToken: AccessToken?) {


        val credential = FacebookAuthProvider.getCredential(accessToken?.token!!)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("fbLog", "signInWithCredential:success")
                        val user = auth.currentUser
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("fbLog", "signInWithCredential:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        // updateUI(null)
                    }

                    // ...
                }

    }

    override fun setupLayout(): Int = R.layout.activity_login

    override fun bindView() {

    }

    override fun setupInstance() {

    }

    override fun createPresenter(): LoginContract.Presenter = LoginPresenter.create()

    override fun onRestoreInstanceState(bundle: Bundle) {

    }

    override fun showLoginSuccess() {

        //TODO:GotoMainActivity

    }

    override fun showDialogLoginFail(msg: String) {
        DialogManager.showMessageDialog(this@LoginActivity, msg)
    }

    override fun showLoadingDialog() {
        progressLogin.visibility = View.VISIBLE
    }

    override fun hideLoadingDialog() {
        progressLogin.visibility = View.GONE
    }

    override fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (!task.isSuccessful) {
                        Log.w("Firebase", "signInWithCredential", task.exception)
                    }
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

    override fun onStart() {
        super.onStart()
        mGoogleApiClient?.connect()
        mAuth!!.addAuthStateListener(mAuthListener!!)
    }

    override fun onStop() {
        super.onStop()

        if (mGoogleApiClient != null && mGoogleApiClient?.isConnected!!) {
            mGoogleApiClient?.stopAutoManage(this@LoginActivity)
            mGoogleApiClient?.disconnect()
        }
        mAuth!!.removeAuthStateListener(mAuthListener!!)
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

    private fun googleSignIn() = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)

    private fun setupFirebase() {
        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            // hideProgressDialog();
            if (user != null) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            } else {
                setupGoogleSign()
            }
        }
    }

    private fun setupGoogleSign() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this) { }
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

    }
}
