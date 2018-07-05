package vn.loitp.app.activity.customviews.button.circularimageclick;

import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.button.circularimageclick.CircularClickImageButton;

//guide https://github.com/hoang8f/android-flat-button
public class CircularImageClickActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CircularClickImageButton) findViewById(R.id.circleButton)).setOnCircleClickListener(new CircularClickImageButton.OnCircleClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("onClick");
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
        return R.layout.activity_circular_image_click;
    }
}
