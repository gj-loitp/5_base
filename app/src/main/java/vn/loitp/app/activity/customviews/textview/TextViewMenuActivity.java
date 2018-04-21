package vn.loitp.app.activity.customviews.textview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import vn.loitp.app.activity.customviews.textview.autofittextview.AutoFitTextViewActivity;
import vn.loitp.app.activity.customviews.textview.circletextview.CircleTextViewActivity;
import vn.loitp.app.activity.customviews.textview.colortextview.ColorTextViewActivity;
import vn.loitp.app.activity.customviews.textview.countdown.CountDownActivity;
import vn.loitp.app.activity.customviews.textview.justifiedtextview.JustifiedTextViewActivity;
import vn.loitp.app.activity.customviews.textview.scoretext.ScoreTextViewActivity;
import vn.loitp.app.activity.customviews.textview.scrollnumber.ScrollNumberActivity;
import vn.loitp.app.activity.customviews.textview.selectabletextView.SelectableTextViewActivity;
import vn.loitp.app.activity.customviews.textview.textdecorator.TextDecoratorActivity;
import vn.loitp.app.activity.customviews.textview.translucentview.TranslucentViewActivity;
import vn.loitp.app.activity.customviews.textview.typewritertextview.TypeWriterTextViewActivity;
import vn.loitp.app.activity.customviews.textview.verticalmarqueetextview.VerticalMarqueeTextViewActivity;
import vn.loitp.app.activity.customviews.textview.zoomtextview.ZoomTextViewActivity;
import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.textview.autofittextview.AutofitTextView;

public class TextViewMenuActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_autofit_textview).setOnClickListener(this);
        findViewById(R.id.bt_circle_textview).setOnClickListener(this);
        findViewById(R.id.bt_score_text).setOnClickListener(this);
        findViewById(R.id.bt_countdown).setOnClickListener(this);
        findViewById(R.id.bt_color_textview).setOnClickListener(this);
        findViewById(R.id.bt_scroll_number).setOnClickListener(this);
        findViewById(R.id.bt_selectable_textview).setOnClickListener(this);
        findViewById(R.id.bt_zoom_textview).setOnClickListener(this);
        findViewById(R.id.bt_vertical_marquee_textview).setOnClickListener(this);
        findViewById(R.id.bt_translucent_view).setOnClickListener(this);
        findViewById(R.id.bt_type_writer_textview).setOnClickListener(this);
        findViewById(R.id.bt_text_decorator).setOnClickListener(this);
        findViewById(R.id.bt_justifield_textview).setOnClickListener(this);
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
        return R.layout.activity_menu_textview;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_autofit_textview:
                intent = new Intent(activity, AutoFitTextViewActivity.class);
                break;
            case R.id.bt_circle_textview:
                intent = new Intent(activity, CircleTextViewActivity.class);
                break;
            case R.id.bt_score_text:
                intent = new Intent(activity, ScoreTextViewActivity.class);
                break;
            case R.id.bt_countdown:
                intent = new Intent(activity, CountDownActivity.class);
                break;
            case R.id.bt_color_textview:
                intent = new Intent(activity, ColorTextViewActivity.class);
                break;
            case R.id.bt_scroll_number:
                intent = new Intent(activity, ScrollNumberActivity.class);
                break;
            case R.id.bt_selectable_textview:
                intent = new Intent(activity, SelectableTextViewActivity.class);
                break;
            case R.id.bt_zoom_textview:
                intent = new Intent(activity, ZoomTextViewActivity.class);
                break;
            case R.id.bt_vertical_marquee_textview:
                intent = new Intent(activity, VerticalMarqueeTextViewActivity.class);
                break;
            case R.id.bt_translucent_view:
                intent = new Intent(activity, TranslucentViewActivity.class);
                break;
            case R.id.bt_type_writer_textview:
                intent = new Intent(activity, TypeWriterTextViewActivity.class);
                break;
            case R.id.bt_text_decorator:
                intent = new Intent(activity, TextDecoratorActivity.class);
                break;
            case R.id.bt_justifield_textview:
                intent = new Intent(activity, JustifiedTextViewActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
