package vn.loitp.app.activity.demo.film.groupviewpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LDeviceUtil;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.views.LToast;
import vn.loitp.views.viewpager.swipeoutviewpager.SwipeOutViewPager;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmGroupViewPager extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private TextView tv;
    private SwipeOutViewPager viewPager;
    private ViewPagerAdapter adapter;

    private List<Page> pageArrayList = new ArrayList<>();
    private String FRAGMENT_TAG;

    public void setFragmentTag(String frragmentTag) {
        FRAGMENT_TAG = frragmentTag;
    }

    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    public interface Callback {
        public void onClickRemove(String fragmentTag);
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv = (TextView) frmRootView.findViewById(R.id.tv);
        tv.setText(FRAGMENT_TAG);
        viewPager = (SwipeOutViewPager) frmRootView.findViewById(R.id.vp);
        viewPager.setOnSwipeOutListener(new SwipeOutViewPager.OnSwipeOutListener() {
            @Override
            public void onSwipeOutAtStart() {
                LToast.show(getActivity(), "onSwipeOutAtStart");
            }

            @Override
            public void onSwipeOutAtEnd() {
                LToast.show(getActivity(), "onSwipeOutAtEnd");
            }
        });
        //LUIUtil.setPullLikeIOSHorizontal(viewPager);

        //5<=max<=10
        int max = LDeviceUtil.getRandomNumber(5) + 5;
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

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        frmRootView.findViewById(R.id.bt_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClickRemove(FRAGMENT_TAG);
                }
            }
        });
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_group_view_pager;
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
            return pageArrayList.size();
        }
    }
}