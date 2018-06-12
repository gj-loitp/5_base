package vn.loitp.core.loitp.gallery.slide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.photos.PhotosDataCore;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;

public class GalleryCoreSlideActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        LImageUtil.load(activity, Constants.URL_IMG_2, ivBkg);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SlidePagerAdapter());

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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do nothing
            }

            @Override
            public void onPageSelected(int position) {
                LLog.d(TAG, "onPageSelected " + PhotosDataCore.getInstance().getPhoto(position).getUrlO());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return true;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_gallery_core_slide;
    }

    private class SlidePagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            Photo photo = PhotosDataCore.getInstance().getPhoto(position);
            LayoutInflater inflater = LayoutInflater.from(activity);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_photo_slide_iv_core, collection, false);

            ImageView imageView = (ImageView) layout.findViewById(R.id.imageView);
            LImageUtil.load(activity, photo.getUrlO(), imageView);

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
    }
}
