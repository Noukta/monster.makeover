package com.monster.makeover.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.monster.makeover.db.dao.LockedItemsDao
import com.monster.makeover.db.obj.MonsterItem

@Database(
    version = 1,
    entities = [
        MonsterItem::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lockedItemsDao(): LockedItemsDao
}