package com.example.template.common.android

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

class ContextWrapper {

    private val activityReference: WeakReference<AppCompatActivity>?
    private val fragmentReference: WeakReference<Fragment>?

    val context: Context?
        get() {
            fragmentReference?.get()
                ?.takeIf { it.isAdded }
                ?.let { fragment ->
                    return fragment.context
                }
            activityReference
                ?.get()
                ?.let { activity ->
                    return activity
                }
            return null
        }

    val activity: AppCompatActivity?
        get() = activityReference?.get()
    val fragment: Fragment?
        get() = fragmentReference?.get()

    constructor(activity: AppCompatActivity) {
        activityReference = WeakReference(activity)
        fragmentReference = null
    }

    constructor(fragment: Fragment) {
        fragmentReference = WeakReference(fragment)
        activityReference = null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun shouldShowRationale(permission: String): Boolean {
        fragmentReference?.get()?.takeIf { it.isAdded }?.let { fragment ->
            return fragment.shouldShowRequestPermissionRationale(permission)
        }
        activityReference?.get()?.let { activity ->
            return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestPermissions(permissions: Array<String>, requestCode: Int) {
        fragmentReference?.get()?.takeIf { it.isAdded }?.let { fragment ->
            fragment.requestPermissions(permissions, requestCode)
            return
        }
        activityReference?.get()?.let { activity ->
            ActivityCompat.requestPermissions(activity, permissions, requestCode)
        }
    }

    fun startActivityForResult(intent: Intent, requestCode: Int) {
        fragmentReference?.get()?.takeIf { it.isAdded }?.let { fragment ->
            fragment.startActivityForResult(intent, requestCode)
            return
        }
        activityReference?.get()?.startActivityForResult(intent, requestCode)
    }
}
