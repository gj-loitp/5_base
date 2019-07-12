package vn.loitp.app.activity.demo.firebase.fcm;

import android.os.Bundle;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.core.common.Constants;
import com.core.utilities.LDialogUtil;
import com.core.utilities.LFCMUtil;

import loitp.basemaster.R;

//https://docs.google.com/document/d/1LIkZgTWQB7FWVHUrc-ZrQaMVJkm6lbuMwirr1XCPwcA/edit
public class FCMFirebaseActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String fcmKey = "AIzaSyDmo9cOZx7wb1R1cL7zPhw1YRxEpJFOzgo";
        findViewById(R.id.bt_send_fcm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.INSTANCE.getIS_DEBUG()) {
                    LFCMUtil.sendNotification(fcmKey, "Hello! This is a notification!");
                } else {
                    LDialogUtil.showDialog1(getActivity(), "Message", "This feature is disabled by Loitp", "Okay", new LDialogUtil.Callback1() {
                        @Override
                        public void onClick1() {
                            //do nothing
                        }
                    });
                }
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_fcm_firebase;
    }
}
