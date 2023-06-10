package com.monster.makeover.db.obj

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unlockedItems")
data class MonsterItem(@PrimaryKey val id: Int)