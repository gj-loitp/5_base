package vn.loitp.core.loitp.gallery.slide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.photos.PhotosDataCore;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.viewpager.viewpagertransformers.ZoomOutSlideTransformer;

public class GalleryCoreSlideActivity extends BaseFontActivity {
    private ImageView ivBkg;
    private int sizeScreenW;
    private int sizeScreenH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTransparentStatusNavigationBar();

        sizeScreenW = LScreenUtil.getScreenWidth();
        sizeScreenH = LScreenUtil.getScreenHeightIncludeNavigationBar(activity);

        ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        LImageUtil.load(activity, Constants.URL_IMG_2, ivBkg);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SlidePagerAdapter(getSupportFragmentManager()));

        viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());

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
                //LLog.d(TAG, "onPageSelected " + PhotosDataCore.getInstance().getPhoto(position).getUrlO());
                //loadBlurImg(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
            }
        });
    }

    private void loadBlurImg(int position) {
        LImageUtil.load(activity, PhotosDataCore.getInstance().getPhoto(position).getUrlO(), ivBkg, 20, 20);
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

    /*private class SlidePagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            Photo photo = PhotosDataCore.getInstance().getPhoto(position);
            LayoutInflater inflater = LayoutInflater.from(activity);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_photo_slide_iv_core, collection, false);

            ImageView imageView = (ImageView) layout.findViewById(R.id.imageView);
            LImageUtil.load(activity, photo.getUrlO(), imageView, new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    LLog.d(TAG, "onLoadFailed");
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    LLog.d(TAG, "onResourceReady");
                    return false;
                }
            });

            LImageUtil.load(activity, photo.getUrlO(), imageView, new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    LLog.d(TAG, "onLoadFailed");
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    LLog.d(TAG, "onResourceReady");
                    return false;
                }
            });

            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return PhotosDataCore.getInstance().getSize();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }*/

    private class SlidePagerAdapter extends FragmentStatePagerAdapter {

        SlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            FrmIvSlideCore frmIvSlideCore = new FrmIvSlideCore();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.SK_PHOTO_PISITION, position);
            return frmIvSlideCore;
        }

        @Override
        public int getCount() {
            return PhotosDataCore.getInstance().getSize();
        }
    }
}
