package vn.loitp.app.activity.customviews.bottomnavigationbar.bottombar;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LToast;
import vn.loitp.views.bottombar.LBottomBar;

public class BottomBarActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText(LStoreUtil.readTxtFromRawFolder(activity, R.raw.loitp));
        LBottomBar lBottomBar = (LBottomBar) findViewById(R.id.bottom_bar);
        lBottomBar.setColorIvOn(R.color.Red);
        lBottomBar.setColorIvOff(R.color.Pink);
        lBottomBar.setItem0(R.drawable.baseline_bug_report_black_48, "Bug report");
        lBottomBar.setItem1(R.drawable.baseline_add_black_48, "Add");
        lBottomBar.setItem2(R.drawable.baseline_chat_black_48dp, "Chat");
        lBottomBar.setItem3(R.drawable.baseline_clear_black_48, "Clear");
        lBottomBar.setItem4(R.drawable.baseline_cloud_download_black_48, "Cloud");
        lBottomBar.setItem5(R.drawable.baseline_picture_in_picture_alt_white_48dp, "Picture");
        lBottomBar.setTechniques(Techniques.Bounce);
        lBottomBar.setOnItemClick(new LBottomBar.Callback() {
            @Override
            public void OnClickItem(int position) {
                LToast.show(activity, "Touch " + position);
            }
        });
        findViewById(R.id.bt_blur_view_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lBottomBar.getBlurView().setOverlayColor(ContextCompat.getColor(activity, R.color.RedTrans));
            }
        });
        findViewById(R.id.bt_blur_view_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lBottomBar.getBlurView().setOverlayColor(ContextCompat.getColor(activity, R.color.GreenTrans));
            }
        });
        findViewById(R.id.bt_count_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lBottomBar.setCount(1);
            }
        });
        findViewById(R.id.bt_count_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lBottomBar.setCount(3);
            }
        });
        findViewById(R.id.bt_count_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lBottomBar.setCount(5);
            }
        });
        findViewById(R.id.bt_count_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lBottomBar.setCount(6);
            }
        });
        findViewById(R.id.bt_cl_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lBottomBar.setColorTextView(R.color.Red);
            }
        });
        findViewById(R.id.bt_cl_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lBottomBar.setColorTextView(R.color.Black);
            }
        });
        findViewById(R.id.bt_cl_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lBottomBar.setColorTextView(R.color.Blue);
            }
        });
        LUIUtil.setDelay(5000, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                if (lBottomBar != null) {
                    lBottomBar.setPerformItemClick(4);
                }
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
        return R.layout.activity_bottom_bar;
    }
}
