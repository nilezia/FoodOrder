package nilezia.app.foodorder.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

class DialogManager {
    init {

    }
    companion object {

        fun showQuestionDialog(context: Context, positiveListener: DialogInterface.OnClickListener) = AlertDialog.Builder(context).apply {
            this.setMessage("Discard shipping?")
            this.setPositiveButton("OK", positiveListener)
            this.setNegativeButton("CANCEL") { dialog, _ -> dialog.dismiss() }
            show()

        }

        fun showMessageDialog(context: Context, text: String) = AlertDialog.Builder(context).apply {
            setMessage(text)
            setNegativeButton("Close") { dialog, _ -> dialog.dismiss() }
            show()
        }
    }
}