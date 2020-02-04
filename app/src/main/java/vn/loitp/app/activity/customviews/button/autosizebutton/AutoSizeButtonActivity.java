package vn.loitp.app.activity.customviews.button.autosizebutton;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;
import com.core.utilities.LScreenUtil;
import com.views.LToast;
import com.views.autosize.imagebuttonwithsize.ImageButtonWithSize;

import vn.loitp.app.R;

public class AutoSizeButtonActivity extends BaseFontActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_rotate).setOnClickListener(this);

        ImageButtonWithSize bt0 = findViewById(R.id.bt_0);
        bt0.setPortraitSizeWInDp(50);
        bt0.setPortraitSizeHInDp(50);
        bt0.setLandscapeSizeWInDp(250);
        bt0.setLandscapeSizeHInDp(250);
        bt0.setOnClickListener(this);

        ImageButtonWithSize bt1 = findViewById(R.id.bt_1);
        bt1.setPortraitSizeWInDp(150);
        bt1.setPortraitSizeHInDp(150);
        bt1.setLandscapeSizeWInDp(100);
        bt1.setLandscapeSizeHInDp(100);
        bt1.setOnClickListener(this);

        ImageButtonWithSize bt2 = findViewById(R.id.bt_2);
        bt2.setPortraitSizeWInPx(LScreenUtil.INSTANCE.getScreenWidth());
        bt2.setPortraitSizeHInPx(LScreenUtil.INSTANCE.getScreenWidth() / 10);
        bt2.setLandscapeSizeWInPx(LScreenUtil.INSTANCE.getScreenWidth() / 2);
        bt2.setLandscapeSizeHInPx(LScreenUtil.INSTANCE.getScreenWidth() / 2);
        bt2.setOnClickListener(this);
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
        return R.layout.activity_autosize_button;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_rotate:
                LActivityUtil.toggleScreenOritation(getActivity());
                break;
            case R.id.bt_0:
            case R.id.bt_1:
            case R.id.bt_2:
                LToast.show(getActivity(), "Click");
                break;
        }
    }
}
