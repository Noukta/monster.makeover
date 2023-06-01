package com.monster.makeover.utils

import android.content.Context
import android.content.SharedPreferences
import com.monster.makeover.constants.Time
import com.monster.makeover.constants.Game
import com.monster.makeover.constants.ReviewChoice

object PreferencesHelper {
    // Keys
    private const val reviewStatus = "reviewStatus"
    private const val totalPlayTime = "TotalPlayTime"
    private const val lastPostNotificationsRequestTime = "LastPostNotificationsRequestTime"
    private const val lastDailyGiftTime = "LastDailyGiftTime"
    private const val lastRewardTime = "LastRewardTime"
    private const val availableCoins = "AvailableCoins"
    private const val lastShareTime = "LastShareTime"
    private const val isConsentAccepted = "IsConsentAccepted"
    
    private const val prefFile = "preferences"
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE)
    }

    private fun getBoolean(key: String, defValue: Boolean = true) = preferences.getBoolean(key, defValue)

    private fun setBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    private fun getInt(key: String, defValue: Int = 0) = preferences.getInt(key, defValue)

    private fun setInt(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    private fun getLong(key: String, defValue: Long = 0) = preferences.getLong(key, defValue)

    private fun setLong(key: String, value: Long) {
        preferences.edit().putLong(key, value).apply()
    }

    fun acceptConsent(){
        setBoolean(isConsentAccepted, true)
    }
    fun isConsentAccepted(): Boolean{
        return getBoolean(isConsentAccepted, false)
    }
    fun setReviewStatus(reviewChoice: ReviewChoice){
        setInt(reviewStatus, reviewChoice.ordinal)
    }
    fun getReviewStatus(): ReviewChoice{
        val ordinal = getInt(reviewStatus, ReviewChoice.REMIND.ordinal)
        return ReviewChoice.values()[ordinal]
    }
    fun setTotalPlayTime(time: Long){
        setLong(totalPlayTime, time)
    }

    fun getTotalPlayTime(): Long{
        return getLong(totalPlayTime, 0)
    }
    fun resetLastDailyGiftTime() {
        val currentTime= System.currentTimeMillis()
        setLong(lastDailyGiftTime, currentTime)
    }

    private fun getLastDailyGiftTime(): Long {
        return getLong(lastDailyGiftTime)
    }
    
    fun isDailyGiftAvailable(): Boolean{
        val currentTime= System.currentTimeMillis()
        return currentTime - getLastDailyGiftTime() >= Time.RESET_DAILY_GIFT_INTERVAL
    }

    fun resetLastRewardTime() {
        val currentTime= System.currentTimeMillis()
        setLong(lastRewardTime, currentTime)
    }

    private fun getLastRewardTime(): Long {
        return getLong(lastRewardTime)
    }

    fun isRewardAvailable(): Boolean{
        val currentTime= System.currentTimeMillis()
        return currentTime - getLastRewardTime() >= Time.RESET_REWARD_INTERVAL
    }

    fun addCoins(coinsToAdd: Int): Boolean {
        val currentAvailableCoins = coinsToAdd + getAvailableCoins()
        return if (currentAvailableCoins < 0)
            false
        else {
            setInt(availableCoins, currentAvailableCoins)
            true
        }
    }

    fun getAvailableCoins(): Int {
        return getInt(availableCoins, Game.InitAvailableCoins)
    }

    fun resetLastPostNotificationsRequestTime() {
        val currentTime= System.currentTimeMillis()
        setLong(lastPostNotificationsRequestTime, currentTime)
    }

    fun getLastPostNotificationsRequestTime(): Long {
        return getLong(lastPostNotificationsRequestTime, 0)
    }

    fun updateLastShareTime(){
        val currentTime= System.currentTimeMillis()
        setLong(lastShareTime, currentTime)
    }

    private fun getLastShareTime(): Long {
        return getLong(lastShareTime, 0)
    }

    fun isSharedRecently(): Boolean{
        val currentTime= System.currentTimeMillis()
        return currentTime - getLastShareTime() <= Time.UPDATE_SHARE_INTERVAL
    }
}
