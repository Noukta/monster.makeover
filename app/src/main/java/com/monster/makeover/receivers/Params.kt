package com.monster.makeover.receivers

import android.app.AlarmManager

var isAppRunning = true

// for release
internal const val REWARDS_INTERVAL = AlarmManager.INTERVAL_HOUR * 6
internal const val DAILY_INTERVAL = AlarmManager.INTERVAL_DAY
// for debug
//internal const val REWARDS_INTERVAL = 1000*60L // 30 sec
//internal const val DAILY_INTERVAL = 1000*90L // 90 sec