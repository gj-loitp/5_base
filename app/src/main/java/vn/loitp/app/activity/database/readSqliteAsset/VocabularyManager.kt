package vn.loitp.app.activity.database.readSqliteAsset

import android.annotation.SuppressLint
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.loitpcore.utils.util.AppUtils.Companion.appPackageName
import com.loitpcore.utils.util.AppUtils.Companion.appVersionCode
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class VocabularyManager(
    private val context: Context
) : SQLiteOpenHelper(
    context,
    DB_NAME,
    null,
    DATABASE_VERSION
) {

    companion object {
        @SuppressLint("SdCardPath")
        private val DB_PATH = "/data/data/$appPackageName/databases/"
        private const val DB_NAME = "vocabulary.sqlite"
        private val DATABASE_VERSION = appVersionCode
        private const val TABLE_NAME = "word"
        @Suppress("unused")
        private const val KEY_ID = "_id"
        @Suppress("unused")
        private const val KEY_SWORD = "sword"
        @Suppress("unused")
        private const val KEY_SPHONETIC = "sphonetic"
        @Suppress("unused")
        private const val KEY_SMEANINGS = "smeanings"
        @Suppress("unused")
        private const val KEY_SSUMMARY = "ssummary"
        @Suppress("unused")
        private const val KEY_SISOXFORDLIST = "sisoxfordlist"
    }

    private var sqLiteDatabase: SQLiteDatabase? = null
    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    @Throws(SQLException::class)
    @Suppress("unused")
    fun openDatabase() {
        val myPath = DB_PATH + DB_NAME
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
    }

    @Synchronized
    override fun close() {
        sqLiteDatabase?.close()
        super.close()
    }

    private fun checkDatabase(): Boolean {
        var checkDB: SQLiteDatabase? = null
        try {
            val myPath = DB_PATH + DB_NAME
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
        checkDB?.close()
        return checkDB != null
    }

    @Throws(IOException::class)
    private fun copyDatabase() {
        val myInput = context.assets.open(DB_NAME)
        val outFileName = DB_PATH + DB_NAME
        val outputStream: OutputStream = FileOutputStream(outFileName)
        val buffer = ByteArray(1024)
        var length: Int
        while (myInput.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }
        outputStream.flush()
        outputStream.close()
        myInput.close()
    }

    fun createDatabase() {
        val dbExist = checkDatabase()
        if (dbExist) {
            // khong lam gi ca, database da co roi
        } else {
            this.readableDatabase
            try {
                copyDatabase() // chep du lieu
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    val allVocabulary: List<Vocabulary>
        get() {
            val vocabularyList: MutableList<Vocabulary> = ArrayList()
            try {
                val sqLiteDatabase = this.readableDatabase
                val cursor = sqLiteDatabase.rawQuery("select * from $TABLE_NAME", null)
                cursor.moveToFirst()
                do {
                    val vocabulary = Vocabulary()
                    vocabulary.id = cursor.getString(0).toInt()
                    vocabulary.sword = cursor.getString(1)
                    vocabulary.sphonetic = cursor.getString(2)
                    vocabulary.smeanings = cursor.getString(3)
                    vocabulary.ssummary = cursor.getString(4)
                    vocabulary.sisoxfordlist = cursor.getString(5).toInt()
                    vocabulary.isClose = true
                    vocabularyList.add(vocabulary)
                } while (cursor.moveToNext())
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return vocabularyList
        }

    @Suppress("unused")
    fun getNRandomVocabulary(first: Int, last: Int): List<Vocabulary> {
        val vocabularyList: MutableList<Vocabulary> = ArrayList()
        val limit = "$first,$last"
        val sqLiteDatabase = this.readableDatabase
        val cursor = sqLiteDatabase.query(
            TABLE_NAME, // ten bang
            null, // danh sach cot can lay
            null, // dieu kien where
            null, // doi so dieu kien where
            null, // bieu thuc groupby
            null, // bieu thuc having
            null, // "random()", //bieu thuc orderby
            limit // "0,3" //bieuthuc limit
        )
        cursor.moveToFirst()
        do {
            val vocabulary = Vocabulary()
            vocabulary.id = cursor.getString(0).toInt()
            vocabulary.sword = cursor.getString(1)
            vocabulary.sphonetic = cursor.getString(2)
            vocabulary.smeanings = cursor.getString(3)
            vocabulary.ssummary = cursor.getString(4)
            vocabulary.sisoxfordlist = cursor.getString(5).toInt()
            vocabulary.isClose = true
            vocabularyList.add(vocabulary)
        } while (cursor.moveToNext())
        return vocabularyList
    }
}
