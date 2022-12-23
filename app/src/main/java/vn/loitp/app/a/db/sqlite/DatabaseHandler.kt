package vn.loitp.app.a.db.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by Loitp on 15.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

class DatabaseHandler(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    companion object {
        // Database Version
        private const val DATABASE_VERSION = 1

        // Database Name
        private const val DATABASE_NAME = "contactsManager"

        // Contacts table name
        private const val TABLE_CONTACTS = "contacts"

        // Contacts Table Columns names
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_PHONE_NUMBER = "phone_number"
    }

//    private val logTag = javaClass.simpleName

    // Creating Tables
    override fun onCreate(db: SQLiteDatabase) {
        val sql = (
                "CREATE TABLE " + TABLE_CONTACTS + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," +
                        KEY_PHONE_NUMBER + " TEXT" + ")"
                )
        db.execSQL(sql)
    }

    // Upgrading database
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")

        // Create tables again
        onCreate(db)
    }

    // Adding new contact
    fun addContact(contact: Contact) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, contact.name)
        values.put(KEY_PHONE_NUMBER, contact.phoneNumber)
        db.insert(TABLE_CONTACTS, null, values)
        db.close()
    }

    fun getContact(id: Int): Contact? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_CONTACTS,
            arrayOf(KEY_ID, KEY_NAME, KEY_PHONE_NUMBER),
            "$KEY_ID=?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )
        if (cursor != null) {
            cursor.moveToFirst()
            if (cursor.count >= 1) {
                return Contact(
                    cursor.getString(0).toInt(),
                    cursor.getString(1),
                    cursor.getString(2)
                )
            }
        }
        cursor?.close()
        return null
    }

    // Getting All Contacts
    val allContacts: List<Contact>
        get() {
            val contactList: MutableList<Contact> = ArrayList()
            val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val contact = Contact()
                    contact.id = cursor.getString(0).toInt()
                    contact.name = cursor.getString(1)
                    contact.phoneNumber = cursor.getString(2)
                    contactList.add(contact)
                } while (cursor.moveToNext())
            }
            cursor.close()
            return contactList
        }

    fun getContactListWithPage(page: Int, pageSize: Int): List<Contact> {
        require(pageSize >= 0) { "pageSize <0 is invalid" }
        val contactList: MutableList<Contact> = ArrayList()
        if (page < 0) {
            return contactList
        }
        val rowCount = contactsCount
        val indexStart = pageSize * page
        var indexEnd = indexStart + pageSize
        if (indexEnd >= rowCount) {
            indexEnd = rowCount
        }
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        val tmpContactList: MutableList<Contact> = ArrayList()
        if (cursor.moveToFirst()) {
            do {
                val contact = Contact()
                contact.id = cursor.getString(0).toInt()
                contact.name = cursor.getString(1)
                contact.phoneNumber = cursor.getString(2)
                tmpContactList.add(contact)
            } while (cursor.moveToNext())
        }
        cursor.close()
        for (i in indexStart until indexEnd) {
            val contact = tmpContactList[i]
            contactList.add(contact)
        }
        return contactList
    }

    // Updating single contact
    fun updateContact(contact: Contact): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, contact.name)
        values.put(KEY_PHONE_NUMBER, contact.phoneNumber)
        return db.update(TABLE_CONTACTS, values, "$KEY_ID = ?", arrayOf(contact.id.toString()))
    }

    // Deleting single contact
    fun deleteContact(contact: Contact): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_CONTACTS, "$KEY_ID = ?", arrayOf(contact.id.toString()))
        db.close()
        return result
    }

    // Getting contacts Count
    val contactsCount: Int
        get() {
            val countQuery = "SELECT  * FROM $TABLE_CONTACTS"
            val db = this.readableDatabase
            val cursor = db.rawQuery(countQuery, null)
            var count = 0
            if (cursor != null && !cursor.isClosed) {
                count = cursor.count
                cursor.close()
            }
            return count
        }

    fun clearAllContact() {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_CONTACTS")
        db.close()
    }
}
