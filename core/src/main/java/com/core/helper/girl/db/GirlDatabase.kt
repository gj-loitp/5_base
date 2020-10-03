package com.core.helper.girl.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.core.helper.girl.model.GirlPage

@Database(entities = [GirlPage::class], version = 1)
abstract class GirlDatabase : RoomDatabase() {

    //@TypeConverters(DateTypeConverter::class, AreaConverter::class)

    abstract fun girlPageDao(): GirlPageDao

    companion object {

        var instance: GirlDatabase? = null

        fun getInstance(context: Context): GirlDatabase? {
            if (instance == null) {
                synchronized(GirlDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext, GirlDatabase::class.java, GirlDatabase::class.java.simpleName)
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }

            return instance
        }

    }
}
