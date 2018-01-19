package vn.loitp.app.activity.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NUMBER = "phone_number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PHONE_NUMBER + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE_NUMBER, contact.getPhoneNumber());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS,
                new String[]{KEY_ID, KEY_NAME, KEY_PHONE_NUMBER}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() >= 1) {
                return new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
            }
        }
        return null;
    }

    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE_NUMBER, contact.getPhoneNumber());

        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?", new String[]{String.valueOf(contact.getID())});
    }

    // Deleting single contact
    public int deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[]{String.valueOf(contact.getID())});
        db.close();
        return result;
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = 0;
        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public void clearAllContact() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CONTACTS);
        db.close();
    }
}