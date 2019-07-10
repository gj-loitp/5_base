package vn.loitp.app.activity.animation.basictransition;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.core.base.BaseFontActivity;
import com.core.common.Constants;
import com.core.utilities.LImageUtil;

import loitp.basemaster.R;

public class BasicTransition1Activity extends BaseFontActivity {
    public static final String IV = "iv";
    public static final String TV = "tv";
    private ImageView iv;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iv = (ImageView) findViewById(R.id.imageview_item);
        tv = (TextView) findViewById(R.id.tv);
        LImageUtil.load(activity, Constants.INSTANCE.getURL_IMG_2(), iv);
        ViewCompat.setTransitionName(iv, IV);
        ViewCompat.setTransitionName(tv, TV);
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
        return R.layout.activity_basic_transition_1;
    }
}
