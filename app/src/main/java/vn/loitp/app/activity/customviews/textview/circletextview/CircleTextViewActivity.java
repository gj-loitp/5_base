package vn.loitp.app.activity.customviews.textview.circletextview;

import android.os.Bundle;
import android.text.SpannableStringBuilder;

import com.core.base.BaseFontActivity;
import com.views.textview.circle.LCircleTextView;

import loitp.basemaster.R;

public class CircleTextViewActivity extends BaseFontActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LCircleTextView textView = (LCircleTextView) findViewById(R.id.circle_text_view);
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
