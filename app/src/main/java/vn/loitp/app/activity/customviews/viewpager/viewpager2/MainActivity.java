package vn.loitp.app.activity.customviews.viewpager.viewpager2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import vn.loitp.app.R;

public class MainActivity extends AppCompatActivity {

    public static final String[] tabName = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

    private TabLayout tabLayout;
    private ViewPager2 pager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabs);
        pager2 = findViewById(R.id.view_pager);
        pager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        setUpViewPager();


    }

    private void setUpViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        pager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, pager2, (tab, position) -> tab.setText(tabName[position])).attach();
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {


        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return TabFragment.getInstance(position + 1);
        }

        @Override
        public int getItemCount() {
            return tabName.length;
        }
    }
}
