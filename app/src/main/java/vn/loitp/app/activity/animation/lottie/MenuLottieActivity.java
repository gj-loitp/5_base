package vn.loitp.app.activity.animation.lottie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.core.base.BaseFontActivity;
import com.core.utilities.LUIUtil;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.R;

//https://www.lottiefiles.com/?page=1
public class MenuLottieActivity extends BaseFontActivity {
    private List<LottieItem> lottieItemList = new ArrayList<>();
    private ViewPager viewPager;
    private SeekBar sb;
    private SlidePagerAdapter slidePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = findViewById(R.id.viewPager);
        sb = findViewById(R.id.sb);
        slidePagerAdapter = new SlidePagerAdapter();
        viewPager.setAdapter(slidePagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        LUIUtil.INSTANCE.changeTabsFont(tabLayout, com.core.common.Constants.INSTANCE.getFONT_PATH());
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                viewPager.setCurrentItem(seekBar.getProgress());
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                sb.setProgress(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        prepareData();
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
        return R.layout.activity_menu_lottie;
    }

    private void prepareData() {
        getListAssetFiles("lottie");
        for (int i = 0; i < stringList.size(); i++) {
            lottieItemList.add(new LottieItem(i + " - " + stringList.get(i), "lottie/" + stringList.get(i)));
        }
        sb.setMax(lottieItemList.size());
        viewPager.getAdapter().notifyDataSetChanged();
    }

    private List<String> stringList = new ArrayList<>();

    private boolean getListAssetFiles(String path) {
        String[] list;
        try {
            list = getAssets().list(path);
            if (list.length > 0) {
                // This is a folder
                for (String file : list) {
                    if (!getListAssetFiles(path + "/" + file))
                        return false;
                    else {
                        // This is a file
                        stringList.add(file);
                    }
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private class SlidePagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            LottieItem lottieItem = lottieItemList.get(position);
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_lottie_view, collection, false);
            RelativeLayout rl = layout.findViewById(R.id.rl);
            LottieAnimationView lottieAnimationView = layout.findViewById(R.id.animation_view);

            lottieAnimationView.setAnimation(lottieItem.getPathAsset());
            //lottieAnimationView.useHardwareAcceleration();
            //lottieAnimationView.setScale(0.3f);

            //lottieAnimationView.playAnimation();
            lottieAnimationView.loop(true);

            rl.setOnClickListener(view -> {
                lottieAnimationView.playAnimation();
                //lottieAnimationView.loop(true);
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
            return lottieItemList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return lottieItemList.get(position).getName();
        }
    }
}
