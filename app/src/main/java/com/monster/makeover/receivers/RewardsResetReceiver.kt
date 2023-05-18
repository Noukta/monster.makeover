package com.monster.makeover.receivers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.monster.makeover.utils.PreferencesHelper

class RewardsResetReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("REWARD", "RewardsReset Receive")
        context?.let {
            with(PreferencesHelper(it)) { resetRewardsToClaim() }
        }
    }
}

fun scheduleRewardReset(context: Context) {
    Log.d("REWARD", "schedulePeriodicReward")
    val alarmManager =
        context.applicationContext?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, RewardsResetReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        1003,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )
    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        System.currentTimeMillis(),
        REWARDS_INTERVAL,
        pendingIntent
    )
}