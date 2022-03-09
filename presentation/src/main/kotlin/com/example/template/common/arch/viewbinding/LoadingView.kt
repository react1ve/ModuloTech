package com.example.template.common.arch.viewbinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.example.presentation.R
import com.example.template.common.android.ext.view.showIf

/**
 * Pseudo-mixin that allows to declare implementing view as view having LoadingSource. BaseFragment and BaseActivity
 * will add loading layout when inflating self, that can be controlled through [showLoadingIf]. It uses preset
 * views and ids for this, but it can be redefined by overriding [loadingBinding]
 */
interface LoadingView {

    val loadingBinding: LoadingBinding get() = LoadingBinding.DEFAULT

    val contentView: View

    fun showLoadingIf(show: Boolean = true) {
        loadingBinding.overlayView(contentView).showIf(show)
    }
}

fun LoadingView.layerInflater(layoutInflater: LayoutInflater) =
    NestedInflater.LevelInflater { parent, attach ->
        val loadingView = loadingBinding.inflate(layoutInflater, parent, attach)
        loadingView to loadingBinding.contentView(loadingView)
    }

/**
 * Descriptor of views that will be used for showing loading in [LoadingView]
 *
 * @param loadingRootLayoutId defines a layout that will be inflated to show loading
 * @param contentContainerId id inside [loadingRootLayoutId] where BaseActivity/BaseFragment will inflate content
 * @param loadingOverlayId id inside [loadingRootLayoutId] that is used to show or hide loading overlay
 */
data class LoadingBinding(
    @LayoutRes val loadingRootLayoutId: Int,
    @IdRes val contentContainerId: Int,
    @IdRes val loadingOverlayId: Int,
) {

    fun inflate(layoutInflater: LayoutInflater, parent: ViewGroup?, attach: Boolean = false): View =
        layoutInflater.inflate(loadingRootLayoutId, parent, attach)

    fun contentView(view: View): ViewGroup = view.findViewById(contentContainerId)

    fun overlayView(view: View): View = view.findViewById(loadingOverlayId)

    companion object {
        val DEFAULT = LoadingBinding(R.layout.layout_loading_root, R.id.container_content, R.id.loading_overlay)
    }
}
