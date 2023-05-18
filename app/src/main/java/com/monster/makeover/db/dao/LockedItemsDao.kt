package com.monster.makeover.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.monster.makeover.db.obj.MonsterItem

@Dao
interface LockedItemsDao {
    @Query("SELECT * FROM lockedItems")
    fun getAll(): List<MonsterItem>

    @Query("SELECT * FROM lockedItems WHERE id = :id")
    fun findById(id: Int): MonsterItem

    @Query("SELECT EXISTS (SELECT 1 FROM lockedItems WHERE id = :id)")
    fun exists(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg wallpaper: MonsterItem)

    @Delete
    fun delete(wallpaper: MonsterItem)

    @Query("DELETE FROM lockedItems")
    fun deleteAll()
}