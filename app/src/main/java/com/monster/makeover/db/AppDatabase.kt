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
            MonsterItem(R.drawable.btn_head_25),
            MonsterItem(R.drawable.btn_head_26),
//        MonsterItem(R.drawable.btn_head_27),
            MonsterItem(R.drawable.btn_head_28),
            MonsterItem(R.drawable.btn_head_29),
            MonsterItem(R.drawable.btn_head_30),
            MonsterItem(R.drawable.btn_head_31),
            MonsterItem(R.drawable.btn_head_32),
            MonsterItem(R.drawable.btn_head_33),
            MonsterItem(R.drawable.btn_head_34),
            MonsterItem(R.drawable.btn_head_35),
            MonsterItem(R.drawable.btn_eye_42),
            MonsterItem(R.drawable.btn_eye_43),
            MonsterItem(R.drawable.btn_eye_44),
            MonsterItem(R.drawable.btn_eye_45),
            MonsterItem(R.drawable.btn_eye_46),
            MonsterItem(R.drawable.btn_eye_47),
            MonsterItem(R.drawable.btn_eye_48),
            MonsterItem(R.drawable.btn_eye_49),
            MonsterItem(R.drawable.btn_eye_50),
            MonsterItem(R.drawable.btn_eye_51),
            MonsterItem(R.drawable.btn_eye_52),
            MonsterItem(R.drawable.btn_eye_53),
            MonsterItem(R.drawable.btn_eye_54),
            MonsterItem(R.drawable.btn_eye_55),
            MonsterItem(R.drawable.btn_eye_56),
            MonsterItem(R.drawable.btn_eye_57),
            MonsterItem(R.drawable.btn_eye_58),
            MonsterItem(R.drawable.btn_eye_59),
            MonsterItem(R.drawable.btn_eye_60),
            MonsterItem(R.drawable.btn_eye_61),
            MonsterItem(R.drawable.btn_eye_62),
            MonsterItem(R.drawable.btn_mouth_24),
            MonsterItem(R.drawable.btn_mouth_25),
            MonsterItem(R.drawable.btn_mouth_26),
            MonsterItem(R.drawable.btn_mouth_27),
            MonsterItem(R.drawable.btn_mouth_28),
            MonsterItem(R.drawable.btn_mouth_29),
            MonsterItem(R.drawable.btn_mouth_30),
            MonsterItem(R.drawable.btn_mouth_31),
            MonsterItem(R.drawable.btn_mouth_32),
            MonsterItem(R.drawable.btn_mouth_33),
            MonsterItem(R.drawable.btn_mouth_34),
            MonsterItem(R.drawable.btn_acc_15),
            MonsterItem(R.drawable.btn_acc_16),
            MonsterItem(R.drawable.btn_acc_17),
            MonsterItem(R.drawable.btn_acc_18),
            MonsterItem(R.drawable.btn_acc_19),
            MonsterItem(R.drawable.btn_acc_20),
            MonsterItem(R.drawable.btn_acc_21),
            MonsterItem(R.drawable.btn_acc_22),
            MonsterItem(R.drawable.btn_acc_23),
            MonsterItem(R.drawable.btn_acc_24),
            MonsterItem(R.drawable.btn_acc_25),
            MonsterItem(R.drawable.btn_acc_26),
            MonsterItem(R.drawable.btn_acc_27),
            MonsterItem(R.drawable.btn_acc_28),
            MonsterItem(R.drawable.btn_acc_29),
            MonsterItem(R.drawable.btn_body_23),
            MonsterItem(R.drawable.btn_body_24),
            MonsterItem(R.drawable.btn_body_25),
            MonsterItem(R.drawable.btn_body_26),
            MonsterItem(R.drawable.btn_body_27),
            MonsterItem(R.drawable.btn_body_28),
//        MonsterItem(R.drawable.btn_body_29),
            MonsterItem(R.drawable.btn_body_30),
//        MonsterItem(R.drawable.btn_body_31),
//        MonsterItem(R.drawable.btn_body_32),
//        MonsterItem(R.drawable.btn_body_33),
//        MonsterItem(R.drawable.btn_body_34),
            MonsterItem(R.drawable.btn_body_35),
//        MonsterItem(R.drawable.btn_body_36)
        )
    }
}