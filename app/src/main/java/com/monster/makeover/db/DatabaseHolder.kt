package com.monster.makeover.db

import android.content.Context
import androidx.room.Room

class DatabaseHolder {
    fun create(applicationContext: Context) {
        Database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .createFromAsset("database/monster.makeover.db")
            .build()
    }

    companion object {
        const val DATABASE_NAME = "monster.makeover"
        lateinit var Database: AppDatabase
    }
}