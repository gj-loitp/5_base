package vn.loitp.core.loitp.gallery.slide;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.photos.PhotosDataCore;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LSocialUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.task.AsyncTaskDownloadImage;
import vn.loitp.views.layout.floatdraglayout.DisplayUtil;
import vn.loitp.views.viewpager.viewpagertransformers.StackTransformer;

public class GalleryCoreSlideActivity extends BaseFontActivity {
    private SlidePagerAdapter slidePagerAdapter;
    private TextView tvSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTransparentStatusNavigationBar();
            LUIUtil.setMargins(findViewById(R.id.rl_control), 0, 0, 0, DisplayUtil.getNavigationBarHeight(activity));
        } else {
            LUIUtil.setMargins(findViewById(R.id.rl_control), 0, 0, 0, DisplayUtil.getStatusHeight(activity));
        }

        int bkgRootView = getIntent().getIntExtra(Constants.BKG_ROOT_VIEW, Constants.NOT_FOUND);
        LLog.d(TAG, "bkgRootView " + bkgRootView);
        if (bkgRootView == Constants.NOT_FOUND) {
            getRootView().setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        } else {
            getRootView().setBackgroundResource(bkgRootView);
        }

        tvSize = (TextView) findViewById(R.id.tv_size);

        //final ImageView ivBkg1 = (ImageView) findViewById(R.id.iv_bkg_1);
        //final ImageView ivBkg2 = (ImageView) findViewById(R.id.iv_bkg_2);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        slidePagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(slidePagerAdapter);
        LUIUtil.setPullLikeIOSHorizontal(viewPager);
        viewPager.setPageTransformer(true, new StackTransformer());

        String photoID = getIntent().getStringExtra(Constants.SK_PHOTO_ID);
        int position = PhotosDataCore.getInstance().getPosition(photoID);
        //LLog.d(TAG, "position: " + position);
        viewPager.setCurrentItem(position);

        //viewPager.setOffscreenPageLimit(3);

        /*viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do nothing
            }

            @Override
            public void onPageSelected(int position) {
                Photo photo = PhotosDataCore.getInstance().getPhoto(position);
                tvSize.setText(photo.getWidthO() + "x" + photo.getHeightO());
                //LLog.d(TAG, "photo.getUrlS() " + photo.getUrlS());
                //LLog.d(TAG, "photo.getFlickrLink100() " + photo.getFlickrLink100());
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
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });*/

        findViewById(R.id.bt_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LAnimationUtil.play(v, Techniques.Pulse);
                new AsyncTaskDownloadImage(getApplicationContext(), PhotosDataCore.getInstance().getPhoto(viewPager.getCurrentItem()).getUrlO()).execute();
            }
        });
        findViewById(R.id.bt_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LAnimationUtil.play(v, Techniques.Pulse);
                LSocialUtil.share(activity, PhotosDataCore.getInstance().getPhoto(viewPager.getCurrentItem()).getUrlO());
            }
        });
        findViewById(R.id.bt_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LAnimationUtil.play(v, Techniques.Pulse);
                LSocialUtil.sendEmail(activity);
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return false;
        } else {
            return true;
        }
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
