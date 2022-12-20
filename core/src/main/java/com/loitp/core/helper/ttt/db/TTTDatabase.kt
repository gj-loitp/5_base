package com.loitp.core.helper.ttt.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.loitp.core.helper.ttt.model.comic.Comic

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Database(
    entities = [Comic::class],
    version = 2
)
abstract class TTTDatabase : RoomDatabase() {

    abstract fun tttDao(): TTTDao

    companion object {

        var instance: TTTDatabase? = null

        fun getInstance(context: Context): TTTDatabase? {
            if (instance == null) {
                synchronized(TTTDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TTTDatabase::class.java,
                        TTTDatabase::class.simpleName
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return instance
        }
    }
}
