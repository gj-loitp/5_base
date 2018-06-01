package vn.loitp.core.loitp.adhelper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import loitp.core.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.viewpager.parrallaxviewpager.lib.parrallaxviewpager.Mode;
import vn.loitp.views.viewpager.parrallaxviewpager.lib.parrallaxviewpager.ParallaxViewPager;

/**
 * Created by LENOVO on 5/31/2018.
 */

public class AdHelperActivity extends BaseActivity {
    private List<Integer> resList = new ArrayList<>();
    private FloatingActionButton btPrevScreen;
    private FloatingActionButton btNextScreen;
    private TextView tvPage;
    private ParallaxViewPager viewPager;

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
        return R.layout.activity_ad_helper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExist = false;
        btPrevScreen = (FloatingActionButton) findViewById(R.id.bt_prev_screen);
        btNextScreen = (FloatingActionButton) findViewById(R.id.bt_next_screen);
        tvPage = (TextView) findViewById(R.id.tv_page);
        LUIUtil.setTextShadow(tvPage);
        findViewById(R.id.bt_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                LActivityUtil.tranOut(activity);
            }
        });
        btPrevScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
        btNextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == (resList.size() - 1)) {
                    finish();
                    LActivityUtil.tranOut(activity);
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });

        viewPager = (ParallaxViewPager) findViewById(R.id.viewpager);
        for (int i = 0; i < 4; i++) {
            resList.add(LStoreUtil.getRandomColor());
        }

        viewPager.setMode(Mode.RIGHT_OVERLAY);
        viewPager.setAdapter(new SlidePagerAdapter());

        LUIUtil.setPullLikeIOSHorizontal(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do nothing
            }

            @Override
            public void onPageSelected(int position) {
                tvPage.setText((position + 1) + "/" + resList.size());
                if (position == 0) {
                    btPrevScreen.setVisibility(View.INVISIBLE);
                } else {
                    btPrevScreen.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
            }
        });
    }

    private class SlidePagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            Integer res = resList.get(position);
            LayoutInflater inflater = LayoutInflater.from(activity);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_photo_ad_helper, collection, false);

            ImageView imageView = (ImageView) layout.findViewById(R.id.imageView);
            imageView.setImageResource(R.drawable.iv);

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

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }
}
