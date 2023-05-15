package com.monster.makeover.utils

import android.content.Context

class PreferencesHelper(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

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

    fun getAppOpenMaxToReview(): Int {
        return sharedPreferences.getInt("AppOpenMaxToReview", 0)
    }
}
