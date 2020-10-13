package vn.loitp.app.activity.demo.firebase.database;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.annotation.IsFullScreen;
import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.utilities.LActivityUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import vn.loitp.app.R;
import vn.loitp.app.activity.demo.firebase.database.fragment.DatabaseFirebaseMyPostsFragmentDatabaseFirebase;
import vn.loitp.app.activity.demo.firebase.database.fragment.DatabaseFirebaseMyTopPostsFragmentDatabaseFirebase;
import vn.loitp.app.activity.demo.firebase.database.fragment.DatabaseFirebaseRecentPostsFragmentDatabaseFirebase;

//https://github.com/firebase/quickstart-android

@LayoutId(R.layout.activity_database_firebase)
@LogTag("DatabaseFirebaseActivity")
@IsFullScreen(false)
public class DatabaseFirebaseActivity extends BaseFirebaseActivity {
    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[]{
                    new DatabaseFirebaseRecentPostsFragmentDatabaseFirebase(),
                    new DatabaseFirebaseMyPostsFragmentDatabaseFirebase(),
                    new DatabaseFirebaseMyTopPostsFragmentDatabaseFirebase(),
            };
            private final String[] mFragmentNames = new String[]{
                    getString(R.string.heading_recent),
                    getString(R.string.heading_my_posts),
                    getString(R.string.heading_my_top_posts)
            };

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Button launches DatabaseFirebaseNewPostActivity
        findViewById(R.id.fabNewPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DatabaseFirebaseActivity.this, DatabaseFirebaseNewPostActivity.class));
                LActivityUtil.tranIn(DatabaseFirebaseActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, DatabaseFirebaseSignInActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
