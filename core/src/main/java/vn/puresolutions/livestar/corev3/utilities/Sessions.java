package vn.puresolutions.livestar.corev3.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Edited Bien Ly
 * @version 1.0.0
 * @since Mar 11, 2014
 */
public class Sessions {
	private static Sessions instance;
	private SharedPreferences pref;

	private Sessions(Context context) {
		pref = context.getSharedPreferences("LiveStar", Context.MODE_PRIVATE);
	}

	public static Sessions getInstance(Context context) {
		if (instance == null) {
			instance = new Sessions(context);
		}
		return instance;
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