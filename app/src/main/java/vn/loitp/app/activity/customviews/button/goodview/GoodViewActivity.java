package vn.loitp.app.activity.customviews.button.goodview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.core.base.BaseFontActivity;
import com.views.button.goodview.LGoodView;

import loitp.basemaster.R;

//https://github.com/venshine/GoodView?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=3854
public class GoodViewActivity extends BaseFontActivity {
    private LGoodView LGoodView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for button
        LGoodView = new LGoodView(getActivity());
        Button button = (Button) findViewById(R.id.bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LGoodView.setText("+1");
                LGoodView.show(v);
            }
        });

        //for imageview
        LGoodView = new LGoodView(getActivity());
        ImageView iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setColorFilter(Color.TRANSPARENT);
                LGoodView.setImage(R.mipmap.ic_launcher);
                //LGoodView.setDistance(1000);
                //LGoodView.setTranslateY(0, 10000);
                //LGoodView.setAlpha(0, 0.5f);
                //LGoodView.setDuration(3000);
                LGoodView.show(v);
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
