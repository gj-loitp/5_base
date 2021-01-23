package com.core.helper.ttt.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.core.helper.ttt.model.comic.Comic

@Database(
        entities = [Comic::class],
        version = 1
)
abstract class TTTDatabase : RoomDatabase() {

    abstract fun tttDao(): TTTDao

    companion object {

        var instance: TTTDatabase? = null

        fun getInstance(context: Context): TTTDatabase? {
            if (instance == null) {
                synchronized(TTTDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext, TTTDatabase::class.java, "TTTDatabase")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }

            return instance
        }

    }
}
