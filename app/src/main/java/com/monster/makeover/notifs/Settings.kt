package com.monster.makeover.notifs
import android.app.AlarmManager.INTERVAL_DAY
import com.monster.makeover.R

object Settings{
    const val POST_NOTIFICATIONS_PERMISSION_REQUEST_INTERVAL: Long = 2 * INTERVAL_DAY
    const val DAILY_NOTIFICATION_INTERVAL: Long = INTERVAL_DAY
    const val DAILY_CHANNEL_ID = "daily_gift"
    const val DAILY_CHANNEL_NAME = "Daily Gift"
    const val DAILY_NOTIFICATION_ID = 3
    val DAILY_NOTIFICATION_TITLE by lazy {
        listOf(
            R.string.notification_daily_title_1,
            R.string.notification_daily_title_2)
            .random()
    }
    val DAILY_NOTIFICATION_CONTENT by lazy {
        listOf(
            R.string.notification_daily_content_1,
            R.string.notification_daily_content_2)
            .random()
    }

}