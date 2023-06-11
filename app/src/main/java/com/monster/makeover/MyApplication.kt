package com.monster.makeover

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.monster.makeover.db.DatabaseHolder
import com.monster.makeover.utils.PreferencesHelper
import com.monster.makeover.utils.SoundHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseHolder().create(this)
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseHolder.Database.unlockedItemsDao().exists(0)
        }
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
