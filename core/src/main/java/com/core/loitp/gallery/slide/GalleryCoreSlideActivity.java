package com.core.loitp.gallery.slide;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.core.base.BaseFontActivity;
import com.core.common.Constants;
import com.core.loitp.gallery.photos.PhotosDataCore;
import com.core.utilities.LAnimationUtil;
import com.core.utilities.LLog;
import com.core.utilities.LSocialUtil;
import com.core.utilities.LUIUtil;
import com.daimajia.androidanimations.library.Techniques;
import com.task.AsyncTaskDownloadImage;
import com.views.layout.floatdraglayout.DisplayUtil;
import com.views.viewpager.viewpagertransformers.ZoomOutSlideTransformer;

import loitp.core.R;

public class GalleryCoreSlideActivity extends BaseFontActivity {
    private LinearLayout rlControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rlControl = findViewById(R.id.rl_control);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTransparentStatusNavigationBar();
            LUIUtil.setMargins(rlControl, 0, 0, 0, DisplayUtil.getNavigationBarHeight(getActivity()));
        } else {
            LUIUtil.setMargins(rlControl, 0, 0, 0, DisplayUtil.getStatusHeight(getActivity()));
        }

        int bkgRootView = getIntent().getIntExtra(Constants.getBKG_ROOT_VIEW(), Constants.getNOT_FOUND());
        LLog.d(getTAG(), "bkgRootView " + bkgRootView);
        if (bkgRootView == Constants.getNOT_FOUND()) {
            getRootView().setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        } else {
            getRootView().setBackgroundResource(bkgRootView);
        }

        final TextView tvSize = findViewById(R.id.tv_size);

        //final ImageView ivBkg1 = (ImageView) findViewById(R.id.iv_bkg_1);
        //final ImageView ivBkg2 = (ImageView) findViewById(R.id.iv_bkg_2);

        final ViewPager viewPager = findViewById(R.id.viewpager);
        final SlidePagerAdapter slidePagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(slidePagerAdapter);
        LUIUtil.setPullLikeIOSHorizontal(viewPager);
        viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());

        final String photoID = getIntent().getStringExtra(Constants.INSTANCE.getSK_PHOTO_ID());
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

        findViewById(R.id.bt_download).setOnClickListener(v -> {
            //LAnimationUtil.play(v, Techniques.Pulse);
            new AsyncTaskDownloadImage(getApplicationContext(), PhotosDataCore.getInstance().getPhoto(viewPager.getCurrentItem()).getUrlO()).execute();
        });
        findViewById(R.id.bt_share).setOnClickListener(v -> {
            //LAnimationUtil.play(v, Techniques.Pulse);
            LSocialUtil.INSTANCE.share(getActivity(), PhotosDataCore.getInstance().getPhoto(viewPager.getCurrentItem()).getUrlO());
        });
        findViewById(R.id.bt_report).setOnClickListener(v -> {
            //LAnimationUtil.play(v, Techniques.Pulse);
            LSocialUtil.INSTANCE.sendEmail(getActivity());
        });
    }

    @Override
    protected boolean setFullScreen() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
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
            final FrmIvSlideCore frmIvSlideCore = new FrmIvSlideCore();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.INSTANCE.getSK_PHOTO_PISITION(), position);
            frmIvSlideCore.setArguments(bundle);
            return frmIvSlideCore;
        }

        @Override
        public int getCount() {
            return PhotosDataCore.getInstance().getSize();
        }
    }

    protected void toggleDisplayRlControl() {
        if (isRlControlShowing) {
            hideRlControl();
        } else {
            showRlControl();
        }
    }

    private boolean isRlControlShowing = true;

    private void showRlControl() {
        if (rlControl == null) {
            return;
        }
        rlControl.setVisibility(View.VISIBLE);
        isRlControlShowing = true;
        LAnimationUtil.INSTANCE.play(rlControl, Techniques.SlideInUp);
    }

    private void hideRlControl() {
        if (rlControl == null) {
            return;
        }
        LAnimationUtil.INSTANCE.play(rlControl, Techniques.SlideOutDown, new LAnimationUtil.Callback() {
            @Override
            public void onCancel() {
            }

            @Override
            public void onEnd() {
                rlControl.setVisibility(View.INVISIBLE);
                isRlControlShowing = false;
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
