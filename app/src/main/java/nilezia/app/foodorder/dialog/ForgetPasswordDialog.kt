package nilezia.app.foodorder.dialog

import android.app.DialogFragment
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_forgot_password.*
import nilezia.app.foodorder.R

class ForgetPasswordDialog : DialogFragment() {

    private var message: Int = 0
    private var positive: Int = 0
    private var negative: Int = 0

    companion object {
        private const val KEY_MESSAGE = "key_message"
        private const val KEY_POSITIVE = "key_positive"
        private const val KEY_NEGATIVE = "key_negative"

        private fun newInstance(@StringRes positive: Int, @StringRes negative: Int) =
                ForgetPasswordDialog().apply {
                    arguments = Bundle().apply {
                        putInt(KEY_NEGATIVE, negative)
                        putInt(KEY_POSITIVE, positive)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            onRestoreArgument(arguments)
        } else {
            onRestoreInstanceState(savedInstanceState)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
//        btnChangePassword.setOnClickListener {
//            getOnDialogListener()?.onPositiveButtonClick()
//        }
//
//        btnCancel.setOnClickListener{
//
//            getOnDialogListener()?.onNegativeButtonClick()
//
//        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle) {


    }

    private fun onRestoreArgument(arguments: Bundle?) {
        arguments?.apply {
            positive = getInt(KEY_POSITIVE, 0)
            negative = getInt(KEY_NEGATIVE, 0)
        }
    }

    private fun getOnDialogListener(): OnDialogListener? {
        val fragment = parentFragment
        try {
            return if (fragment != null) {
                fragment as OnDialogListener?
            } else {
                activity as OnDialogListener
            }
        } catch (ignored: ClassCastException) {
        }
        return null
    }



    class Builder {
        private var positive: Int = 0
        private var negative: Int = 0

        fun Builder() {

        }

        fun setPositive(@StringRes positive: Int): Builder {
            this.positive = positive
            return this
        }

        fun setNegative(@StringRes negative: Int): Builder {
            this.negative = negative
            return this
        }


        fun build(): ForgetPasswordDialog {
            return ForgetPasswordDialog.newInstance(positive, negative)
        }
    }

    interface OnDialogListener {
        fun onPositiveButtonClick()

        fun onNegativeButtonClick()
    }


}