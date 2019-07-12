package vn.loitp.app.activity.customviews.facebookcomment;

import android.os.Bundle;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.core.utilities.LSocialUtil;

import loitp.basemaster.R;

public class FacebookCommentActivity extends BaseFontActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LSocialUtil.openFacebookComment(getActivity(), "http://truyentranhtuan.com/one-piece-chuong-907/", getString(R.string.str_b));
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
        return R.layout.activity_fb_cmt;
    }
}
