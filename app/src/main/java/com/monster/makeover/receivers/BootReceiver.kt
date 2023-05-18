package com.monster.makeover.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.monster.makeover.utils.PreferencesHelper

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent,action.BOOT_COMPLETED") {
            context?.let {
                scheduleRewardReset(it)
                if (with(PreferencesHelper(it)) { isPostNotificationsGranted() }) {
                    scheduleRewardNotification(it)
                    scheduleDailyNotification(it)
                }
            }
        }
    }
}