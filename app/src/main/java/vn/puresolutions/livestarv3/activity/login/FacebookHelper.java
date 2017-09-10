package vn.puresolutions.livestarv3.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/29/2015
 */
public class FacebookHelper {

    private static final List<String> PERMISSION = Arrays.asList("email", "public_profile");
    private static final String LOG_TAG = FacebookHelper.class.getSimpleName();

    private Context context;
    private CallbackManager callbackManager;
    private OnLoginFacebookListener onLoginFacebookListener;

    private String token;

    private FacebookCallback<LoginResult> loginCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            getProfile(loginResult.getAccessToken());
        }

        @Override
        public void onCancel() {
            if (onLoginFacebookListener != null) {
                onLoginFacebookListener.onCancel();
            }
        }

        @Override
        public void onError(FacebookException error) {
            if (onLoginFacebookListener != null) {
                onLoginFacebookListener.onFail(error);
            }
        }
    };

    private GraphRequest.GraphJSONObjectCallback graphJSONObjectCallback = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
            try {
                if (jsonObject == null) {
                    onLoginFacebookListener.onFail(null);
                } else {
                    onLoginFacebookListener.onSuccess(jsonObject.getString("email"), token);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage() + "");
            }
        }
    };

    public FacebookHelper(Context context) {
        this.context = context;
        callbackManager = CallbackManager.Factory.create();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getProfile(AccessToken accessToken) {
        token = accessToken.getToken();

        GraphRequest request = GraphRequest.newMeRequest(accessToken, graphJSONObjectCallback);
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void performLoginFacebook(OnLoginFacebookListener onLoginFacebookListener) {
        this.onLoginFacebookListener = onLoginFacebookListener;

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) {
            getProfile(accessToken);
        } else {
            LoginManager.getInstance().logInWithReadPermissions((Activity) context, PERMISSION);
            LoginManager.getInstance().registerCallback(callbackManager, loginCallback);
        }
    }

    public void performLogoutFacebook() {
        LoginManager.getInstance().logOut();
    }

    public interface OnLoginFacebookListener {
        void onSuccess(String... params);
        void onFail(FacebookException error);
        void onCancel();
    }
}