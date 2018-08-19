package vn.loitp.app.activity.customviews.lcardview;

import android.os.Bundle;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.views.card.LCardView;

public class LCardViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LCardView lCardView0 = (LCardView) findViewById(R.id.l_card_view_0);
        lCardView0.setCallback(new LCardView.Callback() {
            @Override
            public void onClickRoot(View v) {
                LAnimationUtil.play(v, Techniques.Pulse);
            }

            @Override
            public void onLongClickRoot(View v) {
                LAnimationUtil.play(v, Techniques.Pulse);
            }

            @Override
            public void onClickText(View v) {
                LAnimationUtil.play(v, Techniques.Pulse);
            }

            @Override
            public void onLongClickText(View v) {
                LAnimationUtil.play(v, Techniques.Pulse);
            }
        });
        lCardView0.setText(System.currentTimeMillis() + "");
        lCardView0.setHeight(300);
        lCardView0.setRadius(10);
        lCardView0.setImg(Constants.URL_IMG_2);

        LCardView lCardView1 = (LCardView) findViewById(R.id.l_card_view_1);
        lCardView1.setText(System.currentTimeMillis() + "");
        lCardView1.setHeight(600);
        lCardView1.setRadius(20);
        lCardView1.setImg(Constants.URL_IMG_4);
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
        return R.layout.activity_l_card_view;
    }
}
