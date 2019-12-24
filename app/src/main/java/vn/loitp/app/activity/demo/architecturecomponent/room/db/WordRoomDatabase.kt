package vn.loitp.app.activity.demo.architecturecomponent.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import vn.loitp.app.activity.demo.architecturecomponent.room.dao.WordDao
import vn.loitp.app.activity.demo.architecturecomponent.room.model.Word

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

        private class WordDatabaseCallback(
                private val scope: CoroutineScope
        ) : RoomDatabase.Callback()

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        /*suspend fun populateDatabase(wordDao: WordDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.

            //wordDao.deleteAll()

            var word = Word()
            word.word = "Hello world!"
            wordDao.insert(word)

            word = Word()
            word.word = "Hello Room!"
            wordDao.insert(word)
        }*/
    }

}
