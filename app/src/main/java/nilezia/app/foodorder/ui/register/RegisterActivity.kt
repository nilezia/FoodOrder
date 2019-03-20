package nilezia.app.foodorder.ui.register

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
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
import nilezia.app.foodorder.ui.repository.OrderRepository
import siclo.com.ezphotopicker.api.EZPhotoPick
import siclo.com.ezphotopicker.api.EZPhotoPickStorage
import siclo.com.ezphotopicker.api.models.EZPhotoPickConfig
import siclo.com.ezphotopicker.api.models.PhotoSource
import java.io.IOException


class RegisterActivity : BaseMvpActivity<RegisterContract.View, RegisterContract.Presenter>(), RegisterContract.View {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var bottomSheetView: View
    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun initial() {

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

    override fun setupLayout(): Int = R.layout.activity_register

    override fun setupView() {
        setSupportActionBar((findViewById(R.id.toolbar)))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
    }

    override fun setupInstance() {

    }

    override fun createPresenter(): RegisterContract.Presenter = RegisterPresenter()

    override fun onRestoreInstanceState(bundle: Bundle) {

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
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getSuccessText(): String = "Create Account Success!"

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

    private fun chooseImage() = EZPhotoPickConfig().apply {
        photoSource = PhotoSource.GALLERY
        exportingSize = 900
        exportedPhotoName = "IMG_" + System.currentTimeMillis().toString()

    }

    private fun chooseCamera() = EZPhotoPickConfig().apply {

        photoSource = PhotoSource.CAMERA
        exportingSize = 900
        exportedPhotoName = "IMG_PF" + System.currentTimeMillis().toString()

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
        if (requestCode == EZPhotoPick.PHOTO_PICK_GALLERY_REQUEST_CODE ||
                requestCode == EZPhotoPick.PHOTO_PICK_CAMERA_REQUEST_CODE &&
                resultCode == RESULT_OK) {
            try {
                val pickedPhoto: Bitmap = EZPhotoPickStorage(this).loadLatestStoredPhotoBitmap()
                val photoName = data?.getStringExtra(EZPhotoPick.PICKED_PHOTO_NAME_KEY)!!
                setPhotoOnImageView(pickedPhoto)
                getPresenter().getRegister().imgPath = EZPhotoPickStorage(this).getAbsolutePathOfStoredPhoto("", photoName)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}