package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.years;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.views.recyclerview.recyclertablayout.RecyclerTabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.Demo;

public class DemoYearsActivity extends AppCompatActivity {

    protected static final String KEY_DEMO = "demo";

    public static void startActivity(Context context, Demo demo) {
        Intent intent = new Intent(context, DemoYearsActivity.class);
        intent.putExtra(KEY_DEMO, demo.name());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler_tablayout_demo_basic);

        Demo demo = Demo.valueOf(getIntent().getStringExtra(KEY_DEMO));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(demo.titleResId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int startYear = 1900;
        int endYear = 3000;
        int initialYear = Calendar.getInstance().get(Calendar.YEAR);

        List<String> items = new ArrayList<>();
        for (int i = startYear; i <= endYear; i++) {
            items.add(String.valueOf(i));
        }

        DemoYearsPagerAdapter adapter = new DemoYearsPagerAdapter();
        adapter.addAll(items);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(initialYear - startYear);

        RecyclerTabLayout recyclerTabLayout = findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}