package com.monster.makeover.utils

import android.content.Context

class PreferencesHelper(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)


    val initRewardsToClaim = 3
    private val dailyGift = 60

    fun incrementAppOpenCount() {
        val count = getAppOpenCount() + 1
        sharedPreferences.edit().putInt("AppOpenCount", count).apply()
    }

    fun getAppOpenCount(): Int {
        return sharedPreferences.getInt("AppOpenCount", 0)
    }

    fun incrementAppOpenMaxToReview() {
        var count = getAppOpenMaxToReview() + 5
        if (count == 1000) count = 5
        sharedPreferences.edit().putInt("AppOpenMaxToReview", count).apply()
    }

    fun resetDailyGift() {
        sharedPreferences.edit().putBoolean("DailyGift", true).apply()
    }

    fun takeDailyGift() {
        sharedPreferences.edit().putBoolean("DailyGift", false).apply()
        addCoins(dailyGift)
    }

    fun checkDailyGift(): Boolean {
        return sharedPreferences.getBoolean("DailyGift", true)

    }

    fun getAppOpenMaxToReview(): Int {
        return sharedPreferences.getInt("AppOpenMaxToReview", 0)
    }

    fun addCoins(coins: Int): Boolean {
        val newCoins = coins + getCoins()
        return if (newCoins < 0)
            false
        else {
            sharedPreferences.edit().putInt("Coins", newCoins).apply()
            true
        }
    }

    fun getCoins(): Int {
        return sharedPreferences.getInt("Coins", 20)
    }

    fun decrementRewardsToClaim(): Boolean {
        val currentRewardsToClaim = getRewardsToClaim() - 1
        sharedPreferences.edit().putInt("RewardsToClaim", currentRewardsToClaim).apply()
        return currentRewardsToClaim > 0
    }

    fun resetRewardsToClaim() {
        sharedPreferences.edit().putInt("RewardsToClaim", initRewardsToClaim).apply()
    }

    fun getRewardsToClaim(): Int {
        return sharedPreferences.getInt("RewardsToClaim", initRewardsToClaim)
    }

    fun setNextPermissionRequestTime(time: Long) {
        sharedPreferences.edit().putLong("NextRequestTime", time).apply()
    }

    fun getNextPermissionRequestTime(): Long {
        return sharedPreferences.getLong("NextRequestTime", 0L)
    }

    fun setPostNotificationsGranted(opened: Boolean) {
        sharedPreferences.edit().putBoolean("PostNotificationsGranted", opened).apply()
    }

    fun isPostNotificationsGranted(): Boolean {
        return sharedPreferences.getBoolean("PostNotificationsGranted", false)
    }
}
