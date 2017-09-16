package vn.loitp.app.common;

/**
 * Created by loitp
 */
public class Constants {
    public final static boolean IS_DEBUG = true;

    // On air item scale ratio
    public static final float ON_AIR_ITEM_SCALE_WIDTH_RATIO = 0.75f;

    //eventbus message
    public static String MSG = "msg";

    public static final int TYPE_IDOL_POSTER = 0;
    public static final int TYPE_IDOL_FOLLOW_OR_SUGGEST = 1;
    public static final int TYPE_IDOL_CATEGORY = 2;
    public static final int TYPE_IDOL_ROOM_BY_CATEGORY = 3;
    public static final int TYPE_IDOL_LAST = 4;

    public static final int TICKET_PRIVATE_ROOM_PRICE = 10;

    public static final int TYPE_1ST = 0;
    public static final int TYPE_2ND_3RD = 1;
    public static final int TYPE_OTHER = 2;

    public static final int NOT_FOUND = -6969;

    public static final String KEY_LIVESTREAM_ASK_SAVE_VIDEO = "KEY_LIVESTREAM_ASK_SAVE_VIDEO";

    public static final String MODE_PUBLIC = "1";
    public static final String MODE_RESTRICT = "2";
    public static final String MODE_PRIVATE = "3";

    //common screenkey
    public static final String KEY_HOME_TO_ROOM = "KEY_HOME_TO_ROOM";
    public static final String KEY_HOME_TO_ROOM_POSITION = "KEY_HOME_TO_ROOM_POSITION";
    public static final String KEY_HOME_TO_ROOM_LIST_ROOM = "KEY_HOME_TO_ROOM_LIST_ROOM";
    public static final String KEY_HOME_TO_PRIVATE_ROOM = "KEY_HOME_TO_PRIVATE_ROOM";
    public static final String KEY_USER_TO_PROFILE = "KEY_USER_TO_PROFILE";
    public static final String FORGOT_TO_VERIFY = "FORGOT_TO_VERIFY";
    public static final String OTP_VERIFY = "OTP_VERIFY";
    public static final String REGISTER_TO_VERIFY = "REGISTER_TO_VERIFY";

    public static final String KEY_ROOM = "KEY_ROOM";
    public static final String REGISTER_DATA = "REGISTER_DATA";
    public static final String LOGIN_DATA = "LOGIN_DATA";

    public static final int READ_EXTERNAL_STORAGE = 160;
    public static final int RESULT_CHANGE_PHOTO_LIBRARY = 110;
    public static final int RESULT_CROP_PHOTO = 210;

    public final static String RESET_CODE = "RESET_CODE";
    public final static String PHONE_NUM = "PHONE_NUM";

    public final static String LOGIN_IS_CALL_FROM_HOME_MAIN_LIVE_STREAM = "LOGIN_IS_CALL_FROM_HOME_MAIN_LIVE_STREAM";
    public final static String ROOM_MANAGER_IS_CALL_FROM_LOGIN_ACTIVITY = "ROOM_MANAGER_IS_CALL_FROM_LOGIN_ACTIVITY";

    public final static String LIVESTREAM_SAVE_VIDEO_OR_SHOW_DETAIL = "LIVESTREAM_SAVE_VIDEO_OR_SHOW_DETAIL";

    public final static String KEY_USER_ID = "KEY_USER_ID";
    public final static String KEY_USER_NAME = "KEY_USER_NAME";
    //end common screenkey

    //gender
    public final static String GENDER_MALE = "male";
    public final static String GENDER_FEMALE = "female";
    public final static String GENDER_NO = "no";

    public final static int USER_IS_IDOL = 1;

    public final static int LIMIT_IDOL_BY_CATEGORY = 20;
    public final static String URL = "URL";
}
