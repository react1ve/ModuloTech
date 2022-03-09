package com.example.template.common

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.core.os.ConfigurationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import com.example.core.common.safeCast
import com.example.core.logger.logger
import com.example.template.common.android.ContextWrapper
import com.example.template.common.android.delegate.DialogDelegate
import com.example.template.common.android.delegate.KeyboardDelegate
import com.example.template.common.android.ext.android.applyNoStatusBar
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
import org.koin.androidx.scope.ScopeActivity
import java.util.*

@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class BaseActivity : ScopeActivity, NestedInflater {

    constructor()

    /**
     * Constructor for cases where you don't need view binding for view interaction
     */
    constructor(bindingFactory: BindingFactory<*>) {
        viewBindingDelegate.createPrimaryViewBindingHandler(bindingFactory)
    }

    abstract val loggerTag: String
    protected val log by lazy { logger(loggerTag) }

    protected val contextWrapper: ContextWrapper get() = ContextWrapper(this)

    /* MVVM */
    protected abstract val viewModel: MvvmViewModel

    /* NAVIGATION */
    protected abstract val navigationController: NavController

    /* DELEGATES */
    private val viewBindingDelegate by lazy { ViewBindingDelegate() }
    var keyboardDelegate: KeyboardDelegate? = null
    var dialogDelegate: DialogDelegate? = null

    var statusBarHeight: Int = 0

    private var onViewCreatedCalled = false
    protected val contentView: View get() = window.decorView.rootView

    protected open fun observeLiveData() {}
    protected open fun removeLiveDataObservers() {}

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

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(fixContextLocale(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log.info("created")

        /* UI */
        applyTheme()
        val view = inflateNested(
            null,
            viewBindingDelegate.terminalInflater(layoutInflater),
            this.safeCast<LoadingView>()?.layerInflater(layoutInflater)
        )
        setContentView(view)

        keyboardDelegate = KeyboardDelegate(this)
        dialogDelegate = DialogDelegate(this)
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        view?.let { onSetContentView(it) }
    }

    protected fun onSetContentView(view: View) {
        statusBarHeight.takeIf { it > 0 }?.also { onApplyScreenInsets() }
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, windowInsets ->
            val invokeInsetListener = statusBarHeight == 0
            statusBarHeight = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            if (invokeInsetListener) onApplyScreenInsets()
            windowInsets
        }
    }

    override fun onStart() {
        super.onStart()
        if (!onViewCreatedCalled) {
            onViewCreatedCalled = true
            viewBindingDelegate.onCreateView(contentView)
        }
        log.debug("started")
    }

    override fun onResume() {
        super.onResume()
        log.debug("resumed")
        observeLiveData()
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

    override fun onDestroy() {
        super.onDestroy()
        viewBindingDelegate.onDestroyView()
        log.info("destroyed")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        log.debug("state saved")
    }

    open fun applyTheme() {
        // setup Activity theme if needed
        window.applyNoStatusBar()
    }

    fun processBackIfListener(fragment: Fragment?) = when (fragment) {
        is BackListener -> fragment.onBackPressed()
        else -> false
    }

    fun processBackToSuper() {
        super.onBackPressed()
    }

    @Suppress("DEPRECATION")
    private fun fixContextLocale(context: Context?): Context? {
        if (context == null) return context
        val resources = context.resources
        val configuration = resources.configuration
        val locale =
            when (val primaryLocale = ConfigurationCompat.getLocales(configuration).get(0)) {
                Locale.FRENCH -> primaryLocale
                Locale.FRANCE -> primaryLocale
                Locale.CANADA_FRENCH -> primaryLocale
                else -> Locale.US
            }
        Locale.setDefault(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            return context.createConfigurationContext(configuration)
        }
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }

    protected fun View.applyStatusBarInset() {
        setMarginExtra(topMargin = statusBarHeight)
    }

    protected fun View.applyStatusBarInsetWithPadding() {
        setPaddingExtra(topPadding = statusBarHeight)
    }
}
