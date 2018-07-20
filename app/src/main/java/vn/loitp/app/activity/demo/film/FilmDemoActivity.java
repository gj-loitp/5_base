package vn.loitp.app.activity.demo.film;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.film.groupviewpager.VGViewPager;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LToast;

public class FilmDemoActivity extends BaseFontActivity implements View.OnClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout llBaseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        llBaseView = (LinearLayout) findViewById(R.id.ll_base_view);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout);

        findViewById(R.id.bt_clear_all).setOnClickListener(this);
        findViewById(R.id.bt_add_vgviewpager).setOnClickListener(this);
        findViewById(R.id.bt_add_dummy_textview).setOnClickListener(this);
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_film_demo;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_clear_all:
                clearAllViews();
                break;
            case R.id.bt_add_vgviewpager:
                addVGViewPager();
                break;
            case R.id.bt_add_dummy_textview:
                addDummyTextView();
                break;
        }
    }

    private void refresh() {
        LUIUtil.setDelay(3000, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                swipeRefreshLayout.setRefreshing(false);
                LToast.show(activity, "Finish refresh -> clear all views");
                clearAllViews();
            }
        });
    }

    private void addVGViewPager() {
        VGViewPager vgViewPager = new VGViewPager(activity);
        vgViewPager.init();
        vgViewPager.setCallback(new VGViewPager.Callback() {
            @Override
            public void onClickRemove() {
                removeView(vgViewPager);
            }
        });
        addView(vgViewPager);
    }

    private void addDummyTextView() {
        TextView textView = new TextView(activity);
        textView.setText("Dummy " + System.currentTimeMillis());
        textView.setTextColor(LStoreUtil.getRandomColor());
        LUIUtil.setTextShadow(textView);
        LUIUtil.setMarginsDp(textView, 10, 3, 3, 10);
        LUIUtil.setTextSize(textView, TypedValue.COMPLEX_UNIT_DIP, 18);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(textView);
            }
        });
        addView(textView);
    }

    //for utils
    private void clearAllViews() {
        LAnimationUtil.play(llBaseView, Techniques.FadeOut, new LAnimationUtil.Callback() {
                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onEnd() {
                        llBaseView.removeAllViews();
                        LAnimationUtil.play(llBaseView, Techniques.FadeIn);
                    }

                    @Override
                    public void onRepeat() {
                    }

                    @Override
                    public void onStart() {
                    }
                }
        );
    }

    private void addView(View view) {
        llBaseView.addView(view);
        LAnimationUtil.play(view, Techniques.FadeIn);
    }

    private void removeView(View view) {
        LAnimationUtil.play(view, Techniques.FadeOut, new LAnimationUtil.Callback() {
            @Override
            public void onCancel() {
            }

            @Override
            public void onEnd() {
                llBaseView.removeView(view);
            }

            @Override
            public void onRepeat() {
            }

            @Override
            public void onStart() {
            }
        });
    }
}
