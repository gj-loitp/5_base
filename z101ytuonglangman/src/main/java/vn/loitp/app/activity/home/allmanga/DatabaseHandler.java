package vn.loitp.app.activity.home.allmanga;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.common.Constants;
import vn.loitp.app.model.comic.Comic;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "comicDatabase";

    // Contacts table name
    private static final String TABLE_COMIC_ALL = "comicall";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_URL = "url";
    private static final String KEY_TV_DATE = "tvDate";
    private static final String KEY_URLIMG = "urlImg";
    private static final String KEY_TYPE = "type";
    private static final String KEY_IS_FAV = "isfav";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_COMIC_ALL
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_URL + " TEXT,"
                + KEY_TV_DATE + " TEXT,"
                + KEY_URLIMG + " TEXT,"
                + KEY_TYPE + " TEXT,"
                + KEY_IS_FAV + " INTEGER"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMIC_ALL);

        // Create tables again
        onCreate(db);
    }

    public long addComic(Comic comic) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, comic.getTitle());
        values.put(KEY_URL, comic.getUrl());
        values.put(KEY_TV_DATE, comic.getDate());
        values.put(KEY_URLIMG, comic.getUrlImg());
        values.put(KEY_TYPE, comic.getType());
        values.put(KEY_IS_FAV, comic.isFav());

        long result = db.insert(TABLE_COMIC_ALL, null, values);
        db.close();
        return result;
    }

    public Comic getComic(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_COMIC_ALL,
                new String[]{KEY_ID, KEY_TITLE, KEY_URL, KEY_TV_DATE, KEY_URLIMG, KEY_TYPE, KEY_IS_FAV}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() >= 1) {
                Comic comic = new Comic();
                comic.setId(Integer.parseInt(cursor.getString(0)));
                comic.setTitle(cursor.getString(1));
                comic.setUrl(cursor.getString(2));
                comic.setDate(cursor.getString(3));
                comic.setUrlImg(cursor.getString(4));
                comic.setType(cursor.getString(5));
                comic.setFav(Integer.parseInt(cursor.getString(6)));
                return comic;
            }
        }
        return null;
    }

    public List<Comic> getAllComic() {
        List<Comic> contactList = new ArrayList<Comic>();
        String selectQuery = "SELECT  * FROM " + TABLE_COMIC_ALL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Comic comic = new Comic();
                comic.setId(Integer.parseInt(cursor.getString(0)));
                comic.setTitle(cursor.getString(1));
                comic.setUrl(cursor.getString(2));
                comic.setDate(cursor.getString(3));
                comic.setUrlImg(cursor.getString(4));
                comic.setType(cursor.getString(5));
                comic.setFav(Integer.parseInt(cursor.getString(6)));
                contactList.add(comic);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Comic> getAllComicFav() {
        List<Comic> contactList = new ArrayList<Comic>();
        String selectQuery = "SELECT  * FROM " + TABLE_COMIC_ALL + " WHERE " + KEY_IS_FAV + "=" + Constants.IS_FAV;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Comic comic = new Comic();
                comic.setId(Integer.parseInt(cursor.getString(0)));
                comic.setTitle(cursor.getString(1));
                comic.setUrl(cursor.getString(2));
                comic.setDate(cursor.getString(3));
                comic.setUrlImg(cursor.getString(4));
                comic.setType(cursor.getString(5));
                comic.setFav(Integer.parseInt(cursor.getString(6)));
                contactList.add(comic);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public int updateComic(Comic comic) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, comic.getTitle());
        values.put(KEY_URL, comic.getUrl());
        values.put(KEY_TV_DATE, comic.getDate());
        values.put(KEY_URLIMG, comic.getUrlImg());
        values.put(KEY_TYPE, comic.getType());
        values.put(KEY_IS_FAV, comic.isFav());

        return db.update(TABLE_COMIC_ALL, values, KEY_ID + " = ?", new String[]{String.valueOf(comic.getId())});
    }

    public int deleteComic(Comic contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_COMIC_ALL, KEY_ID + " = ?", new String[]{String.valueOf(contact.getId())});
        db.close();
        return result;
    }

    public int getComicCount() {
        String countQuery = "SELECT  * FROM " + TABLE_COMIC_ALL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = 0;
        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public void clearAllComic() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_COMIC_ALL);
        db.close();
    }
}