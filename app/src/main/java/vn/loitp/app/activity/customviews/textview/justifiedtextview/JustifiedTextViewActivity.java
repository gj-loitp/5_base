package vn.loitp.app.activity.customviews.textview.justifiedtextview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.textview.justifiedtextview.JustifiedTextView;

public class JustifiedTextViewActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        JustifiedTextView jtv = new JustifiedTextView(getApplicationContext(), getString(R.string.large_text));
        ll.addView(jtv);
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
        return R.layout.activity_justifield_textview;
    }
}
