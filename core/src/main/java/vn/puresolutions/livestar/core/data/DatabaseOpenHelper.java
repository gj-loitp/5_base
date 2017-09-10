package vn.puresolutions.livestar.core.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import vn.puresolutions.livestar.core.api.model.CoinPackage;
import vn.puresolutions.livestar.core.api.model.CoinTransaction;

/**
 * Created by Phu Tran on 3/18/2016.
 */
public class DatabaseOpenHelper extends OrmLiteSqliteOpenHelper {

    private final String TAG = getClass().getSimpleName();
    private static final String DB_NAME = "LiveStar.db";
    private static final int DB_VERSION = 2;

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, CoinTransaction.class);
        } catch (SQLException e) {
            Log.e(TAG, "Can't create database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, CoinTransaction.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "Can't drop database", e);
        }
    }
}