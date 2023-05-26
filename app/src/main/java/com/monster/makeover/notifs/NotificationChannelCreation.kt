package com.monster.makeover.notifs

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

fun createNotificationChannel(channelId: String, channelName: String, context: Context){
    val channel =
        NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
    // Register the channel with the system
    val notificationManager = context.applicationContext
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}