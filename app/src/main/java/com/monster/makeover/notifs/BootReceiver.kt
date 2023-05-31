package com.monster.makeover.notifs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if ("android.intent.action.BOOT_COMPLETED" == intent?.action) {
            Log.d("NOTIFICATION", "on receive boot")
            if (context != null) {
                scheduleDailyNotification(context, afterReboot = true)
            }
        }
    }
}