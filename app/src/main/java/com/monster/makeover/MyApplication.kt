package com.monster.makeover

import android.app.Application
import com.monster.makeover.ads.UnityAdsManager
import com.monster.makeover.db.DatabaseHolder
import com.monster.makeover.utils.PreferencesHelper
import com.monster.makeover.utils.SoundHelper

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseHolder().create(this)
        //Initialize SoundHelper
        SoundHelper.init(this)
        //Initialize preferencesHelper
        PreferencesHelper.init(this)
        //Initialize UnityAdsManager
        UnityAdsManager.initialize(this)
    }
}
