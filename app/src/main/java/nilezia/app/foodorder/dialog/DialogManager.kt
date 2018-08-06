package nilezia.app.foodorder.dialog

import android.app.AlertDialog
import android.content.Context

class DialogManager {


    init {


    }

    companion object {

        fun showQuestionDialog(context: Context) = AlertDialog.Builder(context)

        fun showAlertDialog(context: Context,text: String) = AlertDialog.Builder(context).apply {

            setMessage(text)
            setNegativeButton("Close") { dialog, _ -> dialog.dismiss() }


        }

    }

}