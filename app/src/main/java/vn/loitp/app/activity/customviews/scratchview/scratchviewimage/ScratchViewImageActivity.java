package vn.loitp.app.activity.customviews.scratchview.scratchviewimage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.scratchview.ScratchImageView;

public class ScratchViewImageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScratchImageView scratchImageView = (ScratchImageView) findViewById(R.id.sample_image);
        TextView textView = (TextView) findViewById(R.id.tv);
        scratchImageView.setRevealListener(new ScratchImageView.IRevealListener() {
            @Override
            public void onRevealed(ScratchImageView tv) {
                textView.setText("onRevealed");
            }

            @Override
            public void onRevealPercentChangedListener(ScratchImageView siv, float percent) {
                textView.setText("onRevealPercentChangedListener percent: " + percent);
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
        return R.layout.activity_scratchview_image;
    }
}
