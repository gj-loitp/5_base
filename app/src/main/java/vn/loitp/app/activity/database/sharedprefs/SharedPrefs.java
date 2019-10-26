package vn.loitp.app.activity.database.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.core.utilities.LLog;
import com.utils.util.AppUtils;
import com.utils.util.Utils;

public class SharedPrefs {
    private String TAG = "TAG" + getClass().getSimpleName();
    private static final String PREFS_NAME = AppUtils.getAppPackageName();
    private static SharedPrefs mInstance;
    private SharedPreferences mSharedPreferences;

    private SharedPrefs() {
        mSharedPreferences = Utils.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefs getInstance() {
        if (mInstance == null) {
            mInstance = new SharedPrefs();
        }
        return mInstance;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> anonymousClass) {
        if (anonymousClass == String.class) {
            LLog.d(TAG, "getString " + key);
            return (T) mSharedPreferences.getString(key, "");
        } else if (anonymousClass == Boolean.class) {
            LLog.d(TAG, "getBoolean " + key);
            return (T) Boolean.valueOf(mSharedPreferences.getBoolean(key, false));
        } else if (anonymousClass == Float.class) {
            LLog.d(TAG, "getFloat " + key);
            return (T) Float.valueOf(mSharedPreferences.getFloat(key, 0));
        } else if (anonymousClass == Integer.class) {
            LLog.d(TAG, "getInteger " + key);
            return (T) Integer.valueOf(mSharedPreferences.getInt(key, 0));
        } else if (anonymousClass == Long.class) {
            LLog.d(TAG, "getLong " + key);
            return (T) Long.valueOf(mSharedPreferences.getLong(key, 0));
        } else {
            LLog.d(TAG, "get -> do nothing: " + anonymousClass.getSimpleName());
        }
        return null;
    }

    public <T> void put(String key, T data) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (data instanceof String) {
            editor.putString(key, (String) data);
            LLog.d(TAG, "putString " + key + " -> " + data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key, (Boolean) data);
            LLog.d(TAG, "putBoolean " + key + " -> " + data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
            LLog.d(TAG, "putFloat " + key + " -> " + data);
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
            LLog.d(TAG, "putInt " + key + " -> " + data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
            LLog.d(TAG, "putLong " + key + " -> " + data);
        } else {
            LLog.d(TAG, "put -> do nothing");
        }
        editor.apply();
    }

    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }
}
