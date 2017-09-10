package vn.puresolutions.livestarv3.utilities.old;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by khanh on 3/23/16.
 */
public class SharedPreferenceUtils {

    private SharedPreferences pref;

    public SharedPreferenceUtils(Context context, String name) {
        pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void clear() {
        if (pref != null) {
            pref.edit().clear().commit();
        }
    }

    public void put(String key, int value) {
        pref.edit().putInt(key, value).commit();
    }

    public void put(String key, String value) {
        pref.edit().putString(key, value).commit();
    }

    public void put(String key, float value) {
        pref.edit().putFloat(key, value).commit();
    }

    public void put(String key, long value) {
        pref.edit().putLong(key, value).commit();
    }

    public void put(String key, boolean value) {
        pref.edit().putBoolean(key, value).commit();
    }

    public boolean get(String key, boolean defValue) {
        return pref.getBoolean(key, defValue);
    }

    public int get(String key, int defValue) {
        return pref.getInt(key, defValue);
    }

    public float get(String key, float defValue) {
        return pref.getFloat(key, defValue);
    }

    public String get(String key, String defValue) {
        return pref.getString(key, defValue);
    }

    public long get(String key, long defValue) {
        return pref.getLong(key, defValue);
    }

    public SharedPreferences getPref() {
        return pref;
    }

    public void setPref(SharedPreferences pref) {
        this.pref = pref;
    }
}
