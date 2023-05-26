package com.monster.makeover.notifs

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.monster.makeover.notifs.Settings.DAILY_NOTIFICATION_INTERVAL

fun scheduleDailyNotification(context: Context){
    val alarmManager = context.applicationContext
        .getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, DailyNotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        1002,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )
    alarmManager.setInexactRepeating(
        AlarmManager.RTC_WAKEUP,
        System.currentTimeMillis() + DAILY_NOTIFICATION_INTERVAL,
        DAILY_NOTIFICATION_INTERVAL,
        pendingIntent
    )
}