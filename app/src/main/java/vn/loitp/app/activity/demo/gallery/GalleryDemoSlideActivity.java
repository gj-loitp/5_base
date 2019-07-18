package vn.loitp.app.activity.demo.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.core.base.BaseFontActivity;
import com.core.utilities.LImageUtil;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;
import com.restapi.flickr.model.photosetgetphotos.Photo;
import com.views.viewpager.parrallaxviewpager.Mode;
import com.views.viewpager.parrallaxviewpager.ParallaxViewPager;

import loitp.basemaster.R;

public class GalleryDemoSlideActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParallaxViewPager viewPager = (ParallaxViewPager) findViewById(R.id.viewpager);
        viewPager.setMode(Mode.RIGHT_OVERLAY);
        viewPager.setAdapter(new SlidePagerAdapter());

        LUIUtil.INSTANCE.setPullLikeIOSVertical(viewPager);

        String photoID = getIntent().getStringExtra("photoID");
        int position = PhotosData.getInstance().getPosition(photoID);
        LLog.INSTANCE.d(getTAG(), "position: " + position);
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
    protected int setLayoutResourceId() {
        return R.layout.activity_gallery_demo_slide;
    }

    private class SlidePagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            Photo photo = PhotosData.getInstance().getPhoto(position);
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_photo_slide_iv, collection, false);

            ImageView imageView = (ImageView) layout.findViewById(R.id.imageView);
            LImageUtil.INSTANCE.load(getActivity(), photo.getUrlO(), imageView, 50, 80);

            TextView tv = (TextView) layout.findViewById(R.id.tv);
            LUIUtil.INSTANCE.printBeautyJson(photo, tv);

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
