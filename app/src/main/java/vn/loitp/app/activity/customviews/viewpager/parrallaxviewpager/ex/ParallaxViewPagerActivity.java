package vn.loitp.app.activity.customviews.viewpager.parrallaxviewpager.ex;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.activity.customviews.viewpager.parrallaxviewpager._lib.parrallaxviewpager.Mode;
import vn.loitp.app.activity.customviews.viewpager.parrallaxviewpager._lib.parrallaxviewpager.ParallaxViewPager;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.utilities.LStoreUtil;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.livestar.R;

public class ParallaxViewPagerActivity extends BaseActivity {

    private List<Integer> resList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParallaxViewPager viewPager = (ParallaxViewPager) findViewById(R.id.viewpager);
        LUIUtil.setPullLikeIOSVertical(viewPager);
        for (int i = 0; i < 20; i++) {
            resList.add(LStoreUtil.getRandomColor());
        }

        viewPager.setMode(Mode.RIGHT_OVERLAY);
        viewPager.setAdapter(new SlidePagerAdapter());
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
        return R.layout.activity_parallax_view_pager;
    }

    private class SlidePagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            Integer res = resList.get(position);
            LayoutInflater inflater = LayoutInflater.from(activity);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_photo_slide, collection, false);

            ImageView imageView = (ImageView) layout.findViewById(R.id.imageView);
            imageView.setBackgroundColor(res);

            TextView tv = (TextView) layout.findViewById(R.id.tv);
            tv.setText(position + "/" + resList.size());

            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return resList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
