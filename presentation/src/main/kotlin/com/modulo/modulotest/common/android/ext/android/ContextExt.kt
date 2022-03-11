package com.modulo.modulotest.common.android.ext.android

import android.content.Context
import android.view.LayoutInflater

val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)
