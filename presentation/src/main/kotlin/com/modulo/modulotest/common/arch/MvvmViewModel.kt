package com.modulo.modulotest.common.arch

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.modulo.core.logger.logger

abstract class MvvmViewModel : ViewModel() {

    protected abstract val tag: String
    protected val log by lazy { logger(tag) }
    var savedState: SavedStateHandle = SavedStateHandle()

    open fun attach() {
    }

    open fun detach() {
    }

    override fun onCleared() {
        super.onCleared()
        log.debug("cleared")
    }
}
