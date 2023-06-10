package com.monster.makeover.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.monster.makeover.data.UNLOCKED_ACCS
import com.monster.makeover.data.UNLOCKED_BODIES
import com.monster.makeover.data.UNLOCKED_EYES
import com.monster.makeover.data.UNLOCKED_HEADS
import com.monster.makeover.data.UNLOCKED_MOUTHS
import com.monster.makeover.db.dao.UnlockedItemsDao
import com.monster.makeover.db.obj.MonsterItem
import com.monster.makeover.ext.query

@Database(
    version = 2,
    entities = [
        MonsterItem::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun unlockedItemsDao(): UnlockedItemsDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, DatabaseHolder.DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                        super.onDestructiveMigration(db)
                        query {
                            val unlockedItems =
                                UNLOCKED_HEADS + UNLOCKED_EYES + UNLOCKED_MOUTHS + UNLOCKED_ACCS + UNLOCKED_BODIES
                            getInstance(context).unlockedItemsDao().insertAll(unlockedItems)
                        }
                    }
                })
                .build()
    }
}