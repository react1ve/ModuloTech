package com.modulo.modulotest.common.android.delegate

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.modulo.modulotest.common.descriptor.AlertDialogDescriptor
import com.modulo.presentation.R

class DialogDelegate(
    private val context: Context
) {

    private var alertDialog: AlertDialog? = null

    fun showAlertDialog(descriptor: AlertDialogDescriptor) {
        showDialog(descriptor.asAlertDialog(context))
    }

    fun showOkDialog(
        title: String? = null,
        message: String = "",
        ok: String? = null,
        okAction: (() -> Unit)? = null
    ) {
        val builder = with(AlertDialog.Builder(context)) {
            title?.let { setTitle(it) }
            setMessage(message)
            setPositiveButton(ok ?: context.getString(R.string.ok)) { _, _ ->
                okAction?.invoke()
                cancelDialog()
            }
        }
        showDialog(builder.create())
    }

    fun showOkCancelDialog(
        title: String? = null,
        message: String = "",
        yes: String? = null,
        no: String? = null,
        yesAction: (() -> Unit)? = null,
        noAction: (() -> Unit)? = null
    ) {
        val builder = with(AlertDialog.Builder(context)) {
            title?.let { setTitle(it) }
            setMessage(message)
            setPositiveButton(yes ?: context.getString(R.string.ok)) { _, _ ->
                yesAction?.invoke()
                cancelDialog()
            }
            setNegativeButton(no ?: context.getString(R.string.cancel)) { _, _ ->
                noAction?.invoke()
                cancelDialog()
            }
        }
        showDialog(builder.create())
    }

    fun cancelDialog() {
        alertDialog?.takeIf { it.isShowing }?.apply { dismiss() }
        alertDialog = null
    }

    private fun showDialog(alertDialog: AlertDialog) {
        cancelDialog()
        this.alertDialog = alertDialog.apply { show() }
    }
}
