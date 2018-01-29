package vn.loitp.app.activity.comicinfo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.comicinfo.chap.ComicInfoData;
import vn.loitp.app.activity.comicinfo.chap.FrmChap;
import vn.loitp.app.activity.comicinfo.info.FrmInfo;
import vn.loitp.app.app.LSApplication;
import vn.loitp.app.common.Constants;
import vn.loitp.app.helper.chaplist.GetChapTask;
import vn.loitp.app.model.chap.TTTChap;
import vn.loitp.app.model.comic.Comic;
import vn.loitp.app.util.AppUtil;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

public class ComicInfoActivity extends BaseActivity {
    private ImageView toolbarImage;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton btFav;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private List<String> stringList = new ArrayList<>();
    private GetChapTask getChapTask;
    private AVLoadingIndicatorView avi;

    private Comic comic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        comic = (Comic) getIntent().getSerializableExtra(Constants.KEY_COMIC);
        if (comic == null) {
            showDialogError(getString(R.string.cannot_read_this_manga));
            return;
        }
        LLog.d(TAG, "comic: " + LSApplication.getInstance().getGson().toJson(comic));

        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        toolbarImage = (ImageView) findViewById(R.id.toolbar_image);

        if (comic.getUrlImg() == null) {
            LImageUtil.load(activity, AppUtil.getRandomUrl(), toolbarImage);
        } else {
            LImageUtil.load(activity, comic.getUrlImg(), toolbarImage);
        }

        setCustomStatusBar(Color.TRANSPARENT, ContextCompat.getColor(activity, R.color.colorPrimary));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btFav = (FloatingActionButton) findViewById(R.id.bt_fav);
        btFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSelect();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        if (comic.getTitle() != null) {
            collapsingToolbarLayout.setTitle(comic.getTitle());
        }
        /*collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(activity, R.color.White));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(activity, R.color.White));*/

        /*LAppBarLayout appBarLayout = (LAppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.setOnStateChangeListener(new LAppBarLayout.OnStateChangeListener() {
            @Override
            public void onStateChange(LAppBarLayout.State toolbarChange) {
                //LLog.d(TAG, "toolbarChange: " + toolbarChange);
                if (toolbarChange.equals(LAppBarLayout.State.COLLAPSED)) {
                    //COLLAPSED appBarLayout min
                    //LLog.d(TAG, "COLLAPSED toolbarChange: " + toolbarChange);
                } else if (toolbarChange.equals(LAppBarLayout.State.EXPANDED)) {
                    //EXPANDED appBarLayout max
                    //LLog.d(TAG, "EXPANDED toolbarChange: " + toolbarChange);
                } else {
                    //IDLE appBarLayout not min not max
                    //LLog.d(TAG, "IDLE toolbarChange: " + toolbarChange);
                }
            }
        });*/

        stringList.add(getString(R.string.list));
        stringList.add(getString(R.string.comic_info));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        String urlComic = comic.getUrl();
        getChapTask = new GetChapTask(activity, urlComic, new GetChapTask.Callback() {
            @Override
            public void onSuccess(TTTChap tttChap) {
                LLog.d(TAG, "onSuccess " + LSApplication.getInstance().getGson().toJson(tttChap));
                if (tttChap == null) {
                    showDialogError(getString(R.string.err_unknow));
                } else {
                    ComicInfoData.getInstance().setTttChap(tttChap);

                    //update ui
                    btFav.setVisibility(View.VISIBLE);
                    LAnimationUtil.play(btFav, Techniques.SlideInUp);
                    adapter = new ViewPagerAdapter(getSupportFragmentManager());
                    viewPager.setAdapter(adapter);
                    LUIUtil.setPullLikeIOSHorizontal(viewPager);
                }
                avi.smoothToHide();
            }

            @Override
            public void onError() {
                avi.smoothToHide();
                showDialogError(getString(R.string.err_unknow));
            }
        });
        getChapTask.execute();
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_comic_info;
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FrmChap();
                case 1:
                    return new FrmInfo();
            }
            return null;
        }

        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return stringList.get(position);
        }
    }

    private void showDialogSelect() {
        //TODO
    }

    @Override
    protected void onDestroy() {
        if (getChapTask != null) {
            getChapTask.cancel(true);
        }
        ComicInfoData.getInstance().clearAll();
        super.onDestroy();
    }
}
