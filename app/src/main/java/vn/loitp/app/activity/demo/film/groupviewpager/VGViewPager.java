package vn.loitp.app.activity.demo.film.groupviewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LDeviceUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.views.LToast;
import vn.loitp.views.viewpager.swipeoutviewpager.SwipeOutViewPager;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class VGViewPager extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private TextView tv;
    private SwipeOutViewPager viewPager;
    private ViewPagerAdapter adapter;

    private List<Page> pageArrayList = new ArrayList<>();

    public interface Callback {
        public void onClickRemove();
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public VGViewPager(Context context) {
        super(context);
        findViews();
    }

    public VGViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        findViews();
    }

    public VGViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        findViews();
    }

    private void findViews() {
        inflate(getContext(), R.layout.vg_view_pager, this);
        tv = (TextView) findViewById(R.id.tv);
        viewPager = (SwipeOutViewPager) findViewById(R.id.vp);
    }

    public void init() {
        tv.setText("AAAAAAAAAAa");
        viewPager.setOnSwipeOutListener(new SwipeOutViewPager.OnSwipeOutListener() {
            @Override
            public void onSwipeOutAtStart() {
                LToast.show(getContext(), "onSwipeOutAtStart");
            }

            @Override
            public void onSwipeOutAtEnd() {
                LToast.show(getContext(), "onSwipeOutAtEnd");
            }
        });
        //LUIUtil.setPullLikeIOSHorizontal(viewPager);

        //5<=max<=10
        int max = LDeviceUtil.getRandomNumber(5) + 5;
        LLog.d(TAG, "max " + max);
        for (int i = 0; i < max; i++) {
            Page page = new Page();
            page.setColor(LStoreUtil.getRandomColor());
            page.setName("Loitp " + i + "/" + (max));
            if (i % 2 == 0) {
                page.setUrlImg(Constants.URL_IMG_1);
            } else {
                page.setUrlImg(Constants.URL_IMG_2);
            }
            pageArrayList.add(page);
        }

        adapter = new ViewPagerAdapter(((BaseActivity) getContext()).getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        findViewById(R.id.bt_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClickRemove();
                }
            }
        });
        //LLog.d(TAG, "init done");
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(FrmPage.BUNDLE_PAGE, pageArrayList.get(position));
            BaseFragment fragment = new FrmPage();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return pageArrayList.size();
        }
    }
}