package com.example.template.common.android.delegate

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyboardDelegate(
    private val activity: Activity
) {

    private val input: InputMethodManager
        get() = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    fun showKeyboard(target: Any): Unit = with(input) {
        when (target) {
            is View -> showSoftInput(
                target.apply { requestFocus() },
                InputMethodManager.SHOW_FORCED
            )
            else -> toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    fun hideKeyboard() {
        activity.currentFocus?.apply { input.hideSoftInputFromWindow(windowToken, 0) }
    }
}
