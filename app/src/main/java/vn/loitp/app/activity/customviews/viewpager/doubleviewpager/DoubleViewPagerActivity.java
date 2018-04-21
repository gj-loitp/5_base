package vn.loitp.app.activity.customviews.viewpager.doubleviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;

import com.emoiluj.doubleviewpager.DoubleViewPager;
import com.emoiluj.doubleviewpager.DoubleViewPagerAdapter;

import java.util.ArrayList;

import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;

public class DoubleViewPagerActivity extends BaseActivity {
    private DoubleViewPager viewpager;
    private int horizontalChilds;
    private int verticalChilds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadDataFromSplash();
        loadUI();
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
        return R.layout.activity_double_viewpager;
    }

    private void loadDataFromSplash() {
        horizontalChilds = getIntent().getExtras().getInt("HORIZONTAL");
        verticalChilds = getIntent().getExtras().getInt("VERTICAL");
    }

    private void loadUI() {

        ArrayList<PagerAdapter> verticalAdapters = new ArrayList<PagerAdapter>();
        generateVerticalAdapters(verticalAdapters);

        viewpager = (DoubleViewPager) findViewById(R.id.pager);
        viewpager.setAdapter(new DoubleViewPagerAdapter(getApplicationContext(), verticalAdapters));
    }

    private void generateVerticalAdapters(ArrayList<PagerAdapter> verticalAdapters) {
        for (int i = 0; i < horizontalChilds; i++) {
            verticalAdapters.add(new VerticalPagerAdapter(this, i, verticalChilds));
        }
    }
}
