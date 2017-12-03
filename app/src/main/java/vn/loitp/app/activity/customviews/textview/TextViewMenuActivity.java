package vn.loitp.app.activity.customviews.textview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import vn.loitp.app.activity.customviews.textview.colortextview.ColorTextViewActivity;
import vn.loitp.app.activity.customviews.textview.countdown.CountDownActivity;
import vn.loitp.app.activity.customviews.textview.scoretext.ScoreTextViewActivity;
import vn.loitp.app.activity.customviews.textview.scrollnumber.ScrollNumberActivity;
import vn.loitp.app.activity.customviews.textview.translucentview.TranslucentViewActivity;
import vn.loitp.app.activity.customviews.textview.typewritertextview.TypeWriterTextViewActivity;
import vn.loitp.app.activity.customviews.textview.verticalmarqueetextview.VerticalMarqueeTextViewActivity;
import vn.loitp.app.activity.customviews.textview.zoomtextview.ZoomTextViewActivity;
import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.core.utilities.LUIUtil;

public class TextViewMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_score_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ScoreTextViewActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
        findViewById(R.id.bt_countdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CountDownActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
        findViewById(R.id.bt_color_textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ColorTextViewActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
        findViewById(R.id.bt_scroll_number).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ScrollNumberActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
        findViewById(R.id.bt_zoom_textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ZoomTextViewActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
        findViewById(R.id.bt_vertical_marquee_textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, VerticalMarqueeTextViewActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
        findViewById(R.id.bt_translucent_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TranslucentViewActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
        findViewById(R.id.bt_type_writer_textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TypeWriterTextViewActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_menu_textview;
    }
}
