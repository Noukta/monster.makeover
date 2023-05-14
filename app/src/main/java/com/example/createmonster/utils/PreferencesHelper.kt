package com.example.createmonster.utils

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
}
