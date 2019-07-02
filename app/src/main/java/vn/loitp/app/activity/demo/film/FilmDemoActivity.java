package vn.loitp.app.activity.demo.film;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.daimajia.androidanimations.library.Techniques;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.film.grouprecyclerviewhorizontal.VGReVHorizontal;
import vn.loitp.app.activity.demo.film.grouprecyclerviewvertical.VGReVVertical;
import vn.loitp.app.activity.demo.film.groupviewpager.VGViewPager;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LToast;

public class FilmDemoActivity extends BaseFontActivity implements View.OnClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout llBaseView;
    private NestedScrollView nsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        llBaseView = findViewById(R.id.ll_base_view);
        nsv = findViewById(R.id.nsv);
        //nsv.setNestedScrollingEnabled(false);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this::refresh);
        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout);

        findViewById(R.id.bt_clear_all).setOnClickListener(this);
        findViewById(R.id.bt_add_vgviewpager).setOnClickListener(this);
        findViewById(R.id.bt_add_dummy_textview).setOnClickListener(this);
        findViewById(R.id.bt_add_vgrev_horizontal).setOnClickListener(this);
        findViewById(R.id.bt_add_vgrev_vertical).setOnClickListener(this);

        nsv.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            /*if (scrollY > oldScrollY) {
                LLog.d(TAG, "onScrollChange Scroll DOWN");
            }
            if (scrollY < oldScrollY) {
                LLog.d(TAG, "onScrollChange Scroll UP");
            }*/
            if (scrollY == 0) {
                LLog.d(TAG, "onScrollChange TOP SCROLL");
                LToast.show(activity, "TOP SCROLL");
            }

            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                LLog.d(TAG, "onScrollChange BOTTOM SCROLL");
                LToast.show(activity, "BOTTOM SCROLL -> LOAD MORE");
            }
        });
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
            case R.id.bt_add_vgrev_horizontal:
                addVGReVHorizontal();
                break;
            case R.id.bt_add_vgrev_vertical:
                addVGReVvertical();
                break;
        }
    }

    private void refresh() {
        LUIUtil.setDelay(3000, mls -> {
            swipeRefreshLayout.setRefreshing(false);
            LToast.show(activity, "Finish refresh -> clear all views");
            clearAllViews();
        });
    }

    private void addVGViewPager() {
        final VGViewPager vgViewPager = new VGViewPager(activity);
        vgViewPager.init();
        vgViewPager.setCallback(() -> removeView(vgViewPager));
        addView(vgViewPager);
    }

    private void addDummyTextView() {
        final TextView textView = new TextView(activity);
        textView.setText("Dummy " + System.currentTimeMillis());
        textView.setTextColor(LStoreUtil.getRandomColor());
        LUIUtil.setTextShadow(textView);
        LUIUtil.setMarginsDp(textView, 10, 3, 3, 10);
        LUIUtil.setTextSize(textView, TypedValue.COMPLEX_UNIT_DIP, 18);
        textView.setOnClickListener(v -> removeView(textView));
        addView(textView);
    }

    private void addVGReVHorizontal() {
        final VGReVHorizontal vgReVHorizontal = new VGReVHorizontal(activity);
        vgReVHorizontal.setCallback(() -> removeView(vgReVHorizontal));
        addView(vgReVHorizontal);
    }

    private void addVGReVvertical() {
        final VGReVVertical vgReVVertical = new VGReVVertical(activity);
        vgReVVertical.setCallback(() -> removeView(vgReVVertical));
        addView(vgReVVertical);
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
        nsv.post(() -> nsv.fullScroll(View.FOCUS_DOWN));
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
