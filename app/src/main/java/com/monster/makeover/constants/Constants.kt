package com.monster.makeover.constants

import com.monster.makeover.BuildConfig

object URL {
    const val PRIVACY_POLICY = "https://www.privacypolicy.example"
    const val PLAY_STORE =
        "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
}

object Time {
    private const val HOUR = 3_600_000L
    private const val DAY = 86_400_000L
    const val POST_NOTIFICATIONS_PERMISSION_REQUEST_INTERVAL: Long = 2 * DAY
    const val RESET_REWARD_INTERVAL: Long = HOUR
    const val RESET_DAILY_GIFT_INTERVAL: Long = DAY
    const val PLAY_TIME_BEFORE_REVIEW: Long = HOUR
}
// Test values
/*object Time {
    private const val HOUR = 3_600_000L
    private const val DAY = 86_400_000L
    const val POST_NOTIFICATIONS_PREMISSION_REQUEST_INTERVAL: Long = 2 * DAY
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