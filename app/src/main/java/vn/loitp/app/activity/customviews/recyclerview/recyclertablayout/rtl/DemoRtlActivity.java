package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.rtl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.views.recyclerview.recyclertablayout.RecyclerTabLayout;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.ColorItem;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.Demo;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.DemoColorPagerAdapter;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.basic.DemoBasicActivity;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.utils.DemoData;

public class DemoRtlActivity extends DemoBasicActivity {

    protected RecyclerTabLayout mRecyclerTabLayout;

    public static void startActivity(Context context, Demo demo) {
        Intent intent = new Intent(context, DemoRtlActivity.class);
        intent.putExtra(KEY_DEMO, demo.name());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_tablayout_demo_rtl);

        Demo demo = Demo.valueOf(getIntent().getStringExtra(KEY_DEMO));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(demo.titleResId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<ColorItem> items = DemoData.loadDemoColorItems(this);
        Collections.sort(items, new Comparator<ColorItem>() {
            @Override
            public int compare(ColorItem lhs, ColorItem rhs) {
                return lhs.name.compareTo(rhs.name);
            }
        });

        DemoColorPagerAdapter adapter = new DemoColorPagerAdapter();
        adapter.addAll(items);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        mRecyclerTabLayout = findViewById(R.id.recycler_tab_layout);
        mRecyclerTabLayout.setUpWithViewPager(viewPager);
    }
}