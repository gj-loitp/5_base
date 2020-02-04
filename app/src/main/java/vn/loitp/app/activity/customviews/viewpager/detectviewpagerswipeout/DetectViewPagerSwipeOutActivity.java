package vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout;

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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.R;

public class DetectViewPagerSwipeOutActivity extends BaseFontActivity {
    private List<VPPhoto> vpPhotoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPager viewPager = findViewById(R.id.viewpager);

        int max = 3;
        for (int i = 0; i < max; i++) {
            VPPhoto vpPhoto = new VPPhoto();
            vpPhoto.setColor(LStoreUtil.INSTANCE.getRandomColor());
            vpPhoto.setString("Page " + i + "/" + (max - 1));
            vpPhotoList.add(vpPhoto);
        }

        LUIUtil.INSTANCE.setPullLikeIOSHorizontal(viewPager, new LUIUtil.Callback() {
            @Override
            public void onUpOrLeft(float offset) {
                LLog.d(getTAG(), "onUpOrLeft " + offset);
                showShort("Detect Left");
            }

            @Override
            public void onUpOrLeftRefresh(float offset) {
                LLog.d(getTAG(), "onUpOrLeftRefresh " + offset);
            }

            @Override
            public void onDownOrRight(float offset) {
                LLog.d(getTAG(), "onDownOrRight " + offset);
                showShort("Detect Right");
            }

            @Override
            public void onDownOrRightRefresh(float offset) {
                LLog.d(getTAG(), "onDownOrRightRefresh " + offset);
            }
        });

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
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

        ViewPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NotNull
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
