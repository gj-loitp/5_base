package vn.loitp.up.a.db.sqliteEncryption

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.loitp.core.ext.decrypt
import com.loitp.core.ext.encodeBase64
import com.loitp.core.ext.encrypt
import com.loitp.core.utils.DeviceUtils

/**
 * Created by Loitp on 15.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

class BikeDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private val DATABASE_NAME = BikeDatabase::class.java.simpleName
        private val TABLE_BIKE = Bike::class.java.simpleName

        // Contacts Table Columns names
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_BRANCH = "branch"
        private const val KEY_HP = "hp"
        private const val KEY_PRICE = "price"
        private const val KEY_IMG_PATH_0 = "imgPath0"
        private const val KEY_IMG_PATH_1 = "imgPath1"
        private const val KEY_IMG_PATH_2 = "imgPath2"

        @Suppress("unused")
        const val RESULT_SUCCESS: Long = 1
        const val RESULT_FAILED: Long = -1
    }

    private val logTag = javaClass.simpleName
    private val pw =
        (logTag + DeviceUtils.androidID + DeviceUtils.macAddress).encodeBase64() + "1993"

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

                    val id = cursor.getString(0).toLong()
                    val decryptName = cursor.getString(1).decrypt(pw)
                    val decryptBranch = cursor.getString(2).decrypt(pw)
                    val decryptHp = cursor.getString(3).decrypt(pw)
                    val decryptPrice = cursor.getString(4).decrypt(pw)
                    val decryptImgPath0 = cursor.getString(5).decrypt(pw)
                    val decryptImgPath1 = cursor.getString(6).decrypt(pw)
                    val decryptImgPath2 = cursor.getString(7).decrypt(pw)

                    bike.id = id
                    bike.name = decryptName
                    bike.branch = decryptBranch
                    bike.hp = decryptHp?.toInt()
                    bike.price = decryptPrice?.toInt()
                    bike.imgPath0 = decryptImgPath0
                    bike.imgPath1 = decryptImgPath1
                    bike.imgPath2 = decryptImgPath2
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
        val query =
            ("CREATE TABLE " + TABLE_BIKE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT NOT NULL, " + KEY_BRANCH + " TEXT NOT NULL, " + KEY_HP + " TEXT NOT NULL, " + KEY_PRICE + " TEXT NOT NULL, " + KEY_IMG_PATH_0 + " TEXT NOT NULL, " + KEY_IMG_PATH_1 + " TEXT NOT NULL, " + KEY_IMG_PATH_2 + " TEXT NOT NULL " + ")")
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
    // return id
    fun addBike(bike: Bike?): Long {
        if (bike == null) {
            return RESULT_FAILED
        }
        val db = this.writableDatabase
        val values = ContentValues()

        val encryptName = bike.name.encrypt(pw)
        val encryptBranch = bike.branch.encrypt(pw)
        val encryptHp = bike.hp.toString().encrypt(pw)
        val encryptPrice = bike.price.toString().encrypt(pw)
        val encryptImgPath0 = bike.imgPath0.encrypt(pw)
        val encryptImgPath1 = bike.imgPath1.encrypt(pw)
        val encryptImgPath2 = bike.imgPath2.encrypt(pw)

        values.put(KEY_NAME, encryptName)
        values.put(KEY_BRANCH, encryptBranch)
        values.put(KEY_HP, encryptHp)
        values.put(KEY_PRICE, encryptPrice)
        values.put(KEY_IMG_PATH_0, encryptImgPath0)
        values.put(KEY_IMG_PATH_1, encryptImgPath1)
        values.put(KEY_IMG_PATH_2, encryptImgPath2)
        val result = db.insert(TABLE_BIKE, null, values)
        db.close()
        return result
    }

    fun getBike(idBike: Long?): Bike? {
        if (idBike == null) {
            return null
        }
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_BIKE, arrayOf(
                KEY_ID,
                KEY_NAME,
                KEY_BRANCH,
                KEY_HP,
                KEY_PRICE,
                KEY_IMG_PATH_0,
                KEY_IMG_PATH_1,
                KEY_IMG_PATH_2
            ), "$KEY_ID=?", arrayOf(idBike.toString()), null, null, null, null
        )
        if (cursor != null) {
            cursor.moveToFirst()
            if (cursor.count >= 1) {
                val bike = Bike()
                val id = cursor.getString(0).toLong()
                val decryptName = cursor.getString(1).decrypt(pw)
                val decryptBranch = cursor.getString(2).decrypt(pw)
                val decryptHp = cursor.getString(3).decrypt(pw)
                val decryptPrice = cursor.getString(4).decrypt(pw)
                val decryptImgPath0 = cursor.getString(5).decrypt(pw)
                val decryptImgPath1 = cursor.getString(6).decrypt(pw)
                val decryptImgPath2 = cursor.getString(7).decrypt(pw)

                bike.id = id
                bike.name = decryptName
                bike.branch = decryptBranch
                bike.hp = decryptHp?.toInt()
                bike.price = decryptPrice?.toInt()
                bike.imgPath0 = decryptImgPath0
                bike.imgPath1 = decryptImgPath1
                bike.imgPath2 = decryptImgPath2
                cursor.close()
                return bike
            }
            cursor.close()
        }
        return null
    }

    // Updating single bike
    // return 1 if success
    // return -1 if input is null
    fun updateBike(bike: Bike?): Long {
        if (bike == null) {
            return RESULT_FAILED
        }
        val db = this.writableDatabase
        val values = ContentValues()

        val encryptName = bike.name.encrypt(pw)
        val encryptBranch = bike.branch.encrypt(pw)
        val encryptHp = bike.hp.toString().encrypt(pw)
        val encryptPrice = bike.price.toString().encrypt(pw)
        val encryptImgPath0 = bike.imgPath0.encrypt(pw)
        val encryptImgPath1 = bike.imgPath1.encrypt(pw)
        val encryptImgPath2 = bike.imgPath2.encrypt(pw)

        values.put(KEY_NAME, encryptName)
        values.put(KEY_BRANCH, encryptBranch)
        values.put(KEY_HP, encryptHp)
        values.put(KEY_PRICE, encryptPrice)
        values.put(KEY_IMG_PATH_0, encryptImgPath0)
        values.put(KEY_IMG_PATH_1, encryptImgPath1)
        values.put(KEY_IMG_PATH_2, encryptImgPath2)

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
    @Suppress("unused")
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
}
