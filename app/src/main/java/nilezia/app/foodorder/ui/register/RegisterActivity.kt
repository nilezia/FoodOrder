package nilezia.app.foodorder.ui.register

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import nilezia.app.foodorder.R
import nilezia.app.foodorder.base.BaseMvpActivity
import nilezia.app.foodorder.dialog.DialogManager
import nilezia.app.foodorder.ui.register.model.RegisterModel
import nilezia.app.foodorder.ui.repository.OrderRepository
import org.parceler.Parcels
import siclo.com.ezphotopicker.api.EZPhotoPick
import siclo.com.ezphotopicker.api.EZPhotoPickStorage
import siclo.com.ezphotopicker.api.models.EZPhotoPickConfig
import siclo.com.ezphotopicker.api.models.PhotoSource
import java.io.IOException


class RegisterActivity : BaseMvpActivity<RegisterContract.View, RegisterContract.Presenter>(), RegisterContract.View {

    private val EMAIL_BUNDLE_KEY = "email_key"
    private val PASSWORD_BUNDLE_KEY = "password_key"
    private val CONFIRM_PASSWORD_BUNDLE_KEY = "confirm_password_key"
    private val DISPLAY_BUNDLE_KEY = "display_key"
    private val REGISTER_OBJECT_BUNDLE_KEY = "register_object_key"
    private val PATH_PHOTO_BUNDLE_KEY = "path_photo_key"
    private val prefix = "IMG_PF"
    private lateinit var mAuth: FirebaseAuth
    private lateinit var bottomSheetView: View
    private lateinit var bottomSheetDialog: BottomSheetDialog

    private var mPathPhoto: String? = null
    override fun initial() {


    }

    override fun setupLayout(): Int = R.layout.activity_register

    override fun setupView() {
        setSupportActionBar((findViewById(R.id.toolbar)))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
    }

    override fun setupInstance() {
        setupFirebase()
        setupBottomSheet()
        getPresenter().registerRepository(OrderRepository())
        btnCreateAccount.setOnClickListener {
            getPresenter().getRegister().apply {
                this.displayName = edtDisplayName.text.toString()
                this.userName = edtEmailName.text.toString()
                this.password = edtPassword.text.toString()
            }.run {

                getPresenter().onClickCreateAccount(
                        edtEmailName.text.toString(),
                        edtPassword.text.toString(),
                        edtConfirmPassword.text.toString())
            }
        }

        imgProfile.setOnClickListener {

            showBottomSheet()

        }
    }

    override fun createPresenter(): RegisterContract.Presenter = RegisterPresenter()

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(DISPLAY_BUNDLE_KEY, edtDisplayName.text.toString())
        outState?.putString(EMAIL_BUNDLE_KEY, edtEmailName.text.toString())
        outState?.putString(PASSWORD_BUNDLE_KEY, edtPassword.text.toString())
        outState?.putString(CONFIRM_PASSWORD_BUNDLE_KEY, edtConfirmPassword.text.toString())
        outState?.putParcelable(REGISTER_OBJECT_BUNDLE_KEY, Parcels.wrap(getPresenter().getRegister()))
        outState?.putString(PATH_PHOTO_BUNDLE_KEY, mPathPhoto)
    }

    override fun onRestoreInstanceState(bundle: Bundle) {
        val registerObject = Parcels.unwrap<RegisterModel>(bundle.getParcelable(REGISTER_OBJECT_BUNDLE_KEY))
        val pathPhoto = bundle.getString(PATH_PHOTO_BUNDLE_KEY)
        edtDisplayName.setText(bundle.getString(DISPLAY_BUNDLE_KEY))
        edtEmailName.setText(bundle.getString(EMAIL_BUNDLE_KEY))
        edtPassword.setText(bundle.getString(PASSWORD_BUNDLE_KEY))
        edtConfirmPassword.setText(bundle.getString(CONFIRM_PASSWORD_BUNDLE_KEY))
        getPresenter().mRegisterModel = registerObject
        pathPhoto?.let {
            setPhotoOnImageView(createBitmap(pathPhoto))
        }

    }

    private fun createBitmap(pathPhoto: String): Bitmap {
        mPathPhoto = pathPhoto
        val bmOptions = BitmapFactory.Options()
        val bitmap = BitmapFactory.decodeFile(pathPhoto, bmOptions)
        return Bitmap.createBitmap(bitmap)

    }

    override fun createUserWithEmail() {
        mAuth.createUserWithEmailAndPassword(getPresenter().getRegister().userName,
                getPresenter().getRegister().password)
                .addOnCompleteListener(this) { task ->
                    getPresenter().onCompleteListener(task)
                }
    }

    override fun showSuccessToast(msg: String) {

        Toast.makeText(this@RegisterActivity, msg, Toast.LENGTH_LONG).show()
        finish()
    }

    override fun showDialogFail(msg: String) {

        DialogManager.showMessageDialog(this@RegisterActivity, msg)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getSuccessText(): String = getString(R.string.txt_create_account_success)

    override fun showLoadingDialog() {
        progressLogin.visibility = View.VISIBLE
    }

    override fun hideLoadingDialog() {
        progressLogin.visibility = View.GONE
    }

    private fun showBottomSheet() {
        bottomSheetView.findViewById<TextView>(R.id.menu_bottom_sheet_camera).setOnClickListener {
            EZPhotoPick.startPhotoPickActivity(this, chooseCamera())
            bottomSheetDialog.cancel()

        }

        bottomSheetView.findViewById<TextView>(R.id.menu_bottom_sheet_gallery).setOnClickListener {
            EZPhotoPick.startPhotoPickActivity(this, chooseImage())
            bottomSheetDialog.cancel()
        }

        bottomSheetDialog.show()
    }

    private fun chooseImage(): EZPhotoPickConfig {

        return EZPhotoPickConfig().apply {
            photoSource = PhotoSource.GALLERY
            exportingSize = 900
            exportedPhotoName = prefix + "_" + System.currentTimeMillis().toString()

        }
    }

    private fun chooseCamera() = EZPhotoPickConfig().apply {

        photoSource = PhotoSource.CAMERA
        exportingSize = 900
        exportedPhotoName = prefix + "_" + System.currentTimeMillis().toString()

    }

    private fun setupFirebase() {
        mAuth = FirebaseAuth.getInstance()

    }

    @SuppressLint("InflateParams")
    private fun setupBottomSheet() {
        bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        bottomSheetDialog = BottomSheetDialog(this@RegisterActivity)

        bottomSheetDialog.apply {

            bottomSheetDialog.setContentView(bottomSheetView)
        }

    }

    private fun setPhotoOnImageView(pickedPhoto: Bitmap) = imgProfile.setImageBitmap(pickedPhoto)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == EZPhotoPick.PHOTO_PICK_GALLERY_REQUEST_CODE ||
                        requestCode == EZPhotoPick.PHOTO_PICK_CAMERA_REQUEST_CODE) &&
                resultCode == RESULT_OK) {
            try {
                val pickedPhoto: Bitmap = EZPhotoPickStorage(this).loadLatestStoredPhotoBitmap()
                val photoName = data?.getStringExtra(EZPhotoPick.PICKED_PHOTO_NAME_KEY)!!
                setPhotoOnImageView(pickedPhoto)
                mPathPhoto = EZPhotoPickStorage(this).getAbsolutePathOfStoredPhoto("", photoName)
                getPresenter().getRegister().imgPath = mPathPhoto

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}