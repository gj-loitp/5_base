package vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout.ex;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import loitp.utils.util.ToastUtils;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.base.BaseFragment;
import vn.loitp.app.utilities.LLog;
import vn.loitp.app.utilities.LStoreUtil;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.livestar.R;

public class DetectViewPagerSwipeOutActivity extends BaseActivity {
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private List<VPPhoto> vpPhotoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        int max = 3;
        for (int i = 0; i < max; i++) {
            VPPhoto vpPhoto = new VPPhoto();
            vpPhoto.setColor(LStoreUtil.getRandomColor());
            vpPhoto.setString("Page " + i + "/" + (max - 1));
            vpPhotoList.add(vpPhoto);
        }

        LUIUtil.setPullLikeIOSHorizontal(viewPager, new LUIUtil.Callback() {
            @Override
            public void onUpOrLeft(float offset) {
                LLog.d(TAG, "onUpOrLeft " + offset);
                ToastUtils.showShort("Detect Left");
            }

            @Override
            public void onUpOrLeftRefresh(float offset) {
                LLog.d(TAG, "onUpOrLeftRefresh " + offset);
            }

            @Override
            public void onDownOrRight(float offset) {
                LLog.d(TAG, "onDownOrRight " + offset);
                ToastUtils.showShort("Detect Right");
            }

            @Override
            public void onDownOrRightRefresh(float offset) {
                LLog.d(TAG, "onDownOrRightRefresh " + offset);
            }
        });

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_viewpager_detect_swipeout;
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("vpphoto", vpPhotoList.get(position));
            BaseFragment fragment = null;
            fragment = new FrmPhoto();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
