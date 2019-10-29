package vn.loitp.app.activity.database.sqliteencryption;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.core.utilities.LLog;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.app.LApplication;

public class BikeDatabase extends SQLiteOpenHelper {
    private final String TAG = getClass().getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = BikeDatabase.class.getSimpleName();
    private static final String TABLE_BIKE = Bike.class.getSimpleName();

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_BRANCH = "branch";
    private static final String KEY_HP = "hp";
    private static final String KEY_PRICE = "price";
    private static final String KEY_IMG_PATH_0 = "imgPath0";
    private static final String KEY_IMG_PATH_1 = "imgPath1";
    private static final String KEY_IMG_PATH_2 = "imgPath2";

    public BikeDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_BIKE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT NOT NULL, "
                + KEY_BRANCH + " TEXT NOT NULL, "
                + KEY_HP + " INTEGER, "
                + KEY_PRICE + " INTEGER, "
                + KEY_IMG_PATH_0 + " TEXT NOT NULL, "
                + KEY_IMG_PATH_1 + " TEXT NOT NULL, "
                + KEY_IMG_PATH_2 + " TEXT NOT NULL "
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIKE);
        // Create tables again
        onCreate(db);
    }

    // Adding new bike
    public void addBike(Bike bike) {
        LLog.d(TAG, "addBike " + LApplication.Companion.getGson().toJson(bike));
        if (bike == null) {
            return;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, bike.getName());
        values.put(KEY_BRANCH, bike.getBranch());
        values.put(KEY_HP, bike.getHp());
        values.put(KEY_PRICE, bike.getPrice());
        values.put(KEY_IMG_PATH_0, bike.getImgPath0());
        values.put(KEY_IMG_PATH_1, bike.getImgPath1());
        values.put(KEY_IMG_PATH_2, bike.getImgPath2());
        db.insert(TABLE_BIKE, null, values);
        LLog.d(TAG, "->addBike success");
        db.close();
    }

    public Bike getBike(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BIKE,
                new String[]{KEY_ID, KEY_NAME, KEY_BRANCH, KEY_HP, KEY_PRICE, KEY_IMG_PATH_0, KEY_IMG_PATH_1, KEY_IMG_PATH_2},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() >= 1) {
                Bike bike = new Bike();
                bike.setId(Integer.parseInt(cursor.getString(0)));
                bike.setName(cursor.getString(1));
                bike.setBranch(cursor.getString(2));
                bike.setHp(Integer.parseInt(cursor.getString(3)));
                bike.setPrice(Integer.parseInt(cursor.getString(4)));
                bike.setImgPath0(cursor.getString(5));
                bike.setImgPath1(cursor.getString(6));
                bike.setImgPath2(cursor.getString(7));
                cursor.close();
                return bike;
            }
            cursor.close();
        }
        return null;
    }

    // Getting All Bike
    public List<Bike> getAllBike() {
        List<Bike> bikeList = new ArrayList<Bike>();
        String selectQuery = "SELECT  * FROM " + TABLE_BIKE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Bike bike = new Bike();
                bike.setId(Integer.parseInt(cursor.getString(0)));
                bike.setName(cursor.getString(1));
                bike.setBranch(cursor.getString(2));
                bike.setHp(Integer.parseInt(cursor.getString(3)));
                bike.setPrice(Integer.parseInt(cursor.getString(4)));
                bike.setImgPath0(cursor.getString(5));
                bike.setImgPath1(cursor.getString(6));
                bike.setImgPath2(cursor.getString(7));
                bikeList.add(bike);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bikeList;
    }

    // Updating single bike
    public int updateBike(Bike bike) {
        if (bike == null) {
            return -1;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, bike.getName());
        values.put(KEY_BRANCH, bike.getBranch());
        values.put(KEY_HP, bike.getHp());
        values.put(KEY_PRICE, bike.getPrice());
        values.put(KEY_IMG_PATH_0, bike.getImgPath0());
        values.put(KEY_IMG_PATH_1, bike.getImgPath1());
        values.put(KEY_IMG_PATH_2, bike.getImgPath2());
        return db.update(TABLE_BIKE, values, KEY_ID + " = ?", new String[]{String.valueOf(bike.getId())});
    }

    // Deleting single bike
    public int deleteBike(Bike bike) {
        if (bike == null) {
            return -1;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_BIKE, KEY_ID + " = ?", new String[]{String.valueOf(bike.getId())});
        db.close();
        return result;
    }

    // Getting bike Count
    public int getBikeCount() {
        String countQuery = "SELECT  * FROM " + TABLE_BIKE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = 0;
        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public void clearAllBike() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_BIKE);
        db.close();
    }
}