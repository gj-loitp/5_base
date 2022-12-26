package com.loitp.game.findNumber.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.loitp.game.findNumber.model.Level

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Database(entities = [Level::class], version = 1)
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
