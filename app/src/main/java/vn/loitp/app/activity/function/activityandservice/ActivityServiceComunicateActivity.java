package vn.loitp.app.activity.function.activityandservice;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.views.LToast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.floatingwidget.ComunicateMng;

public class ActivityServiceComunicateActivity extends BaseFontActivity {
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//Check if the application has draw over other apps permission or not?
        //This permission is by default available for API<23. But for API > 23
        //you have to ask for the permission in runtime.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
            initializeView();
        }
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
        return R.layout.activity_service_communicate;
    }

    private void initializeView() {
        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.notify_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LToast.INSTANCE.show(getActivity(), "onClick TestService");
                tv.setText("");
                startService(new Intent(getActivity(), TestService.class));
            }
        });
        Button button0 = (Button) findViewById(R.id.bt_0);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComunicateMng.postFromActivity(new ComunicateMng.MsgFromActivity(button0.getText().toString()));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                initializeView();
            } else { //Permission is not available
                LToast.INSTANCE.show(getActivity(), "Draw over other app permission not available. Closing the application");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //listen msg from service
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ComunicateMng.MsgFromService msg) {
        tv.setText(msg.getMsg());
    }
}
