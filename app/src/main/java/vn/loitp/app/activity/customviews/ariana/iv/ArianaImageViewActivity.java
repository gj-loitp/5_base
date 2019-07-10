package vn.loitp.app.activity.customviews.ariana.iv;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LStoreUtil;
import com.views.ariana.Ariana;
import com.views.ariana.GradientAngle;

import loitp.basemaster.R;

//https://github.com/akshay2211/Ariana?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6235
public class ArianaImageViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView iv = (ImageView) findViewById(R.id.iv);

        Drawable drawable = Ariana.drawable(LStoreUtil.getColors(), GradientAngle.LEFT_BOTTOM_TO_RIGHT_TOP);
        iv.setBackground(drawable);
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
        return R.layout.activity_ariana_iv;
    }
}
