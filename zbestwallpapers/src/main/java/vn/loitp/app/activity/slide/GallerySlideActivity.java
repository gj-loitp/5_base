package vn.loitp.app.activity.slide;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.app.model.PhotosData;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

public class GallerySlideActivity extends BaseActivity {
    private ImageView ivBkg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        viewPager.setAdapter(new SlidePagerAdapter());
        viewPager.setOffscreenPageLimit(3);
        LUIUtil.setPullLikeIOSVertical(viewPager);

        String photoID = getIntent().getStringExtra(Constants.PHOTO_ID);
        int position = PhotosData.getInstance().getPosition(photoID);
        //LLog.d(TAG, "position: " + position);
        viewPager.setCurrentItem(position);

        //load blur bkg fist
        Photo photo = PhotosData.getInstance().getPhoto(position);
        LImageUtil.load(activity, photo.getUrlO(), ivBkg);

        /*viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do nothing
            }

            @Override
            public void onPageSelected(int position) {
                Photo photo = PhotosData.getInstance().getPhoto(position);
                LImageUtil.loadImage(ivBkg, photo.getUrlO());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
            }
        });*/
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

            //TextView tv = (TextView) layout.findViewById(R.id.tv);
            //LUIUtil.printBeautyJson(photo, tv);

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
