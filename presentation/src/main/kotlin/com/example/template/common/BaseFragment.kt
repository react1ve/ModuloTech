package com.example.template.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.core.common.safeCast
import com.example.core.logger.logger
import com.example.presentation.R
import com.example.template.common.android.ContextWrapper
import com.example.template.common.android.delegate.DialogDelegate
import com.example.template.common.android.delegate.KeyboardDelegate
import com.example.template.common.android.ext.view.setMarginExtra
import com.example.template.common.android.ext.view.setPaddingExtra
import com.example.template.common.arch.MvvmViewModel
import com.example.template.common.arch.viewbinding.BindingFactory
import com.example.template.common.arch.viewbinding.LoadingView
import com.example.template.common.arch.viewbinding.NestedInflater
import com.example.template.common.arch.viewbinding.ViewBindingDelegate
import com.example.template.common.arch.viewbinding.layerInflater
import com.example.template.common.arch.viewbinding.terminalInflater
import com.example.template.common.navigation.BackListener
import com.example.template.common.navigation.Navigator
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.scope.ScopeFragment

abstract class BaseFragment : ScopeFragment, NestedInflater {

    constructor()

    /**
     * Constructor for cases where you don't need view binding for view interaction
     */
    constructor(bindingFactory: BindingFactory<*>) {
        viewBindingDelegate.createPrimaryViewBindingHandler(bindingFactory)
    }

    abstract val loggerTag: String
    protected open val log by lazy { logger(loggerTag) }
    protected val contextWrapper: ContextWrapper get() = ContextWrapper(this)
    protected val baseActivity: BaseActivity get() = activity?.safeCast<BaseActivity>()!!

    /* MVVM */
    protected abstract val viewModel: MvvmViewModel

    /* NAVIGATION */
    protected val navigationController: NavController get() = findNavController()
    protected abstract val navigator: Navigator

    /* DELEGATES */
    protected val dialogDelegate: DialogDelegate? get() = baseActivity.dialogDelegate
    protected val keyboardDelegate: KeyboardDelegate? get() = baseActivity.keyboardDelegate
    private val viewBindingDelegate by lazy { ViewBindingDelegate() }

    protected var statusBarHeight: Int = 0

    protected open fun initClicks() {}
    protected open fun observeLiveData() {}
    protected open fun removeLiveDataObservers() {}

    val contentView get() = requireView()

    fun <Binding : ViewBinding> viewBinding(factory: BindingFactory<Binding>) =
        viewBindingDelegate.createPrimaryViewBindingHandler(factory)

    fun <Binding : ViewBinding> viewBinding(@IdRes viewId: Int, binder: (View) -> Binding) =
        viewBindingDelegate.createSecondaryViewBindingHandler {
            binder(it.findViewById(viewId))
        }

    /**
     * Method to apply screen insets: bottomBar offset or statusBar offset.
     * It is invoked only once, when [statusBarHeight] is found.
     */
    open fun onApplyScreenInsets() {
        log.debug("onApplyScreenInsets statusBarHeight = $statusBarHeight")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        log.debug("attached")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log.info("created")
        onCreated()
    }

    operator fun <T : ViewBinding> T.invoke(init: T.() -> Unit): T = this.apply(init)

    override fun onCreateView(
        inflater: LayoutInflater,
        view: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflateNested(
            view,
            viewBindingDelegate.terminalInflater(inflater),
            this.safeCast<LoadingView>()?.layerInflater(inflater)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log.debug("view created")
        (statusBarHeight.takeIf { it > 0 } ?: baseActivity.statusBarHeight.takeIf { it > 0 })
            ?.also {
                statusBarHeight = it
                onApplyScreenInsets()
            }
        view.setOnApplyWindowInsetsListener { _, insets ->
            val invokeInsetListener = statusBarHeight == 0
            statusBarHeight = baseActivity.statusBarHeight
            if (invokeInsetListener) onApplyScreenInsets()
            insets
        }

        observeLiveData()
        initClicks()
    }

    override fun onStart() {
        super.onStart()
        log.debug("started")
    }

    override fun onResume() {
        super.onResume()
        log.debug("resumed")
        viewModel.attach()
    }

    override fun onPause() {
        super.onPause()
        log.debug("paused")
        keyboardDelegate?.hideKeyboard()
        viewModel.detach()
        removeLiveDataObservers()
    }

    override fun onStop() {
        super.onStop()
        log.debug("stopped")
    }

    override fun onDestroyView() {
        log.debug("view destroyed")
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
        log.debug("detached")
    }

    override fun onDestroy() {
        super.onDestroy()
        log.debug("destroyed")
        if (baseActivity.isFinishing) {
            onDestroyed()
            return
        }

        var anyParentIsRemoving = false
        var parent = parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }

        if (isRemoving || anyParentIsRemoving) {
            onDestroyed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        log.debug("state saved")
    }

    /**
     * Method following [onCreate] event.
     *
     * This method needs to be implemented in child fragments, for instance to inject DI scopes.
     * Called once after in [onCreate] method.
     */
    protected open fun onCreated() {
        log.debug("onCreated")
    }

    /**
     * Method following [onDestroy] event.
     *
     * Called in [onDestroy] method.
     */
    protected open fun onDestroyed() {
        log.debug("onDestroyed")
    }

    /**
     * Method for handling [android.app.Activity.onBackPressed].
     *
     * @return boolean (true - stop handling, false - continue handling)
     */
    open fun onBackPressed() = false

    protected fun tryBackPress(fragment: Fragment?) = when (fragment) {
        is BackListener -> fragment.onBackPressed()
        else -> false
    }

    open fun performBackPress() = baseActivity.onBackPressed()

    /* STATE */

    open fun showError(message: String?, okAction: (() -> Unit)? = null) {
        if (activity == null) {
            log.warn("showError() called from dead fragment")
            return
        }
        message?.let {
            dialogDelegate?.showOkDialog(
                title = getString(R.string.error),
                message = message,
                okAction = okAction
            )
        }
    }

    open fun showErrorWithSnackbar(message: String, parent: View) {
        Snackbar.make(parent, message, Snackbar.LENGTH_LONG).show()
    }

    open fun cancelDialog() {
        dialogDelegate?.cancelDialog()
    }

    protected fun View.applyStatusBarInset() {
        setMarginExtra(topMargin = statusBarHeight)
    }

    protected fun View.applyStatusBarInsetWithPadding() {
        setPaddingExtra(topPadding = statusBarHeight)
    }
}
