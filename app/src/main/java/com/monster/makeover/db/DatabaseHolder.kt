package com.monster.makeover.db

import android.content.Context

class DatabaseHolder {
    fun create(applicationContext: Context) {
        Database = AppDatabase.getInstance(applicationContext)
    }

    companion object {
        const val DATABASE_NAME = "monster.makeover"
        lateinit var Database: AppDatabase
    }
}