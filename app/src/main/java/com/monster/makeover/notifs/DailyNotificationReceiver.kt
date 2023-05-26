package com.monster.makeover.notifs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.monster.makeover.notifs.Settings.DAILY_CHANNEL_ID
import com.monster.makeover.notifs.Settings.DAILY_CHANNEL_NAME
import com.monster.makeover.notifs.Settings.DAILY_NOTIFICATION_CONTENT
import com.monster.makeover.notifs.Settings.DAILY_NOTIFICATION_ID
import com.monster.makeover.notifs.Settings.DAILY_NOTIFICATION_TITLE

class DailyNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("NOTIFICATION", "on receive")
        context?.let {
            Log.d("NOTIFICATION", "on receive context")
            createNotificationChannel(DAILY_CHANNEL_ID, DAILY_CHANNEL_NAME, it)
            showNotification(DAILY_NOTIFICATION_ID, DAILY_NOTIFICATION_TITLE, DAILY_NOTIFICATION_CONTENT, DAILY_CHANNEL_ID, context)
        }
    }
}