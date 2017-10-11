package vn.loitp.app.activity.demo.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.utilities.LImageUtil;
import vn.loitp.app.utilities.LLog;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.app.activity.customviews.viewpager.parrallaxviewpager._lib.parrallaxviewpager.Mode;
import vn.loitp.app.activity.customviews.viewpager.parrallaxviewpager._lib.parrallaxviewpager.ParallaxViewPager;
import vn.loitp.flickr.model.photosetgetphotos.Photo;
import vn.loitp.livestar.R;

public class GalleryDemoSlideActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParallaxViewPager viewPager = (ParallaxViewPager) findViewById(R.id.viewpager);
        viewPager.setMode(Mode.RIGHT_OVERLAY);
        viewPager.setAdapter(new SlidePagerAdapter());

        String photoID = getIntent().getStringExtra("photoID");
        int position = PhotosData.getInstance().getPosition(photoID);
        LLog.d(TAG, "position: " + position);
        viewPager.setCurrentItem(position);
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
        return R.layout.activity_gallery_demo_slide;
    }

    private class SlidePagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            Photo photo = PhotosData.getInstance().getPhoto(position);
            LayoutInflater inflater = LayoutInflater.from(activity);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_photo_slide, collection, false);

            ImageView imageView = (ImageView) layout.findViewById(R.id.imageView);
            LImageUtil.load(activity, photo.getUrlO(), imageView, 50, 80);

            TextView tv = (TextView) layout.findViewById(R.id.tv);
            LUIUtil.printBeautyJson(photo, tv);

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
