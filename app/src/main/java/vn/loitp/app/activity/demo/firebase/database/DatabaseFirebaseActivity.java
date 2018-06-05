package vn.loitp.app.activity.demo.firebase.database;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.firebase.database.fragment.DatabaseFirebaseMyPostsFragmentDatabaseFirebase;
import vn.loitp.app.activity.demo.firebase.database.fragment.DatabaseFirebaseMyTopPostsFragmentDatabaseFirebase;
import vn.loitp.app.activity.demo.firebase.database.fragment.DatabaseFirebaseRecentPostsFragmentDatabaseFirebase;
import vn.loitp.core.utilities.LActivityUtil;

//https://github.com/firebase/quickstart-android
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
        findViewById(R.id.fab_new_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, DatabaseFirebaseNewPostActivity.class));
                LActivityUtil.tranIn(activity);
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
        return R.layout.activity_database_firebase;
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
