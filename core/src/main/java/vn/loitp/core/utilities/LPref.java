package vn.loitp.core.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import vn.loitp.core.common.Constants;
import vn.loitp.model.App;
import vn.loitp.utils.util.AppUtils;

/**
 * File created on 11/15/2016.
 *
 * @author loitp
 */
public class LPref {
    private String TAG = getClass().getSimpleName();

    private final static String PREFERENCES_FILE_NAME = "loitp";
    private final static String CHECK_APP_READY = "CHECK_APP_READY" + AppUtils.getAppVersionCode();
    private final static String PRE_LOAD = "PRE_LOAD";
    public static final String JSON_LIST_DATA = "JSON_LIST_DATA";
    public static final String JSON_FAV_DATA = "JSON_FAV_DATA";
    public static final String JSON_AD_DATA = "JSON_AD_DATA";
    public static final String FIRST_RUN_APP = "FIRST_RUN_APP";
    public static final String SAVED_NUMBER_VERSION = "saved.number.version";
    public static final String NOT_READY_USE_APPLICATION = "not.ready.use.application";
    public static final String TEXT_SIZE_EPUB = "TEXT_SIZE_EPUB";
    public static String JSON_BOOK_ASSET = "JSON_BOOK_ASSET";
    public static final String INDEX = "INDEX";
    public static final String PASS_CODE = "PASS_CODE";
    public static final String UZVIDEO_WIDTH = "UZVIDEO_WIDTH";
    public static final String UZVIDEO_HEIGHT = "UZVIDEO_HEIGHT";
    public static final String GG_APP_SETTING = "GG_APP_SETTING";
    public static final String GG_APP_MSG = "GG_APP_MSG";
    public static final String IS_SHOWED_DLG_WARNING_YOUTUBE_PARSER = "IS_SHOWED_DLG_WARNING_YOUTUBE_PARSER";

    //object
    public static App getGGAppSetting(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return new Gson().fromJson(pref.getString(GG_APP_SETTING, ""), App.class);
    }

    public static void setGGAppSetting(Context context, App user) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        editor.putString(GG_APP_SETTING, new Gson().toJson(user));
        editor.apply();
    }

    /////////////////////////////////STRING
    public static String getGGAppMsg(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return pref.getString(GG_APP_MSG, "");
    }

    public static void setGGAppMsg(Context context, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        editor.putString(GG_APP_MSG, value);
        editor.apply();
    }
    /////////////////////////////////BOOLEAN

    public static Boolean getCheckAppReady(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return prefs.getBoolean(CHECK_APP_READY, false);
    }

    public static void setCheckAppReady(Context context, Boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        editor.putBoolean(CHECK_APP_READY, value);
        editor.apply();
    }

    public static Boolean getPreLoad(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return prefs.getBoolean(PRE_LOAD, false);
    }

    public static void setPreLoad(Context context, Boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        editor.putBoolean(PRE_LOAD, value);
        editor.apply();
    }

    public static boolean getIsShowedDlgWarningYoutubeParser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return prefs.getBoolean(IS_SHOWED_DLG_WARNING_YOUTUBE_PARSER, false);
    }

    public static void setIsShowedDlgWarningYoutubeParser(Context context, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        editor.putBoolean(IS_SHOWED_DLG_WARNING_YOUTUBE_PARSER, value);
        editor.apply();
    }

    /////////////////////////////////INT
    public static int getIndex(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return prefs.getInt(INDEX, Constants.NOT_FOUND);
    }

    public static void setIndex(Context context, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        editor.putInt(INDEX, value);
        editor.apply();
    }

    public static int getTextSizeEpub(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return prefs.getInt(TEXT_SIZE_EPUB, 110);
    }

    public static void setTextSizeEpub(Context context, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        editor.putInt(TEXT_SIZE_EPUB, value);
        editor.apply();
    }

    public static int getUZvideoWidth(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return prefs.getInt(UZVIDEO_WIDTH, 16);
    }

    public static void setUZvideoWidth(Context context, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        editor.putInt(UZVIDEO_WIDTH, value);
        editor.apply();
    }

    public static int getUzvideoHeight(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return prefs.getInt(UZVIDEO_HEIGHT, 9);
    }

    public static void setUzvideoHeight(Context context, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        editor.putInt(UZVIDEO_HEIGHT, value);
        editor.apply();
    }

    /////////////////////////////////STRING
    public static String getJsonBookAsset(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return pref.getString(JSON_BOOK_ASSET, null);
    }

    public static void setJsonBookAsset(Context context, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        editor.putString(JSON_BOOK_ASSET, value);
        editor.apply();
    }

    public static void savePassCode(Context context, String str) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(PASS_CODE, str);
        editor.commit();
    }

    public static String getPassCode(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        String defaultValue = "";
        return sharedPref.getString(PASS_CODE, defaultValue);
    }
}