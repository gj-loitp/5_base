package vn.loitp.app.activity.customviews.sticker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.views.sticker.lib.StickerImageView;
import vn.loitp.views.sticker.lib.StickerTextView;

public class StickerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout canvas = (FrameLayout) findViewById(R.id.vg_canvas);
        // add a stickerImage to canvas
        StickerImageView iv_sticker = new StickerImageView(activity);
        //iv_sticker.setImageDrawable(((ImageView) view.findViewById(R.id.iv_sticker)).getDrawable());
        iv_sticker.setBackgroundResource(R.drawable.logo);
        canvas.addView(iv_sticker);

        // add a stickerText to canvas
        StickerTextView tv_sticker = new StickerTextView(activity);
        tv_sticker.setText("Demo StickerText");
        canvas.addView(tv_sticker);

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
        return R.layout.activity_sticker;
    }
}
