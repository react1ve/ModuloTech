package com.example.template.common.android.helper

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import kotlin.math.abs

class KeyboardHelper(
    val withStatusBar: Boolean = false,
    var onKeyboardOpen: ((Boolean) -> Unit)? = null,
    var onHeightChanged: ((Int) -> Unit)? = null
) : ViewTreeObserver.OnGlobalLayoutListener {

    var isKeyboardOpen = false
        private set

    var keyBoardHeight = 0
        private set

    var screenHeight = 0
        private set

    private var rootView: View? = null
    private var previousHeight = -1

    override fun onGlobalLayout() {
        val height = rootView.screenVisibleHeight()
        var modified = false
        if (previousHeight != -1 && previousHeight != height) {
            if (previousHeight > height && !isKeyboardOpen) {
                isKeyboardOpen = isKeyboardOpen(height)
                modified = true
            } else if (previousHeight < height && isKeyboardOpen) {
                isKeyboardOpen = isKeyboardOpen(height)
                modified = true
            }
        }
        if (previousHeight != height) {
            keyBoardHeight = abs(previousHeight - height)
            previousHeight = height
            onHeightChanged?.invoke(height)
        }
        if (modified) {
            onKeyboardOpen?.invoke(isKeyboardOpen(height))
        }
    }

    fun attach(rootView: View?) {
        this.rootView = rootView
        rootView?.viewTreeObserver?.addOnGlobalLayoutListener(this)
        screenHeight = rootView.screenVisibleHeight()
    }

    fun detach() {
        rootView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
    }

    private fun isKeyboardOpen(height: Int) = screenHeight > height

    private fun View?.screenVisibleHeight(): Int {
        val rect = Rect()
        this?.getWindowVisibleDisplayFrame(rect)
        return rect.bottom - (rect.top.takeIf { withStatusBar } ?: 0)
    }
}
