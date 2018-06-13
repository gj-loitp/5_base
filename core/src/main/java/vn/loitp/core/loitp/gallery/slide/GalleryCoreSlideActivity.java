package vn.loitp.core.loitp.gallery.slide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.photos.PhotosDataCore;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.viewpager.viewpagertransformers.AccordionTransformer;

public class GalleryCoreSlideActivity extends BaseFontActivity {
    private SlidePagerAdapter slidePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTransparentStatusNavigationBar();

        final ImageView ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        LImageUtil.load(activity, Constants.URL_IMG_2, ivBkg);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        slidePagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(slidePagerAdapter);

        viewPager.setPageTransformer(true, new AccordionTransformer());

        LUIUtil.setPullLikeIOSHorizontal(viewPager);

        String photoID = getIntent().getStringExtra(Constants.SK_PHOTO_ID);
        int position = PhotosDataCore.getInstance().getPosition(photoID);
        //LLog.d(TAG, "position: " + position);
        viewPager.setCurrentItem(position);

        viewPager.setOffscreenPageLimit(2);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do nothing
            }

            @Override
            public void onPageSelected(int position) {
                LImageUtil.load(activity, PhotosDataCore.getInstance().getPhoto(position).getUrlS(), ivBkg, 16, 9);

                /*
                //get current page
                FrmIvSlideCore currentFrmIvSlideCore = (FrmIvSlideCore) slidePagerAdapter.instantiateItem(viewPager, position);
                if (currentFrmIvSlideCore != null) {
                    LLog.d(TAG, "onPageSelected updateBkg currentFrmIvSlideCore");
                    currentFrmIvSlideCore.updateBkg(PhotosDataCore.getInstance().getPhoto(position).getUrlO());
                }
                //get next page
                FrmIvSlideCore nextFrmIvSlideCore = (FrmIvSlideCore) slidePagerAdapter.instantiateItem(viewPager, position + 1);
                if (nextFrmIvSlideCore != null) {
                    LLog.d(TAG, "onPageSelected updateBkg nextFrmIvSlideCore");
                    nextFrmIvSlideCore.updateBkg(PhotosDataCore.getInstance().getPhoto(position).getUrlO());
                }
                */
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
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
        return R.layout.activity_gallery_core_slide;
    }

    private class SlidePagerAdapter extends FragmentStatePagerAdapter {

        SlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //LLog.d(TAG, "getItem position " + position);
            FrmIvSlideCore frmIvSlideCore = new FrmIvSlideCore();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.SK_PHOTO_PISITION, position);
            frmIvSlideCore.setArguments(bundle);
            return frmIvSlideCore;
        }

        @Override
        public int getCount() {
            return PhotosDataCore.getInstance().getSize();
        }
    }
}
