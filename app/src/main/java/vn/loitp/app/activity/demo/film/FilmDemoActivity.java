package vn.loitp.app.activity.demo.film;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.film.groupviewpager.FrmGroupViewPager;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LToast;

public class FilmDemoActivity extends BaseFontActivity implements View.OnClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout llBaseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        llBaseView = (LinearLayout) findViewById(R.id.ll_base_view);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout);

        findViewById(R.id.bt_clear_all).setOnClickListener(this);
        findViewById(R.id.bt_add_group_0).setOnClickListener(this);
        findViewById(R.id.bt_remove_group_0).setOnClickListener(this);
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_film_demo;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_clear_all:
                clearAllFrm();
                break;
            case R.id.bt_add_group_0:
                add();
                break;
            case R.id.bt_remove_group_0:
                //remove with TAG0
                removeFragmentByTag("TAG0");
                break;
        }
    }

    private void refresh() {
        LUIUtil.setDelay(3000, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                swipeRefreshLayout.setRefreshing(false);
                LToast.show(activity, "Finish refresh -> clear all views");
                clearAllFrm();
            }
        });
    }

    private void clearAllFrm() {
        LScreenUtil.removeAllFragments(activity);
    }

    private void add() {
        FrmGroupViewPager frmGroupViewPager = new FrmGroupViewPager();
        frmGroupViewPager.setFragmentTag(genTagFrm());
        frmGroupViewPager.setCallback(new FrmGroupViewPager.Callback() {
            @Override
            public void onClickRemove(String fragmentTag) {
                removeFragmentByTag(fragmentTag);
            }
        });
        LScreenUtil.addFragment(activity, llBaseView.getId(), frmGroupViewPager, frmGroupViewPager.getFragmentTag(), false);
    }

    private void removeFragmentByTag(String tag) {
        LScreenUtil.removeFragmentByTag(activity, tag);
    }

    private String genTagFrm() {
        int childCount = llBaseView.getChildCount();
        String tag = "TAG" + childCount;
        LLog.d(TAG, "genTagFrm " + tag);
        return tag;
    }
}
