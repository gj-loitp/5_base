package vn.loitp.app.activity.customviews.viewpager.viewpagerwithtablayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.core.base.BaseFontActivity;
import com.core.utilities.LDialogUtil;
import com.core.utilities.LLog;
import com.core.utilities.LStoreUtil;
import com.core.utilities.LUIUtil;
import com.google.android.material.tabs.TabLayout;
import com.views.LToast;
import com.views.viewpager.viewpagertransformers.AccordionTransformer;
import com.views.viewpager.viewpagertransformers.BackgroundToForegroundTransformer;
import com.views.viewpager.viewpagertransformers.CubeInTransformer;
import com.views.viewpager.viewpagertransformers.CubeOutTransformer;
import com.views.viewpager.viewpagertransformers.DefaultTransformer;
import com.views.viewpager.viewpagertransformers.DepthPageTransformer;
import com.views.viewpager.viewpagertransformers.DrawFromBackTransformer;
import com.views.viewpager.viewpagertransformers.FlipHorizontalTransformer;
import com.views.viewpager.viewpagertransformers.FlipVerticalTransformer;
import com.views.viewpager.viewpagertransformers.ForegroundToBackgroundTransformer;
import com.views.viewpager.viewpagertransformers.RotateDownTransformer;
import com.views.viewpager.viewpagertransformers.RotateUpTransformer;
import com.views.viewpager.viewpagertransformers.StackTransformer;
import com.views.viewpager.viewpagertransformers.TabletTransformer;
import com.views.viewpager.viewpagertransformers.ZoomInTransformer;
import com.views.viewpager.viewpagertransformers.ZoomOutSlideTransformer;
import com.views.viewpager.viewpagertransformers.ZoomOutTranformer;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;

//https://github.com/geftimov/android-viewpager-transformers
public class ViewPagerWithTabLayoutActivity extends BaseFontActivity {
    private List<Integer> resList = new ArrayList<>();
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        //LUIUtil.setPullLikeIOSVertical(viewPager);
        for (int i = 0; i < 20; i++) {
            resList.add(LStoreUtil.getRandomColor());
        }
        viewPager.setAdapter(new SlidePagerAdapter());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        LUIUtil.INSTANCE.changeTabsFont(tabLayout, com.core.common.Constants.INSTANCE.getFONT_PATH());

