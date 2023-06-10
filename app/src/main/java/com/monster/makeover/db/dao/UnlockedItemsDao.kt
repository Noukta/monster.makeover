package com.monster.makeover.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.monster.makeover.db.obj.MonsterItem

@Dao
interface UnlockedItemsDao {
    @Query("SELECT * FROM unlockedItems")
    fun getAll(): List<MonsterItem>

    @Query("SELECT * FROM unlockedItems WHERE id = :id")
    fun findById(id: Int): MonsterItem

    @Query("SELECT EXISTS (SELECT 1 FROM unlockedItems WHERE id = :id)")
    fun exists(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(monsterItems: List<MonsterItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(monsterItems: MonsterItem)

    @Delete
    fun delete(monsterItem: MonsterItem)

    @Query("DELETE FROM unlockedItems")
    fun deleteAll()
}