package vn.loitp.app.activity.customviews.lcardview;

import android.os.Bundle;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.core.common.Constants;
import com.core.utilities.LAnimationUtil;
import com.daimajia.androidanimations.library.Techniques;
import com.views.card.LCardView;

import loitp.basemaster.R;

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
        lCardView0.setImg(Constants.INSTANCE.getURL_IMG_2());

        LCardView lCardView1 = (LCardView) findViewById(R.id.l_card_view_1);
        lCardView1.setText(System.currentTimeMillis() + "");
        lCardView1.setHeight(600);
        lCardView1.setRadius(50);
        lCardView1.setImg(Constants.INSTANCE.getURL_IMG_4());
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
