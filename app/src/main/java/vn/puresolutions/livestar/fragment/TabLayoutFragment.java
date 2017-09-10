package vn.puresolutions.livestar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.tab.TabAdapter;
import vn.puresolutions.livestar.adapter.tab.TabLayoutAdapterBuilder;
import vn.puresolutions.livestarv3.base.BaseFragment;

/**
 * @author hoangphu
 * @since 11/27/16
 */

public abstract class TabLayoutFragment extends BaseFragment {
    @BindView(R.id.tabFragment_viewPager)
    protected ViewPager viewPager;
    @BindView(R.id.tabFragment_tabView)
    protected SmartTabLayout smartTabLayout;
    @BindView(R.id.tabFragment_pgbLoading)
    protected ProgressBar progressBar;

    protected TabAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
        smartTabLayout.setViewPager(viewPager);
    }

    public void showTabView() {
        smartTabLayout.setVisibility(View.VISIBLE);
    }

    public void hideTabView() {
        smartTabLayout.setVisibility(View.GONE);
    }

    protected void initView() {
        adapter = getAdapter();
        if (adapter != null) {
            FragmentStatePagerItemAdapter fragmentStatePagerItemAdapter = new TabLayoutAdapterBuilder(getActivity())
                    .setAdapter(adapter)
                    .build(getChildFragmentManager());
            viewPager.setAdapter(fragmentStatePagerItemAdapter);
        }
        smartTabLayout.setCustomTabView(R.layout.tab_view, R.id.ls_tabView_tvTitle);
    }

    public void refresh() {
        initView();
        smartTabLayout.setViewPager(viewPager);
    }

    protected int getCurrentIndex() {
        return viewPager.getCurrentItem();
    }

    public abstract TabAdapter getAdapter();
}
