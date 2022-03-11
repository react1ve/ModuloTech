@file:Suppress("unused")

package com.modulo.modulotest.common.android.ext.view

import android.util.Size
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Checkable
import com.modulo.core.common.safeCast

fun View.isVisible() = visibility == View.VISIBLE

fun View.isGone() = visibility == View.GONE

fun View.isInvisible() = visibility == View.INVISIBLE

fun allVisible(vararg views: View) = views.all { it.isVisible() }

fun anyVisible(vararg views: View) = views.any { it.isVisible() }

fun View.show() {
    visibility = View.VISIBLE
}

fun View.show(visible: Boolean) {
    if (visible) show() else hide()
}

fun View.showIf(visible: Boolean) {
    if (visible) show() else hide()
}

fun View.dissolveIf(invisible: Boolean) {
    if (invisible) dissolve() else show()
}

fun View.dissolve() {
    visibility = View.INVISIBLE
}

fun View.dissolve(invisible: Boolean) {
    if (invisible) dissolve() else show()
}

fun View.hide() {
    visibility = View.GONE
}

/**
 * publish View.isSelected to [selected]
 */
fun View.select(selected: Boolean = true) {
    isSelected = selected
}

/**
 * publish View.isSelected to [selected], also revert [pair].isSelected
 * Simulate logic of radio buttons pair
 */
fun View.select(selected: Boolean, pair: View) {
    isSelected = selected
    pair.isSelected = !selected
}

/**
 * publish View.isSelected to [selected], also revert [others].isSelected
 * Simulate logic of radio buttons group
 */
fun View.select(selected: Boolean, vararg others: View) {
    isSelected = selected
    others.forEach { it.isSelected = !selected }
}

/**
 * publish View.isEnabled to [enabled]
 */
fun View.enable(enabled: Boolean = true) {
    this.isEnabled = enabled
    this.alpha = if (enabled) 1.0f else 0.5f
    this.isClickable = enabled
}

/**
 * publish View.isEnabled to false
 */
fun View.disable() {
    this.isEnabled = false
    this.alpha = 0.5f
    this.isClickable = false
}

/**
 * publish Checkable.isChecked to [checked]
 */
fun Checkable.check(checked: Boolean) {
    this.isChecked = checked
}

/**
 * publish View.visibility to View.VISIBLE
 */
fun showViews(vararg views: View?) = views.forEach { it?.show() }

/**
 * publish View.visibility to View.INVISIBLE
 */
fun dissolveViews(vararg views: View?) = views.forEach { it?.dissolve() }

/**
 * publish View.visibility to View.GONE
 */
fun hideViews(vararg views: View?) = views.forEach { it?.hide() }

fun View?.hasSize(): Boolean = this != null && height > 0 && width > 0

val View?.size: Size? get() = this?.let { Size(width, height) }?.takeIf { hasSize() }

inline fun <T : View> T.doWhenLaidOut(crossinline action: (T) -> Unit) {
    if (hasSize()) {
        action(this)
        return
    }
    viewTreeObserver.addOnGlobalLayoutListener(
        object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (hasSize()) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    action(this@doWhenLaidOut)
                }
            }
        }
    )
}

fun View.findRelativeTop(root: ViewGroup?): Int {
    val childDirectParent = this.parent?.safeCast<View>()
    if (childDirectParent?.id == root?.id) {
        return top
    }
    return top + (childDirectParent?.findRelativeTop(root) ?: 0)
}

fun View.setMarginExtra(
    startMargin: Int = 0,
    topMargin: Int = 0,
    endMargin: Int = 0,
    bottomMargin: Int = 0
) {
    val layoutParams = layoutParams
        ?.safeCast<ViewGroup.MarginLayoutParams>()
    val start = layoutParams?.marginStart ?: 0
    val top = layoutParams?.topMargin ?: 0
    val end = layoutParams?.marginEnd ?: 0
    val bottom = layoutParams?.bottomMargin ?: 0
    layoutParams?.setMargins(
        start + startMargin,
        top + topMargin,
        end + endMargin,
        bottom + bottomMargin
    )
    this.layoutParams = layoutParams
}

fun View.setPaddingExtra(
    startPadding: Int = 0,
    topPadding: Int = 0,
    endPadding: Int = 0,
    bottomPadding: Int = 0
) {
    setPadding(
        paddingStart + startPadding,
        paddingTop + topPadding,
        paddingEnd + endPadding,
        paddingBottom + bottomPadding
    )
}

fun View.setMargin(
    startMargin: Int = 0,
    topMargin: Int = 0,
    endMargin: Int = 0,
    bottomMargin: Int = 0
) {
    val layoutParams = layoutParams
        ?.safeCast<ViewGroup.MarginLayoutParams>()
    layoutParams?.setMargins(
        startMargin,
        topMargin,
        endMargin,
        bottomMargin
    )
    this.layoutParams = layoutParams
}

fun View.changePadding(
    startPadding: Int = paddingStart,
    topPadding: Int = paddingTop,
    endPadding: Int = paddingEnd,
    bottomPadding: Int = paddingBottom
) {
    setPadding(
        startPadding,
        topPadding,
        endPadding,
        bottomPadding
    )
}

fun View.marginLeft() =
    layoutParams
        ?.safeCast<ViewGroup.MarginLayoutParams>()
        ?.leftMargin
        ?: 0

fun View.marginTop() =
    layoutParams
        ?.safeCast<ViewGroup.MarginLayoutParams>()
        ?.topMargin
        ?: 0

fun View.marginRight() =
    layoutParams
        ?.safeCast<ViewGroup.MarginLayoutParams>()
        ?.rightMargin
        ?: 0

fun View.marginBottom() =
    layoutParams
        ?.safeCast<ViewGroup.MarginLayoutParams>()
        ?.bottomMargin
        ?: 0
