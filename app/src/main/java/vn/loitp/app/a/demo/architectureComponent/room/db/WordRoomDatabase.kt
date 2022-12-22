package vn.loitp.app.a.demo.architectureComponent.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import vn.loitp.app.a.demo.architectureComponent.room.dao.WordDao
import vn.loitp.app.a.demo.architectureComponent.room.model.Word

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE
                ?: synchronized(lock = this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        WordRoomDatabase::class.java,
                        Word.DB_NAME_WORD
                    )
                        .addCallback(WordDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                    instance
                }
        }

        @Suppress("unused")
        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : Callback()
    }
}
