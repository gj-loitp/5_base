package vn.loitp.app.activity.album.option1;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LSocialUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.viewpager.parrallaxviewpager.lib.parrallaxviewpager.Mode;
import vn.loitp.views.viewpager.parrallaxviewpager.lib.parrallaxviewpager.ParallaxViewPager;

public class GalleryMenuAlbumActivity extends BaseActivity {
    private ParallaxViewPager viewPager;
    private ViewPagerAdapter adapter;
    private List<String> stringList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExist = false;
        viewPager = (ParallaxViewPager) findViewById(R.id.viewpager);

        stringList.add("Category");
        stringList.add("Vietnamese");
        stringList.add("Gift");
        stringList.add("More");

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setMode(Mode.RIGHT_OVERLAY);
        viewPager.setAdapter(adapter);

        LUIUtil.setPullLikeIOSHorizontal(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
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
        return R.layout.activity_gallery_slide_album;
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FrmPhotoCategory();
                case 1:
                    return new FrmPhotoVietnamese();
                case 2:
                    return new FrmPhotoGift();
                case 3:
                    return new FrmPhotoMore();
            }
            return null;
        }

        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return stringList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        LDialogUtil.showDialog3(activity, getString(R.string.app_name), getString(R.string.msg_exit_app), getString(R.string.yes), getString(R.string.no), getString(R.string.rate), new LDialogUtil.Callback3() {
            @Override
            public void onClick1() {
                finish();
                LUIUtil.transActivityTopToBottomAniamtion(activity);
            }

            @Override
            public void onClick2() {
                //do nothing
            }

            @Override
            public void onClick3() {
                LSocialUtil.rateApp(activity, getPackageName());
            }
        });
    }
}
