package com.monster.makeover.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.monster.makeover.data.unlockedItems
import com.monster.makeover.db.dao.UnlockedItemsDao
import com.monster.makeover.db.obj.MonsterItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            getInstance(context).unlockedItemsDao().insertAll(unlockedItems)
                        }
                    }

                    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                        super.onDestructiveMigration(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            getInstance(context).unlockedItemsDao().insertAll(unlockedItems)
                        }
                    }
                })
                .build()
    }
}