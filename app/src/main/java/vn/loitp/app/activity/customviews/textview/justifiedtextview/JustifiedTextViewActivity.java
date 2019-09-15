package vn.loitp.app.activity.customviews.textview.justifiedtextview;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.core.base.BaseFontActivity;
import com.views.textview.justified.LJustifiedTextView;

import loitp.basemaster.R;

public class JustifiedTextViewActivity extends BaseFontActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        LJustifiedTextView jtv = new LJustifiedTextView(getApplicationContext(), getString(R.string.large_text));
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
