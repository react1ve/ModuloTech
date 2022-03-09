package com.example.template.common.android.ext.view

import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.core.common.safeCast

/**
 * #Recycler #Snap
 *
 * Adapted from https://stackoverflow.com/a/55375162
 *
 * Scroll listener that listens to SnapHelper state changes
 */
class SnapScrollListener(
    private val snapHelper: SnapHelper
) : RecyclerView.OnScrollListener() {
    var onSettle: ((position: Int) -> Unit)? = null
    var onScroll: ((position: Int) -> Unit)? = null

    private var snapPosition = RecyclerView.NO_POSITION
    private var lastSettled = RecyclerView.NO_POSITION

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        notifyListenerIfNeeded(recyclerView.snapPosition, false)
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            notifyListenerIfNeeded(recyclerView.snapPosition, true)
        }
    }

    private val RecyclerView.snapPosition: Int
        get() = layoutManager?.let { snapHelper.findSnapView(it) }
            ?.let { layoutManager?.getPosition(it) } ?: RecyclerView.NO_POSITION

    private fun notifyListenerIfNeeded(newPosition: Int, settled: Boolean) {
        onScroll
            ?.takeIf { snapPosition != newPosition }
            ?.let {
                snapPosition = newPosition
                it(newPosition)
            }
        onSettle
            ?.takeIf { settled && lastSettled != newPosition }
            ?.let {
                lastSettled = newPosition
                it(newPosition)
            }
    }

}

/**
 * #Recycler #Snap
 *
 * Helper method that attaches [snapHelper] and SnapScrollListener
 */
fun RecyclerView.attachSnapHelper(
    snapHelper: SnapHelper,
    onScroll: ((position: Int) -> Unit)? = null,
    onSettle: ((position: Int) -> Unit)? = null
) {
    snapHelper.attachToRecyclerView(this)
    if (onScroll != null || onSettle != null) {
        addOnScrollListener(
            SnapScrollListener(snapHelper)
                .apply {
                    this.onScroll = onScroll
                    this.onSettle = onSettle
                }
        )
    }
}

/**
 * #Recycler
 *
 * Position of first visible item (or [RecyclerView.NO_POSITION] if there's no visible items or
 * layout manager isn't LinearLayoutManager)
 */
val RecyclerView.currentPosition: Int
    get() = layoutManager
        ?.safeCast<LinearLayoutManager>()
        ?.findFirstVisibleItemPosition()
        ?: RecyclerView.NO_POSITION

/**
 * #Recycler
 *
 * ScrollListener that activates when current visible item changes
 */
class VisibleItemChangeListener(val onItemChange: (old: Int?, new: Int) -> Unit) :
    RecyclerView.OnScrollListener() {

    private var position: Int = RecyclerView.NO_POSITION

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dx == 0 && dy == 0) {
            // if dx/dy == 0, it's onScrolled called either after initialization or
            // after scrollToPosition() is finished
            // That means we need to refresh current position
            position = recyclerView.currentPosition
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        when (newState) {
            RecyclerView.SCROLL_STATE_DRAGGING -> {
                if (position == RecyclerView.NO_POSITION) position = recyclerView.currentPosition
            }
            RecyclerView.SCROLL_STATE_IDLE -> {
                position
                    .let { old ->
                        val new = recyclerView.currentPosition
                        if (new >= 0 && old != new) onItemChange(old.takeIf { it >= 0 }, new)
                    }
                position = recyclerView.currentPosition
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun <T : RecyclerView.ViewHolder> RecyclerView?.viewHolderProvider() =
    { position: Int -> this?.findViewHolderForAdapterPosition(position) as? T }

fun RecyclerView.addDividers(
    @DrawableRes dividerResId: Int,
    orientation: Int = DividerItemDecoration.VERTICAL
) {
    ContextCompat.getDrawable(context, dividerResId)?.let {
        addItemDecoration(DividerItemDecoration(context, orientation).apply { setDrawable(it) })
    }
}

fun RecyclerView.scrollToPosition(position: Int, onScrolled: () -> Unit) {
    val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            recyclerView.removeOnScrollListener(this)
            onScrolled()
        }
    }
    addOnScrollListener(listener)
    scrollToPosition(position)
}
