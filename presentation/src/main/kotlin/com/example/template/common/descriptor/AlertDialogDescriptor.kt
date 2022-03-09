package com.example.template.common.descriptor

import android.content.Context
import androidx.appcompat.app.AlertDialog

class AlertDialogDescriptor(
    val cancelable: Boolean = true,
    var title: String? = null,
    var message: String? = null,
    var positiveAnswer: String? = null,
    var neutralAnswer: String? = null,
    var negativeAnswer: String? = null,
    var positiveAction: (() -> Unit)? = null,
    var neutralAction: (() -> Unit)? = null,
    var negativeAction: (() -> Unit)? = null
) {

    fun asAlertDialog(context: Context): AlertDialog = AlertDialog.Builder(context)
        .apply {
            setCancelable(cancelable)
            title?.let { setTitle(it) }
            message?.let { setMessage(it) }
            positiveAnswer?.let {
                setPositiveButton(it) { dialog, _ ->
                    positiveAction?.invoke()
                    dialog.dismiss()
                }
            }
            neutralAnswer?.let {
                setNeutralButton(it) { dialog, _ ->
                    neutralAction?.invoke()
                    dialog.dismiss()
                }
            }
            negativeAnswer?.let {
                setNegativeButton(it) { dialog, _ ->
                    negativeAction?.invoke()
                    dialog.dismiss()
                }
            }
        }
        .create()
}
