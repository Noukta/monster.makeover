package com.monster.makeover

import android.app.Application
import com.google.android.gms.ads.MobileAds
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
        //Initialize Admob
        MobileAds.initialize(this) {}
        //Initialize UnityAdsManager
        //UnityAdsManager.initialize(this)
    }
}
