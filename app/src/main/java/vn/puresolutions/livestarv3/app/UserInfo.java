package vn.puresolutions.livestarv3.app;

import android.content.Context;
import android.content.Intent;

import com.facebook.login.LoginManager;

import vn.puresolutions.livestar.activity.FacebookSignInActivity;
import vn.puresolutions.livestar.activity.RequireLoginActivity;
import vn.puresolutions.livestar.core.api.model.User;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.data.CacheHelper;
import vn.puresolutions.livestar.helper.NotificationCenter;
import vn.puresolutions.livestarv3.utilities.old.SharedPreferenceUtils;

/**
 * Created by khanh on 7/23/16.
 */
public class UserInfo {
    private static final String TOKEN = "token";
    private static final String PHONE_NUMBER = "token";
    private static final String PROFILE = "profile";

    private static String token;
    private static User user;

    public static String getToken() {
        if (token == null) {
            token = getSharedPreference().get(TOKEN, null);
        }
        return token;
    }

    public static void setToken(String token) {
        UserInfo.token = token;
        RestClient.addAuthorization(token);
        getSharedPreference().put(TOKEN, token);
    }

    public static void setPhoneNumber(String phoneNumber) {
        getSharedPreference().put(PHONE_NUMBER, phoneNumber);
    }

    public static String getPhoneNumber() {
        return getSharedPreference().get(PHONE_NUMBER, null);
    }

    public static void setUserLoggedIn(User user) {
        UserInfo.user = user;
        new Thread(() -> {
            if (UserInfo.user != null) {
                UserInfo.user.setToken(token);
                saveUserLogged();
            } else {
                RestClient.removeAuthorization();
                UserInfo.token = null;
                getSharedPreference().put(TOKEN, "");

                CacheHelper.delete(LSApplication.getInstance(), PROFILE);
            }
        }).start();

        if (user != null) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.UserLoggedIn);
        }
    }

    public static User getUserLoggedIn() {
        if (user == null) {
            CacheHelper<User> cacheHelper = new CacheHelper<>(LSApplication.getInstance());
            user = cacheHelper.load(PROFILE, User.class);
        }
        return user;
    }

    private static SharedPreferenceUtils getSharedPreference() {
        return new SharedPreferenceUtils(LSApplication.getInstance(), "user_info");
    }

    public static boolean isLoggedIn() {
        return getUserLoggedIn() != null;
    }

    public static void saveUserLogged() {
        CacheHelper<User> cacheHelper = new CacheHelper<>(LSApplication.getInstance());
        cacheHelper.save(PROFILE, UserInfo.user);
    }

    public static void logout() {
        setUserLoggedIn(null);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.UserLoggedOut);
        LoginManager.getInstance().logOut();
    }

    public static boolean checkLoginAndShowDialog(Context context) {
        boolean isLoggedIn = isLoggedIn();
        if (!isLoggedIn) {
            Intent intent = new Intent(context, RequireLoginActivity.class);
            intent.putExtra(FacebookSignInActivity.BUNDLE_KEY_IS_FINISH_WHEN_COMPLETED, true);
            context.startActivity(intent);
        }
        return isLoggedIn;
    }
}
