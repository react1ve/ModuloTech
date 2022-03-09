package com.example.template.common.navigation

import com.example.core.common.safeCast
import com.example.template.common.BaseActivity
import com.example.template.common.BaseFragment
import com.example.template.common.android.ContextWrapper

interface BaseNavigationProvider {

    val context: ContextWrapper

    val navigationActivity: BaseActivity?
        get() = context.activity?.safeCast<BaseActivity>()

    val navigationFragment: BaseFragment?
        get() = context.fragment?.safeCast<BaseFragment>()
}
