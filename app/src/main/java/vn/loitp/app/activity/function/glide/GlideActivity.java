package vn.loitp.app.activity.function.glide;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LImageUtil;

import loitp.basemaster.R;

public class GlideActivity extends BaseFontActivity implements View.OnClickListener {
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iv = (ImageView) findViewById(R.id.iv);
        findViewById(R.id.bt_0).setOnClickListener(this);
        findViewById(R.id.bt_1).setOnClickListener(this);
        findViewById(R.id.bt_2).setOnClickListener(this);
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
        return R.layout.activity_glide;
    }

    private final String urlLow = "https://c1.staticflickr.com/5/4740/39507376464_359b870746_z.jpg";
    private final String urlMedium = "https://c1.staticflickr.com/5/4740/39507376464_e00b5e16d7_h.jpg";
    private final String urlHigh = "https://c1.staticflickr.com/5/4740/39507376464_c632938112_o.jpg";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_0:
                LImageUtil.INSTANCE.loadNoAmin(getActivity(), urlLow, urlLow, iv, null);
                break;
            case R.id.bt_1:
                LImageUtil.INSTANCE.loadNoAmin(getActivity(), urlMedium, urlLow, iv, null);
                break;
            case R.id.bt_2:
                LImageUtil.INSTANCE.loadNoAmin(getActivity(), urlHigh, urlLow, iv, null);
                break;
        }
    }
}
