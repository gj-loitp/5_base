package vn.loitp.app.activity.demo.film.group0;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LDeviceUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LToast;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmGroup0 extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private List<Page> pageArrayList = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) frmRootView.findViewById(R.id.viewpager);

        int max = LDeviceUtil.getRandomNumber(10);
        for (int i = 0; i < max; i++) {
            Page page = new Page();
            page.setColor(LStoreUtil.getRandomColor());
            page.setName("Loitp " + i + "/" + (max - 1));
            if (i % 2 == 0) {
                page.setUrlImg(vn.loitp.core.common.Constants.URL_IMG_1);
            } else {
                page.setUrlImg(vn.loitp.core.common.Constants.URL_IMG_2);
            }
            pageArrayList.add(page);
        }

        LUIUtil.setPullLikeIOSHorizontal(viewPager, new LUIUtil.Callback() {
            @Override
            public void onUpOrLeft(float offset) {
                LLog.d(TAG, "onUpOrLeft " + offset);
                LToast.show(getActivity(), "Detect Left");
            }

            @Override
            public void onUpOrLeftRefresh(float offset) {
                LLog.d(TAG, "onUpOrLeftRefresh " + offset);
            }

            @Override
            public void onDownOrRight(float offset) {
                LLog.d(TAG, "onDownOrRight " + offset);
                LToast.show(getActivity(), "Detect Right");
            }

            @Override
            public void onDownOrRightRefresh(float offset) {
                LLog.d(TAG, "onDownOrRightRefresh " + offset);
            }
        });

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_group_0;
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(FrmPage.BUNDLE_PAGE, pageArrayList.get(position));
            BaseFragment fragment = new FrmPage();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}