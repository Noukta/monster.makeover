package com.monster.makeover.constants

import android.app.AlarmManager.INTERVAL_DAY
import android.app.AlarmManager.INTERVAL_FIFTEEN_MINUTES
import android.app.AlarmManager.INTERVAL_HOUR
import com.monster.makeover.BuildConfig

object URL {
    const val PRIVACY_POLICY = "https://wkeystudio.blogspot.com/2023/04/privacy-policy.html"
    const val PLAY_STORE =
        "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
}

object Time {
    const val RESET_REWARD_INTERVAL: Long = INTERVAL_FIFTEEN_MINUTES
    const val RESET_DAILY_GIFT_INTERVAL: Long = INTERVAL_DAY
    const val PLAY_TIME_BEFORE_REVIEW: Long = INTERVAL_FIFTEEN_MINUTES
    const val UPDATE_SHARE_INTERVAL: Long = INTERVAL_HOUR
}
// Test values
/*object Time {
    const val RESET_REWARD_INTERVAL: Long = 10_000L
    const val RESET_DAILY_GIFT_INTERVAL: Long = 20_000L
    const val PLAY_TIME_BEFORE_REVIEW: Long = 60_000L
}*/

object Game{
    const val InitAvailableCoins = 100
    const val DailyGift = 100
    const val Reward = 40
    const val ItemValue = 20
}

enum class ItemType{
    Head, Eye, Mouth, Accessory, Body
}

enum class ReviewChoice{
    NOT_ENJOY, YES, NO, REMIND
}