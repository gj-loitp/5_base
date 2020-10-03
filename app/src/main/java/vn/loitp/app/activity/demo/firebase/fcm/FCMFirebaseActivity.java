package vn.loitp.app.activity.demo.firebase.fcm;

import android.os.Bundle;

import com.annotation.IsFullScreen;
import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;
import com.core.common.Constants;
import com.core.utilities.LDialogUtil;
import com.core.utilities.LFCMUtil;
import com.interfaces.Callback1;

import vn.loitp.app.R;

//https://docs.google.com/document/d/1LIkZgTWQB7FWVHUrc-ZrQaMVJkm6lbuMwirr1XCPwcA/edit

@LayoutId(R.layout.activity_fcm_firebase)
@LogTag("FCMFirebaseActivity")
@IsFullScreen(false)
public class FCMFirebaseActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String fcmKey = "AIzaSyDmo9cOZx7wb1R1cL7zPhw1YRxEpJFOzgo";

        findViewById(R.id.bt_send_fcm).setOnClickListener(v -> {
            if (Constants.Companion.getIS_DEBUG()) {
                LFCMUtil.Companion.sendNotification(fcmKey, "Hello! This is a notification! " + System.currentTimeMillis(), null, null);
            } else {
                LDialogUtil.Companion.showDialog1(FCMFirebaseActivity.this, "Message", "This feature is disabled by Loitp", "Okay", new Callback1() {
                    @Override
                    public void onClick1() {
                        //do nothing
                    }
                });
            }
        });
    }

}
