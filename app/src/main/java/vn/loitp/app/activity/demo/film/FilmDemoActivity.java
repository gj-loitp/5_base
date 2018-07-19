package vn.loitp.app.activity.demo.film;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout.ex.VPPhoto;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.utils.util.ToastUtils;

public class FilmDemoActivity extends BaseFontActivity {
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private List<VPPhoto> vpPhotoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        int max = 5;
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
    protected int setLayoutResourceId() {
        return R.layout.activity_film_demo;
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
