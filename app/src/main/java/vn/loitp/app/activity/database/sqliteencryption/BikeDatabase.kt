package vn.loitp.app.activity.database.sqliteencryption

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.core.utilities.LLog
import vn.loitp.app.app.LApplication
import java.util.*

class BikeDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val TAG = javaClass.simpleName

    // Getting All Bike
    val allBike: List<Bike>
        get() {
            val bikeList = ArrayList<Bike>()
            val selectQuery = "SELECT  * FROM $TABLE_BIKE"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val bike = Bike()
                    bike.id = cursor.getString(0).toLong()
                    bike.name = cursor.getString(1)
                    bike.branch = cursor.getString(2)
                    bike.hp = cursor.getString(3).toInt()
                    bike.price = cursor.getString(4).toInt()
                    bike.imgPath0 = cursor.getString(5)
                    bike.imgPath1 = cursor.getString(6)
                    bike.imgPath2 = cursor.getString(7)
                    bikeList.add(bike)
                } while (cursor.moveToNext())
            }
            cursor.close()
            return bikeList
        }

    // Getting bike Count
    val bikeCount: Int
        get() {
            val countQuery = "SELECT  * FROM $TABLE_BIKE"
            val db = this.readableDatabase
            val cursor = db.rawQuery(countQuery, null)
            var count = 0
            if (cursor != null && !cursor.isClosed) {
                count = cursor.count
                cursor.close()
            }
            return count
        }

    // Creating Tables
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_BIKE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT NOT NULL, "
                + KEY_BRANCH + " TEXT NOT NULL, "
                + KEY_HP + " INTEGER, "
                + KEY_PRICE + " INTEGER, "
                + KEY_IMG_PATH_0 + " TEXT NOT NULL, "
                + KEY_IMG_PATH_1 + " TEXT NOT NULL, "
                + KEY_IMG_PATH_2 + " TEXT NOT NULL "
                + ")")
        db.execSQL(query)
    }

    // Upgrading database
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BIKE")
        // Create tables again
        onCreate(db)
    }

    // Adding new bike
    //return id
    fun addBike(bike: Bike?): Long {
        LLog.d(TAG, "addBike " + LApplication.gson.toJson(bike))
        if (bike == null) {
            return RESULT_FAILED
        }
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, bike.name)
        values.put(KEY_BRANCH, bike.branch)
        values.put(KEY_HP, bike.hp)
        values.put(KEY_PRICE, bike.price)
        values.put(KEY_IMG_PATH_0, bike.imgPath0)
        values.put(KEY_IMG_PATH_1, bike.imgPath1)
        values.put(KEY_IMG_PATH_2, bike.imgPath2)
        val result = db.insert(TABLE_BIKE, null, values)
        LLog.d(TAG, "->addBike success result: $result")
        db.close()
        return result
    }

    fun getBike(id: Long): Bike? {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_BIKE,
                arrayOf(KEY_ID, KEY_NAME, KEY_BRANCH, KEY_HP, KEY_PRICE, KEY_IMG_PATH_0, KEY_IMG_PATH_1, KEY_IMG_PATH_2),
                "$KEY_ID=?",
                arrayOf(id.toString()), null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            if (cursor.count >= 1) {
                val bike = Bike()
                bike.id = cursor.getString(0).toLong()
                bike.name = cursor.getString(1)
                bike.branch = cursor.getString(2)
                bike.hp = cursor.getString(3).toInt()
                bike.price = cursor.getString(4).toInt()
                bike.imgPath0 = cursor.getString(5)
                bike.imgPath1 = cursor.getString(6)
                bike.imgPath2 = cursor.getString(7)
                cursor.close()
                return bike
            }
            cursor.close()
        }
        return null
    }

    // Updating single bike
    //return 1 if success
    //return -1 if input is null
    fun updateBike(bike: Bike?): Long {
        LLog.d(TAG, "updateBike " + LApplication.gson.toJson(bike))
        if (bike == null) {
            return RESULT_FAILED
        }
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, bike.name)
        values.put(KEY_BRANCH, bike.branch)
        values.put(KEY_HP, bike.hp)
        values.put(KEY_PRICE, bike.price)
        values.put(KEY_IMG_PATH_0, bike.imgPath0)
        values.put(KEY_IMG_PATH_1, bike.imgPath1)
        values.put(KEY_IMG_PATH_2, bike.imgPath2)
        return db.update(TABLE_BIKE, values, "$KEY_ID = ?", arrayOf(bike.id.toString())).toLong()
    }

    // Deleting single bike
    fun deleteBike(bike: Bike?): Long {
        if (bike == null) {
            return RESULT_FAILED
        }
        val db = this.writableDatabase
        val result = db.delete(TABLE_BIKE, "$KEY_ID = ?", arrayOf(bike.id.toString()))
        db.close()
        return result.toLong()
    }

    // Deleting single bike
    fun deleteBike(id: Long): Long {
        val db = this.writableDatabase
        val result = db.delete(TABLE_BIKE, "$KEY_ID = ?", arrayOf(id.toString()))
        db.close()
        return result.toLong()
    }

    fun clearAllBike() {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_BIKE")
        db.close()
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = BikeDatabase::class.java.simpleName
        private val TABLE_BIKE = Bike::class.java.simpleName

        // Contacts Table Columns names
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_BRANCH = "branch"
        private val KEY_HP = "hp"
        private val KEY_PRICE = "price"
        private val KEY_IMG_PATH_0 = "imgPath0"
        private val KEY_IMG_PATH_1 = "imgPath1"
        private val KEY_IMG_PATH_2 = "imgPath2"
        val RESULT_SUCCESS: Long = 1
        val RESULT_FAILED: Long = -1
    }
}