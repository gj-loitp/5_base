package vn.loitp.core.loitp.gallery.slide;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.daimajia.androidanimations.library.Techniques;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.photos.PhotosDataCore;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;
import vn.loitp.views.viewpager.viewpagertransformers.CubeOutTransformer;

public class GalleryCoreSlideActivity extends BaseFontActivity {
    private SlidePagerAdapter slidePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTransparentStatusNavigationBar();

        final ImageView ivBkg1 = (ImageView) findViewById(R.id.iv_bkg_1);
        final ImageView ivBkg2 = (ImageView) findViewById(R.id.iv_bkg_2);
        LImageUtil.load(activity, Constants.URL_IMG_2, ivBkg1);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        slidePagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(slidePagerAdapter);

        //viewPager.setPageTransformer(true, new CubeOutTransformer());
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                page.setRotationY(position * -30);
            }
        });

        LUIUtil.setPullLikeIOSHorizontal(viewPager);

        String photoID = getIntent().getStringExtra(Constants.SK_PHOTO_ID);
        int position = PhotosDataCore.getInstance().getPosition(photoID);
        //LLog.d(TAG, "position: " + position);
        viewPager.setCurrentItem(position);

        viewPager.setOffscreenPageLimit(3);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do nothing
            }

            @Override
            public void onPageSelected(int position) {
                Photo photo = PhotosDataCore.getInstance().getPhoto(position);
                LLog.d(TAG, "photo.getUrlS() " + photo.getUrlS());
                LLog.d(TAG, "photo.getFlickrLink100() " + photo.getFlickrLink100());
                if (position % 2 == 0) {
                    ivBkg1.setVisibility(View.INVISIBLE);
                    LImageUtil.loadNoAmin(activity, photo.getFlickrLink100(), ivBkg1, new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            ivBkg1.setVisibility(View.VISIBLE);
                            //LAnimationUtil.play(ivBkg1, Techniques.Pulse);
                            ivBkg2.setVisibility(View.INVISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            ivBkg1.setVisibility(View.VISIBLE);
                            //LAnimationUtil.play(ivBkg1, Techniques.Pulse);
                            ivBkg2.setVisibility(View.INVISIBLE);
                            return false;
                        }
                    });
                } else {
                    ivBkg2.setVisibility(View.INVISIBLE);
                    LImageUtil.loadNoAmin(activity, photo.getFlickrLink100(), ivBkg2, new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            ivBkg1.setVisibility(View.INVISIBLE);
                            ivBkg2.setVisibility(View.VISIBLE);
                            //LAnimationUtil.play(ivBkg2, Techniques.Pulse);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            ivBkg1.setVisibility(View.INVISIBLE);
                            ivBkg2.setVisibility(View.VISIBLE);
                            //LAnimationUtil.play(ivBkg2, Techniques.Pulse);
                            return false;
                        }
                    });
                }

                //get current page
                /*FrmIvSlideCore currentFrmIvSlideCore = (FrmIvSlideCore) slidePagerAdapter.instantiateItem(viewPager, position);
                if (currentFrmIvSlideCore != null) {
                    LLog.d(TAG, "onPageSelected updateBkg currentFrmIvSlideCore");
                    currentFrmIvSlideCore.updateBkg(PhotosDataCore.getInstance().getPhoto(position).getUrlO());
                }*/
                //get next page
                /*FrmIvSlideCore nextFrmIvSlideCore = (FrmIvSlideCore) slidePagerAdapter.instantiateItem(viewPager, position + 1);
                if (nextFrmIvSlideCore != null) {
                    LLog.d(TAG, "onPageSelected updateBkg nextFrmIvSlideCore");
                    nextFrmIvSlideCore.updateBkg(PhotosDataCore.getInstance().getPhoto(position).getUrlO());
                }*/
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
