package vn.loitp.app.activity.api.truyentranhtuan.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic
import vn.loitp.app.activity.database.room.converter.AreaConverter
import vn.loitp.app.activity.database.room.db.FloorPlanDao
import vn.loitp.app.activity.database.room.model.FloorPlan

@Database(entities = [Comic::class], version = 1)
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
