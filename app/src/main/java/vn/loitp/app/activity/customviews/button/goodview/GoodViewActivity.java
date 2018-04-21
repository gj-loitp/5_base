package vn.loitp.app.activity.customviews.button.goodview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.button.goodview.GoodView;

//https://github.com/venshine/GoodView?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=3854
public class GoodViewActivity extends BaseActivity {
    private GoodView goodView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for button
        goodView = new GoodView(activity);
        Button button = (Button) findViewById(R.id.bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodView.setText("+1");
                goodView.show(v);
            }
        });

        //for imageview
        goodView = new GoodView(activity);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setColorFilter(Color.TRANSPARENT);
                goodView.setImage(R.mipmap.ic_launcher);
                //goodView.setDistance(1000);
                //goodView.setTranslateY(0, 10000);
                //goodView.setAlpha(0, 0.5f);
                //goodView.setDuration(3000);
                goodView.show(v);
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
        return R.layout.activity_good_view;
    }
}
