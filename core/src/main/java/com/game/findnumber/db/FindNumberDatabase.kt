package com.game.findnumber.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.game.findnumber.model.Level

@Database(entities = [Level::class], version = 2)
abstract class FindNumberDatabase : RoomDatabase() {

    abstract fun levelDao(): LevelDao

    companion object {

        var instance: FindNumberDatabase? = null

        fun getInstance(context: Context): FindNumberDatabase? {
            if (instance == null) {
                synchronized(FindNumberDatabase::class) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            FindNumberDatabase::class.java,
                            FindNumberDatabase::class.java.simpleName
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }

            return instance
        }

    }
}
