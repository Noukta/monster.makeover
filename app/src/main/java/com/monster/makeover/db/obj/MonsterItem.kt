package com.monster.makeover.db.obj

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lockedItems")
data class MonsterItem(@PrimaryKey val id: Int)