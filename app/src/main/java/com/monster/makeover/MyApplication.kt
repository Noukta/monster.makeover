package com.monster.makeover

import android.app.Application
import com.monster.makeover.db.DatabaseHolder
import com.monster.makeover.receivers.isAppRunning
import com.monster.makeover.utils.PreferencesHelper
import com.monster.makeover.utils.UnityAdsManager

class MyApplication : Application() {
    private lateinit var preferencesHelper: PreferencesHelper
    override fun onCreate() {
        super.onCreate()
        isAppRunning = false
        DatabaseHolder().create(this)
        //Initialize preferencesHelper
        preferencesHelper = PreferencesHelper(this)
        preferencesHelper.incrementAppOpenCount()
        //Initialize UnityAdsManager
        UnityAdsManager.initialize(this)
    }
}
