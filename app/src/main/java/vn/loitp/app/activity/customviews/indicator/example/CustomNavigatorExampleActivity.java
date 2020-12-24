package vn.loitp.app.activity.customviews.indicator.example;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import java.util.Arrays;
import java.util.List;

import vn.loitp.app.R;
import vn.loitp.app.activity.customviews.indicator.ext.navigator.ScaleCircleNavigator;

public class CustomNavigatorExampleActivity extends AppCompatActivity {
    private static final String[] CHANNELS = new String[]{"CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "HONEYCOMB", "ICE_CREAM_SANDWICH", "JELLY_BEAN", "KITKAT", "LOLLIPOP", "M", "NOUGAT"};
    private final List<String> mDataList = Arrays.asList(CHANNELS);
    private final ExamplePagerAdapter mExamplePagerAdapter = new ExamplePagerAdapter(mDataList);

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_navigator_example_layout);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mExamplePagerAdapter);

        initMagicIndicator1();
        initMagicIndicator2();
        initMagicIndicator3();
    }

    private void initMagicIndicator1() {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magicIndicator1);
        CircleNavigator circleNavigator = new CircleNavigator(this);
        circleNavigator.setCircleCount(CHANNELS.length);
        circleNavigator.setCircleColor(Color.RED);
        circleNavigator.setCircleClickListener(index -> mViewPager.setCurrentItem(index));
        magicIndicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    private void initMagicIndicator2() {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magicIndicator2);
        CircleNavigator circleNavigator = new CircleNavigator(this);
        circleNavigator.setFollowTouch(false);
        circleNavigator.setCircleCount(CHANNELS.length);
        circleNavigator.setCircleColor(Color.RED);
        circleNavigator.setCircleClickListener(index -> mViewPager.setCurrentItem(index));
        magicIndicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    private void initMagicIndicator3() {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magicIndicator3);
        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(this);
        scaleCircleNavigator.setCircleCount(CHANNELS.length);
        scaleCircleNavigator.setNormalCircleColor(Color.LTGRAY);
        scaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY);
        scaleCircleNavigator.setCircleClickListener(index -> mViewPager.setCurrentItem(index));
        magicIndicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }
}
