package vn.puresolutions.livestarv3.utilities.v3;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import vn.puresolutions.livestar.corev3.api.model.v3.listgift.ListGift;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestar.corev3.api.model.v3.login.param.LoginPhoneParam;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolcategory.ModelIdolCategory;
import vn.puresolutions.livestarv3.app.LSApplication;

/**
 * File created on 11/15/2016.
 *
 * @author loitp
 */
public class LPref {
    private final String TAG = getClass().getSimpleName();

    private final static String PREFERENCES_FILE_NAME = "livestar";
    private final static String PREFERENCES_FILE_NAME_LG = "livestar_lg";
    private final static String USERPROFILE = "USER_PROFILE";
    private final static String TOKEN = "token";
    private final static String LIST_GIFT = "LIST_GIFT";
    private final static String LOGIN_INFO = "LOGIN_INFO";
    private final static String SWITCH_LIVESTREAMING = "SWITCH_LIVESTREAMING";
    private final static String SWITCH_GONNA_LIVESTREAMING = "SWITCH_GONNA_LIVESTREAMING";
    private final static String SWITCH_OTHER_ACTION = "SWITCH_OTHER_ACTION";
    private final static String PREF_CAMERA_ID = "pref_camera_id";
    private final static String PREF_STATE_FILTER = "state_filter";
    private final static String MODEL_IDOL_CATEGOTY = "MODEL_IDOL_CATEGOTY";
    private final static String JSON_LIST_BOUGHT_SESSION_ID = "JSON_LIST_BOUGHT_SESSION_ID";

    public static void removeAll(Context context) {
        context.getApplicationContext().getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit().clear().apply();
    }

    //object
    public static UserProfile getUser(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return LSApplication.getInstance().getGson().fromJson(pref.getString(USERPROFILE, ""), UserProfile.class);
    }

    public static void setUser(Context context, UserProfile user) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        editor.putString(USERPROFILE, LSApplication.getInstance().getGson().toJson(user));
        editor.apply();
    }

    public static ModelIdolCategory getModelIdolCatgory(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return LSApplication.getInstance().getGson().fromJson(pref.getString(MODEL_IDOL_CATEGOTY, ""), ModelIdolCategory.class);
    }

    public static void setModelIdolCatgory(Context context, ModelIdolCategory modelIdolCategory) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        editor.putString(MODEL_IDOL_CATEGOTY, LSApplication.getInstance().getGson().toJson(modelIdolCategory));
        editor.apply();
    }

    public static void setToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        RestClient.addAuthorization(token);
        editor.putString(TOKEN, token);
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return pref.getString(TOKEN, null);
    }

    public static ListGift getListGift(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return LSApplication.getInstance().getGson().fromJson(pref.getString(LIST_GIFT, ""), ListGift.class);
    }

    public static void setListGiftr(Context context, ListGift listGift) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        editor.putString(LIST_GIFT, LSApplication.getInstance().getGson().toJson(listGift));
        editor.apply();
    }

    public static LoginPhoneParam getLoginInfo(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_FILE_NAME_LG, 0);
        return LSApplication.getInstance().getGson().fromJson(pref.getString(LOGIN_INFO, ""), LoginPhoneParam.class);
    }

    public static void setLoginInfo(Context context, LoginPhoneParam param) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME_LG, 0).edit();
        editor.putString(LOGIN_INFO, LSApplication.getInstance().getGson().toJson(param));
        editor.apply();
    }

    /////////////////////////////////STRING
    public static List<String> getJsonListBoughtSessionId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        String json = pref.getString(JSON_LIST_BOUGHT_SESSION_ID, "[]");//importan value
        return LSApplication.getInstance().getGson().fromJson(json, new TypeToken<List<String>>() {
        }.getType());
    }

    public static void setJsonListBoughtSessionId(Context context, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        editor.putString(JSON_LIST_BOUGHT_SESSION_ID, value);
        editor.apply();
    }

    public static void addBoughtSessionIdToList(Context context, String sessionId) {
        List<String> sessionList = getJsonListBoughtSessionId(context);
        if (!sessionList.contains(sessionId)) {
            sessionList.add(sessionId);
            setJsonListBoughtSessionId(context, LSApplication.getInstance().getGson().toJson(sessionList));
        }
    }

    public static boolean isBoughtSessionId(Context context, String sessionId) {
        List<String> sessionList = getJsonListBoughtSessionId(context);
        for (String s : sessionList) {
            if (s.equals(sessionId)) {
                return true;
            }
        }
        return false;
    }

    /////////////////////////////////BOOLEAN
    public static boolean isUserLoggedIn(Context context) {
        UserProfile user = getUser(context);
        return user != null;
    }

    public static boolean isOnSwitchLivestreaming(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return prefs.getBoolean(SWITCH_LIVESTREAMING, false);
    }

    public static void setSwitchLivestreaming(Context context, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        editor.putBoolean(SWITCH_LIVESTREAMING, value);
        editor.apply();
    }

    public static boolean isOnSwitchGonnaLivestreaming(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return prefs.getBoolean(SWITCH_GONNA_LIVESTREAMING, false);
    }

    public static void setSwitchGonnaLivestreaming(Context context, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        editor.putBoolean(SWITCH_GONNA_LIVESTREAMING, value);
        editor.apply();
    }

    public static boolean isOnSwitchOtherAction(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return prefs.getBoolean(SWITCH_OTHER_ACTION, false);
    }

    public static void setSwitchOtherAction(Context context, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit();
        editor.putBoolean(SWITCH_OTHER_ACTION, value);
        editor.apply();
    }

    /////////////////////////////////INT
    /*public static int getViewBy(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Const.PREFERENCES_FILE_NAME, 0);
        return prefs.getInt(Const.ATTR_INDEX_VIEWBY, 0);
    }

    public static void setViewBy(Context context, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Const.PREFERENCES_FILE_NAME, 0).edit();
        editor.putInt(Const.ATTR_INDEX_VIEWBY, value);
        editor.apply();
    }*/

    public static String getStoredCameraId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_FILE_NAME_LG, 0);
        return pref.getString(PREF_CAMERA_ID, "");
    }

    public static void storeCameraId(Context context, String id) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_FILE_NAME_LG, 0);
        pref.edit().putString(PREF_CAMERA_ID, id).apply();
    }

    public static boolean getStoredFilterState(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_FILE_NAME_LG, 0);
        return pref.getBoolean(PREF_STATE_FILTER, false);
    }

    public static void storeFilterState(Context context, boolean on) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_FILE_NAME_LG, 0);
        pref.edit().putBoolean(PREF_STATE_FILTER, on).apply();
    }
}