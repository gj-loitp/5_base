package vn.loitp.app.activity.customviews.actionbar.collapsingtoolbarlayoutwithtablayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import loitp.basemaster.R;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LPopupMenu;
import vn.loitp.views.LToast;

public class CollapsingToolbarWithTabLayoutActivity extends BaseFontActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCustomStatusBar(Color.TRANSPARENT, ContextCompat.getColor(activity, R.color.colorPrimary));

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        /*CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.list_comic));
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(activity, R.color.White));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(activity, R.color.White));*/

        /*LAppBarLayout appBarLayout = (LAppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.setOnStateChangeListener(new LAppBarLayout.OnStateChangeListener() {
            @Override
            public void onStateChange(LAppBarLayout.State toolbarChange) {
                //LLog.d(TAG, "toolbarChange: " + toolbarChange);
                if (toolbarChange.equals(LAppBarLayout.State.COLLAPSED)) {
                    //COLLAPSED appBarLayout min
                    LLog.d(TAG, "COLLAPSED toolbarChange: " + toolbarChange);
                } else if (toolbarChange.equals(LAppBarLayout.State.EXPANDED)) {
                    //EXPANDED appBarLayout max
                    LLog.d(TAG, "EXPANDED toolbarChange: " + toolbarChange);
                } else {
                    //IDLE appBarLayout not min not max
                    LLog.d(TAG, "IDLE toolbarChange: " + toolbarChange);
                }
            }
        });*/

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        //findViewById(R.id.bt_menu).setOnClickListener(this);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        changeTabsFont(tabLayout, vn.loitp.core.common.Constants.INSTANCE.getFONT_PATH());
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
        return R.layout.activity_collapsingtoolbar_withtablayout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.bt_menu:
                LPopupMenu.show(activity, v, R.menu.menu_popup, menuItem -> LToast.show(activity, menuItem.getTitle().toString()));
                break;
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            final PlaceholderFragment fragment = new PlaceholderFragment();
            final Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.frm_text, container, false);
            final TextView textView = rootView.findViewById(R.id.tv);
            textView.setText(R.string.large_text);
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    private void changeTabsFont(TabLayout tabLayout, String fontName) {
        final ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        final int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            final ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            final int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                final View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    CalligraphyUtils.applyFontToTextView(tabLayout.getContext(), (TextView) tabViewChild, fontName);
                }
            }
        }
    }
}
