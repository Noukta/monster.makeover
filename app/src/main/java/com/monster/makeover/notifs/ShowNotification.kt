package com.monster.makeover.notifs

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.monster.makeover.MainActivity
import com.monster.makeover.R

private const val REQUEST_CODE = 2023
fun showNotification(notificationId: Int, title: Int, content: Int, channelId: String, context: Context) {
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent: PendingIntent =
        PendingIntent.getActivity(context, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE)

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle(context.getString(title))
        .setContentText(context.getString(content))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()

    with(NotificationManagerCompat.from(context)) {
        // notificationId is a unique int for each notification that you must define
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        if (areNotificationsEnabled())
            notify(notificationId, notification)
    }
}