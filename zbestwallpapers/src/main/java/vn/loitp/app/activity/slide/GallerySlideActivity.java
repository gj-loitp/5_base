package vn.loitp.app.activity.slide;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.app.model.PhotosData;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

public class GallerySlideActivity extends BaseActivity {
    private ImageView ivBkg0;
    private ImageView ivBkg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        ivBkg0 = (ImageView) findViewById(R.id.iv_bkg_0);
        ivBkg1 = (ImageView) findViewById(R.id.iv_bkg_1);
        viewPager.setAdapter(new SlidePagerAdapter());
        viewPager.setOffscreenPageLimit(3);
        LUIUtil.setPullLikeIOSVertical(viewPager);

        String photoID = getIntent().getStringExtra(Constants.PHOTO_ID);
        int position = PhotosData.getInstance().getPosition(photoID);
        //LLog.d(TAG, "position: " + position);
        viewPager.setCurrentItem(position);

        loadBlurBackground(position, null);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do nothing
            }

            @Override
            public void onPageSelected(int position) {
                Photo photo = PhotosData.getInstance().getPhoto(position);
                //LImageUtil.load(activity, photo.getUrlO(), ivBkg0, 30, 60);
                //LImageUtil.loadNoEffect(activity, photo.getUrlO(), oldImage, ivBkg);

                loadBlurBackground(position, photo);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
            }
        });
    }

    private void loadBlurBackground(int position, Photo photo) {
        //load blur bkg fist
        if (photo == null) {
            photo = PhotosData.getInstance().getPhoto(position);
        }
        if (position % 2 == 0) {
            LImageUtil.load(activity, photo.getUrlO(), ivBkg0, new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    ivBkg0.setVisibility(View.VISIBLE);
                    ivBkg1.setVisibility(View.GONE);
                    return false;
                }
            });
        } else {
            LImageUtil.load(activity, photo.getUrlO(), ivBkg1, new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    ivBkg0.setVisibility(View.GONE);
                    ivBkg1.setVisibility(View.VISIBLE);
                    return false;
                }
            });
        }
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
        return R.layout.activity_gallery_slide;
    }

    private class SlidePagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            Photo photo = PhotosData.getInstance().getPhoto(position);
            LayoutInflater inflater = LayoutInflater.from(activity);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_photo_slide_iv, collection, false);

            //RelativeLayout rootView = (RelativeLayout) layout.findViewById(R.id.root_view);
            //rootView.setBackgroundColor(AppUtil.getColor(activity));

            AVLoadingIndicatorView avLoadingIndicatorView = (AVLoadingIndicatorView) layout.findViewById(R.id.avi);
            ImageView imageView = (ImageView) layout.findViewById(R.id.imageView);
            LImageUtil.load(activity, photo.getUrlO(), imageView, avLoadingIndicatorView);

            TextView tv = (TextView) layout.findViewById(R.id.tv);
            //LUIUtil.printBeautyJson(photo, tv);
            tv.setText("Original size: " + photo.getWidthO() + "x" + photo.getHeightO());
            LUIUtil.setTextShadow(tv);

            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return PhotosData.getInstance().getSize();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
