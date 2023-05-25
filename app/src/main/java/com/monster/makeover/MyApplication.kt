package com.monster.makeover

import android.app.Application
import com.monster.makeover.db.DatabaseHolder
import com.monster.makeover.utils.PreferencesHelper
import com.monster.makeover.utils.UnityAdsManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseHolder().create(this)
        //Initialize preferencesHelper
        PreferencesHelper.init(this)
        //Initialize UnityAdsManager
        UnityAdsManager.initialize(this)
    }
}
