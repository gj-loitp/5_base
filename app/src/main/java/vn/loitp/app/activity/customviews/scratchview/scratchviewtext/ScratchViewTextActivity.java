package vn.loitp.app.activity.customviews.scratchview.scratchviewtext;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.scratchview.ScratchImageView;
import vn.loitp.views.scratchview.ScratchTextView;

public class ScratchViewTextActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = (TextView) findViewById(R.id.tv);
        ScratchTextView scratchTextView = (ScratchTextView) findViewById(R.id.scratchview);
        scratchTextView.setRevealListener(new ScratchTextView.IRevealListener() {
            @Override
            public void onRevealed(ScratchTextView tv) {
                textView.setText("onRevealed");
            }

            @Override
            public void onRevealPercentChangedListener(ScratchTextView stv, float percent) {
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
        return R.layout.activity_scratchview_text;
    }
}
