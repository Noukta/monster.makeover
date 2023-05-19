package com.monster.makeover.receivers

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.monster.makeover.MainActivity
import com.monster.makeover.R
import kotlin.random.Random

class RewardsNotificationsReceiver : BroadcastReceiver() {
    private val random = Random
    private val notificationTitleList =
        listOf(R.string.notification_reward_title_1, R.string.notification_reward_title_2)
    private val notificationContentList =
        listOf(R.string.notification_reward_content_1, R.string.notification_reward_content_2)

    private val notificationId = 1
    private val notificationTitle by lazy {
        println("Calculating field...")
        notificationTitleList[random.nextInt(notificationTitleList.size)]
    }
    private val notificationContent by lazy {
        println("Calculating field...")
        notificationContentList[random.nextInt(notificationContentList.size)]
    }
    private val channelId = "reward_channel"
    private val channelName = "Reward notification"
    override fun onReceive(context: Context?, intent: Intent?) {
        if (isAppRunning) return
        Log.d("NOTIFICATION", "App not running")
        createRewardNotificationChannel(context)
        showRewardNotification(context)
    }

    @SuppressLint("MissingPermission")
    private fun showRewardNotification(context: Context?) {
        context?.let { ctx ->
            // Create an explicit intent for an Activity in your app
            val intent = Intent(ctx, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(
                    ctx,
                    2001,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )

            val notification = NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(ctx.getString(notificationTitle))
                .setContentText(ctx.getString(notificationContent))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            with(NotificationManagerCompat.from(ctx)) {
                // notificationId is a unique int for each notification that you must define
                if (areNotificationsEnabled())
                    notify(notificationId, notification)
            }
        }
    }

    private fun createRewardNotificationChannel(context: Context?) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        Log.d("WORK", "Creating reward channel")
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context?.applicationContext?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun scheduleRewardNotification(context: Context) {
    Log.d("NOTIFICATION", "schedulePeriodicNotification")
    val alarmManager =
        context.applicationContext?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, RewardsNotificationsReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        1001,
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