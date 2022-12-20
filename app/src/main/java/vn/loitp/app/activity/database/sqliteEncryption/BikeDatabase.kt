package vn.loitp.app.activity.database.sqliteEncryption

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.loitp.core.utilities.LEncryptionUtil
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
        LEncryptionUtil.encodeBase64(logTag + DeviceUtils.androidID + DeviceUtils.macAddress) + "1993"

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
                    val decryptName = LEncryptionUtil.decrypt(cursor.getString(1), pw)
                    val decryptBranch = LEncryptionUtil.decrypt(cursor.getString(2), pw)
                    val decryptHp = LEncryptionUtil.decrypt(cursor.getString(3), pw)
                    val decryptPrice = LEncryptionUtil.decrypt(cursor.getString(4), pw)
                    val decryptImgPath0 = LEncryptionUtil.decrypt(cursor.getString(5), pw)
                    val decryptImgPath1 = LEncryptionUtil.decrypt(cursor.getString(6), pw)
                    val decryptImgPath2 = LEncryptionUtil.decrypt(cursor.getString(7), pw)

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
        val query = (
                "CREATE TABLE " + TABLE_BIKE + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY," +
                        KEY_NAME + " TEXT NOT NULL, " +
                        KEY_BRANCH + " TEXT NOT NULL, " +
                        KEY_HP + " TEXT NOT NULL, " +
                        KEY_PRICE + " TEXT NOT NULL, " +
                        KEY_IMG_PATH_0 + " TEXT NOT NULL, " +
                        KEY_IMG_PATH_1 + " TEXT NOT NULL, " +
                        KEY_IMG_PATH_2 + " TEXT NOT NULL " +
                        ")"
                )
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

        val encryptName = LEncryptionUtil.encrypt(bike.name, pw)
        val encryptBranch = LEncryptionUtil.encrypt(bike.branch, pw)
        val encryptHp = LEncryptionUtil.encrypt(bike.hp.toString(), pw)
        val encryptPrice = LEncryptionUtil.encrypt(bike.price.toString(), pw)
        val encryptImgPath0 = LEncryptionUtil.encrypt(bike.imgPath0, pw)
        val encryptImgPath1 = LEncryptionUtil.encrypt(bike.imgPath1, pw)
        val encryptImgPath2 = LEncryptionUtil.encrypt(bike.imgPath2, pw)

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
            TABLE_BIKE,
            arrayOf(
                KEY_ID,
                KEY_NAME,
                KEY_BRANCH,
                KEY_HP,
                KEY_PRICE,
                KEY_IMG_PATH_0,
                KEY_IMG_PATH_1,
                KEY_IMG_PATH_2
            ),
            "$KEY_ID=?",
            arrayOf(idBike.toString()), null, null, null, null
        )
        if (cursor != null) {
            cursor.moveToFirst()
            if (cursor.count >= 1) {
                val bike = Bike()
                val id = cursor.getString(0).toLong()
                val decryptName = LEncryptionUtil.decrypt(cursor.getString(1), pw)
                val decryptBranch = LEncryptionUtil.decrypt(cursor.getString(2), pw)
                val decryptHp = LEncryptionUtil.decrypt(cursor.getString(3), pw)
                val decryptPrice = LEncryptionUtil.decrypt(cursor.getString(4), pw)
                val decryptImgPath0 = LEncryptionUtil.decrypt(cursor.getString(5), pw)
                val decryptImgPath1 = LEncryptionUtil.decrypt(cursor.getString(6), pw)
                val decryptImgPath2 = LEncryptionUtil.decrypt(cursor.getString(7), pw)

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

        val encryptName = LEncryptionUtil.encrypt(bike.name, pw)
        val encryptBranch = LEncryptionUtil.encrypt(bike.branch, pw)
        val encryptHp = LEncryptionUtil.encrypt(bike.hp.toString(), pw)
        val encryptPrice = LEncryptionUtil.encrypt(bike.price.toString(), pw)
        val encryptImgPath0 = LEncryptionUtil.encrypt(bike.imgPath0, pw)
        val encryptImgPath1 = LEncryptionUtil.encrypt(bike.imgPath1, pw)
        val encryptImgPath2 = LEncryptionUtil.encrypt(bike.imgPath2, pw)

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
