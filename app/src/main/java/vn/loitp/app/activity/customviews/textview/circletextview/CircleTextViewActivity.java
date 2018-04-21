package vn.loitp.app.activity.customviews.textview.circletextview;

import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.textview.circletextview.CircleTextView;

public class CircleTextViewActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CircleTextView textView = (CircleTextView) findViewById(R.id.circle_text_view);
        textView.setTextSize(R.dimen.txt_18);

        SpannableStringBuilder builder = new SpannableStringBuilder(getResources().getString(R.string.hello_world));
        textView.setText(builder);
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
        return R.layout.activity_circle_textview;
    }

}
