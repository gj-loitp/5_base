package vn.loitp.app.activity.demo.film;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.BaseFontActivity;
import com.core.utilities.LAnimationUtil;
import com.core.utilities.LLog;
import com.core.utilities.LStoreUtil;
import com.core.utilities.LUIUtil;
import com.daimajia.androidanimations.library.Techniques;
import com.views.LToast;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.film.grouprecyclerviewhorizontal.VGReVHorizontal;
import vn.loitp.app.activity.demo.film.grouprecyclerviewvertical.VGReVVertical;
import vn.loitp.app.activity.demo.film.groupviewpager.VGViewPager;

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
        LUIUtil.INSTANCE.setColorForSwipeRefreshLayout(swipeRefreshLayout);

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
                LLog.d(getTAG(), "onScrollChange TOP SCROLL");
                LToast.show(getActivity(), "TOP SCROLL");
            }

            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                LLog.d(getTAG(), "onScrollChange BOTTOM SCROLL");
                LToast.show(getActivity(), "BOTTOM SCROLL -> LOAD MORE");
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
        LUIUtil.INSTANCE.setDelay(3000, new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                LToast.show(getActivity(), "Finish refresh -> clear all views");
                clearAllViews();
            }
        });
    }

    private void addVGViewPager() {
        final VGViewPager vgViewPager = new VGViewPager(getActivity());
        vgViewPager.init();
        vgViewPager.setCallback(() -> removeView(vgViewPager));
        addView(vgViewPager);
    }

    private void addDummyTextView() {
        final TextView textView = new TextView(getActivity());
        textView.setText("Dummy " + System.currentTimeMillis());
        textView.setTextColor(LStoreUtil.getRandomColor());
        LUIUtil.INSTANCE.setTextShadow(textView);
        LUIUtil.INSTANCE.setMarginsDp(textView, 10, 3, 3, 10);
        LUIUtil.INSTANCE.setTextSize(textView, TypedValue.COMPLEX_UNIT_DIP, 18);
        textView.setOnClickListener(v -> removeView(textView));
        addView(textView);
    }

    private void addVGReVHorizontal() {
        final VGReVHorizontal vgReVHorizontal = new VGReVHorizontal(getActivity());
        vgReVHorizontal.setCallback(() -> removeView(vgReVHorizontal));
        addView(vgReVHorizontal);
    }

    private void addVGReVvertical() {
        final VGReVVertical vgReVVertical = new VGReVVertical(getActivity());
        vgReVVertical.setCallback(() -> removeView(vgReVVertical));
        addView(vgReVVertical);
    }

    //for utils
    private void clearAllViews() {
        LAnimationUtil.INSTANCE.play(llBaseView, Techniques.FadeOut, new LAnimationUtil.Callback() {
                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onEnd() {
                        llBaseView.removeAllViews();
                        LAnimationUtil.INSTANCE.play(llBaseView, Techniques.FadeIn);
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
        LAnimationUtil.INSTANCE.play(view, Techniques.FadeIn);
        nsv.post(() -> nsv.fullScroll(View.FOCUS_DOWN));
    }

    private void removeView(View view) {
        LAnimationUtil.INSTANCE.play(view, Techniques.FadeOut, new LAnimationUtil.Callback() {
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