        findViewById(R.id.bt_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAnim();
            }
        });
    }

    private final String AccordionTransformer = "AccordionTransformer";
    private final String BackgroundToForegroundTransformer = "BackgroundToForegroundTransformer";
    private final String CubeInTransformer = "CubeInTransformer";
    private final String CubeOutTransformer = "CubeOutTransformer";
    private final String DefaultTransformer = "DefaultTransformer";
    private final String DepthPageTransformer = "DepthPageTransformer";
    private final String DrawFromBackTransformer = "DrawFromBackTransformer";
    private final String FlipHorizontalTransformer = "FlipHorizontalTransformer";
    private final String FlipVerticalTransformer = "FlipVerticalTransformer";
    private final String ForegroundToBackgroundTransformer = "ForegroundToBackgroundTransformer";
    private final String ParallaxPageTransformer = "ParallaxPageTransformer";
    private final String RotateUpTransformer = "RotateUpTransformer";
    private final String RotateDownTransformer = "RotateDownTransformer";
    private final String StackTransformer = "StackTransformer";
    private final String TabletTransformer = "TabletTransformer";
    private final String ZoomInTransformer = "ZoomInTransformer";
    private final String ZoomOutSlideTransformer = "ZoomOutSlideTransformer";
    private final String ZoomOutTranformer = "ZoomOutTranformer";

    private void showDialogAnim() {
        List<String> stringList = new ArrayList<>();
        stringList.add(AccordionTransformer);
        stringList.add(BackgroundToForegroundTransformer);
        stringList.add(CubeInTransformer);
        stringList.add(CubeOutTransformer);
        stringList.add(DefaultTransformer);
        stringList.add(DepthPageTransformer);
        stringList.add(DrawFromBackTransformer);
        stringList.add(FlipHorizontalTransformer);
        stringList.add(FlipVerticalTransformer);
        stringList.add(ForegroundToBackgroundTransformer);
        //stringList.add(ParallaxPageTransformer);
        stringList.add(RotateDownTransformer);
        stringList.add(RotateUpTransformer);
        stringList.add(StackTransformer);
        stringList.add(TabletTransformer);
        stringList.add(ZoomInTransformer);
        stringList.add(ZoomOutSlideTransformer);
        stringList.add(ZoomOutTranformer);
        String[] arr = stringList.toArray(new String[stringList.size()]);
        LDialogUtil.INSTANCE.showDialogList(getActivity(), "Select", arr, new LDialogUtil.CallbackList() {
            @Override
            public void onClick(int position) {
                LToast.INSTANCE.show(getActivity(), "Click position " + position + ", item: " + arr[position]);
                switch (stringList.get(position)) {
                    case AccordionTransformer:
                        viewPager.setPageTransformer(true, new AccordionTransformer());
                        break;
                    case BackgroundToForegroundTransformer:
                        viewPager.setPageTransformer(true, new BackgroundToForegroundTransformer());
                        break;
                    case CubeInTransformer:
                        viewPager.setPageTransformer(true, new CubeInTransformer());
                        break;
                    case CubeOutTransformer:
                        viewPager.setPageTransformer(true, new CubeOutTransformer());
                        break;
                    case DefaultTransformer:
                        viewPager.setPageTransformer(true, new DefaultTransformer());
                        break;
                    case DepthPageTransformer:
                        viewPager.setPageTransformer(true, new DepthPageTransformer());
                        break;
                    case DrawFromBackTransformer:
                        viewPager.setPageTransformer(true, new DrawFromBackTransformer());
                        break;
                    case FlipHorizontalTransformer:
                        viewPager.setPageTransformer(true, new FlipHorizontalTransformer());
                        break;
                    case FlipVerticalTransformer:
                        viewPager.setPageTransformer(true, new FlipVerticalTransformer());
                        break;
                    case ForegroundToBackgroundTransformer:
                        viewPager.setPageTransformer(true, new ForegroundToBackgroundTransformer());
                        break;
                    /*case ParallaxPageTransformer:
                        viewPager.setPageTransformer(true, new ParallaxPageTransformer());
                        break;*/
                    case RotateDownTransformer:
                        viewPager.setPageTransformer(true, new RotateDownTransformer());
                        break;
                    case RotateUpTransformer:
                        viewPager.setPageTransformer(true, new RotateUpTransformer());
                        break;
                    case StackTransformer:
                        viewPager.setPageTransformer(true, new StackTransformer());
                        break;
                    case TabletTransformer:
                        viewPager.setPageTransformer(true, new TabletTransformer());
                        break;
                    case ZoomInTransformer:
                        viewPager.setPageTransformer(true, new ZoomInTransformer());
                        break;
                    case ZoomOutSlideTransformer:
                        viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
                        break;
                    case ZoomOutTranformer:
                        viewPager.setPageTransformer(true, new ZoomOutTranformer());
                        break;
                }
            }
        });
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
        return R.layout.activity_viewpager_with_tablayout;
    }

    private class SlidePagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            Integer res = resList.get(position);
            LLog.INSTANCE.d(getTAG(), "res " + res);
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_photo_slide_iv, collection, false);

            ImageView imageView = (ImageView) layout.findViewById(R.id.imageView);
            //imageView.setBackgroundColor(res);
            if (position % 2 == 0) {
                imageView.setImageResource(R.drawable.iv);
            } else {
                imageView.setImageResource(R.drawable.logo);
            }

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

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page Title " + position;
        }
    }
}
