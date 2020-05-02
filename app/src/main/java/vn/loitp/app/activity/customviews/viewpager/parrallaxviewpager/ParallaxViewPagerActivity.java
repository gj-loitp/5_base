package vn.loitp.app.activity.customviews.viewpager.parrallaxviewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.LStoreUtil;
import com.views.viewpager.parrallax.LParallaxViewPager;
import com.views.viewpager.parrallax.ParrallaxMode;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.R;

public class ParallaxViewPagerActivity extends BaseFontActivity {

    private List<Integer> resList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LParallaxViewPager viewPager = findViewById(R.id.viewpager);
        //LUIUtil.setPullLikeIOSVertical(viewPager);
        for (int i = 0; i < 20; i++) {
            resList.add(LStoreUtil.INSTANCE.getRandomColor());
        }

        viewPager.setParrallaxMode(ParrallaxMode.RIGHT_OVERLAY);
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
    protected int setLayoutResourceId() {
        return R.layout.activity_parallax_view_pager;
    }

    private class SlidePagerAdapter extends PagerAdapter {

        @NotNull
        @Override
        public Object instantiateItem(@NotNull ViewGroup collection, int position) {
            Integer res = resList.get(position);
            LLog.d(getTAG(), "res " + res);
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_photo_slide_iv, collection, false);

            ImageView imageView = layout.findViewById(R.id.imageView);
            //imageView.setBackgroundColor(res);
            if (position % 2 == 0) {
                imageView.setImageResource(R.drawable.iv);
            } else {
                imageView.setImageResource(R.drawable.logo);
            }

            TextView tv = layout.findViewById(R.id.textView);
            tv.setText(position + "/" + resList.size());

            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, @NotNull Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return resList.size();
        }

        @Override
        public boolean isViewFromObject(@NotNull View view, @NotNull Object object) {
            return view == object;
        }
    }
}
