package vn.puresolutions.livestar.adapter.tab;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

/**
 * @author hoangphu
 * @since 11/27/16
 */

public class TabLayoutAdapterBuilder {
    private TabAdapter tabAdapter;
    private Context context;

    public TabLayoutAdapterBuilder(Context context) {
        this.context = context;
    }

    public TabLayoutAdapterBuilder setAdapter(TabAdapter adapter) {
        this.tabAdapter = adapter;
        return this;
    }

    public FragmentStatePagerItemAdapter build(FragmentManager fragmentManager) {
        return new FragmentStatePagerItemAdapter(fragmentManager, createPage());
    }

    /**
     * Private method
     */
    private FragmentPagerItems createPage() {
        FragmentPagerItems fragmentPagerItems = new FragmentPagerItems(context);
        for (int i = 0; i < tabAdapter.getCount(); i++) {
            if (tabAdapter.getArgument(i) != null) {
                fragmentPagerItems.add(FragmentPagerItem.of(tabAdapter.getTitle(context, i).toUpperCase(),
                        tabAdapter.getFragmentClass(i), tabAdapter.getArgument(i)));
            } else {
                fragmentPagerItems.add(FragmentPagerItem.of(tabAdapter.getTitle(context, i).toUpperCase(),
                        tabAdapter.getFragmentClass(i)));
            }
        }
        return fragmentPagerItems;
    }
}
