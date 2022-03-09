package com.example.template.lifecycle

import android.app.Activity
import android.os.Bundle

class AppLifecycleDelegate : AppActivityLifecycleCallbacks {

    private var activitiesCreated = 0

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        ++activitiesCreated
    }

    override fun onActivityDestroyed(activity: Activity) {
        --activitiesCreated
    }
}
