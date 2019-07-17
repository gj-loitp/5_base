package vn.loitp.app.activity.customviews.textview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;

import loitp.basemaster.R;
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

public class TextViewMenuActivity extends BaseFontActivity implements OnClickListener {

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
                intent = new Intent(getActivity(), AutoFitTextViewActivity.class);
                break;
            case R.id.bt_circle_textview:
                intent = new Intent(getActivity(), CircleTextViewActivity.class);
                break;
            case R.id.bt_score_text:
                intent = new Intent(getActivity(), ScoreTextViewActivity.class);
                break;
            case R.id.bt_countdown:
                intent = new Intent(getActivity(), CountDownActivity.class);
                break;
            case R.id.bt_color_textview:
                intent = new Intent(getActivity(), ColorTextViewActivity.class);
                break;
            case R.id.bt_scroll_number:
                intent = new Intent(getActivity(), ScrollNumberActivity.class);
                break;
            case R.id.bt_selectable_textview:
                intent = new Intent(getActivity(), SelectableTextViewActivity.class);
                break;
            case R.id.bt_zoom_textview:
                intent = new Intent(getActivity(), ZoomTextViewActivity.class);
                break;
            case R.id.bt_vertical_marquee_textview:
                intent = new Intent(getActivity(), VerticalMarqueeTextViewActivity.class);
                break;
            case R.id.bt_translucent_view:
                intent = new Intent(getActivity(), TranslucentViewActivity.class);
                break;
            case R.id.bt_type_writer_textview:
                intent = new Intent(getActivity(), TypeWriterTextViewActivity.class);
                break;
            case R.id.bt_text_decorator:
                intent = new Intent(getActivity(), TextDecoratorActivity.class);
                break;
            case R.id.bt_justifield_textview:
                intent = new Intent(getActivity(), JustifiedTextViewActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.INSTANCE.tranIn(getActivity());
        }
    }
}
