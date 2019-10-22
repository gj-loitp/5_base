package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.customview02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.views.recyclerview.recyclertablayout.RecyclerTabLayout;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.Demo;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.DemoImagePagerAdapter;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.utils.DemoData;

public class DemoCustomView02Activity extends AppCompatActivity {

    private static final String KEY_DEMO = "demo";

    public static void startActivity(Context context, Demo demo) {
        Intent intent = new Intent(context, DemoCustomView02Activity.class);
        intent.putExtra(KEY_DEMO, demo.name());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_tablayout_demo_custom_view02);

        Demo demo = Demo.valueOf(getIntent().getStringExtra(KEY_DEMO));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(demo.titleResId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DemoImagePagerAdapter adapter = new DemoImagePagerAdapter();
        adapter.addAll(DemoData.loadImageResourceList());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        RecyclerTabLayout recyclerTabLayout = findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithAdapter(new DemoCustomView02Adapter(viewPager));
        recyclerTabLayout.setPositionThreshold(0.5f);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}