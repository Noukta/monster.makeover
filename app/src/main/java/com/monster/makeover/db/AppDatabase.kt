package com.monster.makeover.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.monster.makeover.R
import com.monster.makeover.db.dao.LockedItemsDao
import com.monster.makeover.db.obj.MonsterItem
import com.monster.makeover.ext.query

@Database(
    version = 1,
    entities = [
        MonsterItem::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lockedItemsDao(): LockedItemsDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, DatabaseHolder.DATABASE_NAME)
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        query {
                            getInstance(context).lockedItemsDao().insertAll(PREPOPULATE_DATA)
                        }
                    }
                })
                .build()

        val PREPOPULATE_DATA = listOf(
            MonsterItem(R.drawable.head_25),
            MonsterItem(R.drawable.head_26),
//        MonsterItem(R.drawable.head_27),
            MonsterItem(R.drawable.head_28),
            MonsterItem(R.drawable.head_29),
            MonsterItem(R.drawable.head_30),
            MonsterItem(R.drawable.head_31),
            MonsterItem(R.drawable.head_32),
            MonsterItem(R.drawable.head_33),
            MonsterItem(R.drawable.head_34),
            MonsterItem(R.drawable.head_35),
            MonsterItem(R.drawable.eye_42),
            MonsterItem(R.drawable.eye_43),
            MonsterItem(R.drawable.eye_44),
            MonsterItem(R.drawable.eye_45),
            MonsterItem(R.drawable.eye_46),
            MonsterItem(R.drawable.eye_47),
            MonsterItem(R.drawable.eye_48),
            MonsterItem(R.drawable.eye_49),
            MonsterItem(R.drawable.eye_50),
            MonsterItem(R.drawable.eye_51),
            MonsterItem(R.drawable.eye_52),
            MonsterItem(R.drawable.eye_53),
            MonsterItem(R.drawable.eye_54),
            MonsterItem(R.drawable.eye_55),
            MonsterItem(R.drawable.eye_56),
            MonsterItem(R.drawable.eye_57),
            MonsterItem(R.drawable.eye_58),
            MonsterItem(R.drawable.eye_59),
            MonsterItem(R.drawable.eye_60),
            MonsterItem(R.drawable.eye_61),
            MonsterItem(R.drawable.eye_62),
            MonsterItem(R.drawable.mouth_24),
            MonsterItem(R.drawable.mouth_25),
            MonsterItem(R.drawable.mouth_26),
            MonsterItem(R.drawable.mouth_27),
            MonsterItem(R.drawable.mouth_28),
            MonsterItem(R.drawable.mouth_29),
            MonsterItem(R.drawable.mouth_30),
            MonsterItem(R.drawable.mouth_31),
            MonsterItem(R.drawable.mouth_32),
            MonsterItem(R.drawable.mouth_33),
            MonsterItem(R.drawable.mouth_34),
            MonsterItem(R.drawable.acc_15),
            MonsterItem(R.drawable.acc_16),
            MonsterItem(R.drawable.acc_17),
            MonsterItem(R.drawable.acc_18),
            MonsterItem(R.drawable.acc_19),
            MonsterItem(R.drawable.acc_20),
            MonsterItem(R.drawable.acc_21),
            MonsterItem(R.drawable.acc_22),
            MonsterItem(R.drawable.acc_23),
            MonsterItem(R.drawable.acc_24),
            MonsterItem(R.drawable.acc_25),
            MonsterItem(R.drawable.acc_26),
            MonsterItem(R.drawable.acc_27),
            MonsterItem(R.drawable.acc_28),
            MonsterItem(R.drawable.acc_29),
            MonsterItem(R.drawable.body_23),
            MonsterItem(R.drawable.body_24),
            MonsterItem(R.drawable.body_25),
            MonsterItem(R.drawable.body_26),
            MonsterItem(R.drawable.body_27),
            MonsterItem(R.drawable.body_28),
//        MonsterItem(R.drawable.body_29),
            MonsterItem(R.drawable.body_30),
//        MonsterItem(R.drawable.body_31),
//        MonsterItem(R.drawable.body_32),
//        MonsterItem(R.drawable.body_33),
//        MonsterItem(R.drawable.body_34),
            MonsterItem(R.drawable.body_35),
//        MonsterItem(R.drawable.body_36)
        )
    }
}