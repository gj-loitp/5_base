package vn.loitp.app.activity.animation.lottie;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LUIUtil;

//https://www.lottiefiles.com/?page=1
public class MenuLottieActivity extends BaseFontActivity {
    private List<LottieItem> lottieItemList = new ArrayList<>();
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new SlidePagerAdapter());
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        LUIUtil.changeTabsFont(tabLayout, vn.loitp.core.common.Constants.FONT_PATH);
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
            LayoutInflater inflater = LayoutInflater.from(activity);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_lottie_view, collection, false);
            RelativeLayout rl = (RelativeLayout) layout.findViewById(R.id.rl);
            LottieAnimationView lottieAnimationView = (LottieAnimationView) layout.findViewById(R.id.animation_view);

            lottieAnimationView.setAnimation(lottieItem.getPathAsset());
            lottieAnimationView.useHardwareAcceleration();
            //lottieAnimationView.setScale(0.3f);

            //holder.lottieAnimationView.playAnimation();
            //holder.lottieAnimationView.loop(true);

            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lottieAnimationView.playAnimation();
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
            return lottieItemList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position + " - " + lottieItemList.get(position).getName();
        }
    }
}
