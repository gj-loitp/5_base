package vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout.ex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.core.base.BaseFontActivity;
import com.core.base.BaseFragment;
import com.core.utilities.LLog;
import com.core.utilities.LStoreUtil;
import com.core.utilities.LUIUtil;
import com.utils.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;

public class DetectViewPagerSwipeOutActivity extends BaseFontActivity {
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

        LUIUtil.INSTANCE.setPullLikeIOSHorizontal(viewPager, new LUIUtil.Callback() {
            @Override
            public void onUpOrLeft(float offset) {
                LLog.INSTANCE.d(getTAG(), "onUpOrLeft " + offset);
                ToastUtils.showShort("Detect Left");
            }

            @Override
            public void onUpOrLeftRefresh(float offset) {
                LLog.INSTANCE.d(getTAG(), "onUpOrLeftRefresh " + offset);
            }

            @Override
            public void onDownOrRight(float offset) {
                LLog.INSTANCE.d(getTAG(), "onDownOrRight " + offset);
                ToastUtils.showShort("Detect Right");
            }

            @Override
            public void onDownOrRightRefresh(float offset) {
                LLog.INSTANCE.d(getTAG(), "onDownOrRightRefresh " + offset);
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
