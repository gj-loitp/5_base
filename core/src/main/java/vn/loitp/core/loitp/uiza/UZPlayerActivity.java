package vn.loitp.core.loitp.uiza;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.facebookcomment.FrmFBComment;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.data.EventBusData;
import vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata.Data;
import vn.loitp.views.uzvideo.UZVideo;

//custom UI exo_playback_control_view.xml
public class UZPlayerActivity extends BaseFontActivity implements UZVideo.UZCallback {
    private UZVideo uzVideo;
    private Data data;
    private AdView adView;
    private LinearLayout lnAdview;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAnimWhenExit = false;
        data = (Data) getIntent().getSerializableExtra(UZCons.ENTITY_DATA);
        if (data == null) {
            showDialogError(getString(R.string.video_not_found));
            return;
        }
        String entityId = data.getId();
        if (entityId == null || entityId.isEmpty()) {
            showDialogError(getString(R.string.video_not_found));
            return;
        }
        ImageView ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        LImageUtil.load(activity, data.getThumbnail(), ivBkg, R.drawable.bkg_black_colorprimary);
        uzVideo = (UZVideo) findViewById(R.id.uz_video);
        uzVideo.getRlRootView().setBackgroundColor(Color.TRANSPARENT);
        uzVideo.setTvTitle(data.getName() + "");
        uzVideo.playEntity(entityId);
        uzVideo.setUzCallback(this);

        final String adUnitId = getIntent().getStringExtra(Constants.AD_UNIT_ID_BANNER);
        LLog.d(TAG, "adUnitId " + adUnitId);
        lnAdview = (LinearLayout) findViewById(loitp.core.R.id.ln_adview);
        if (adUnitId == null || adUnitId.isEmpty()) {
            lnAdview.setVisibility(View.GONE);
        } else {
            adView = new AdView(activity);
            adView.setAdSize(AdSize.SMART_BANNER);
            adView.setAdUnitId(adUnitId);
            LUIUtil.createAdBanner(adView);
            lnAdview.addView(adView);
            int navigationHeight = LScreenUtil.getBottomBarHeight(activity);
            LUIUtil.setMargins(lnAdview, 0, 0, 0, navigationHeight + navigationHeight / 4);
        }
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        LUIUtil.setPullLikeIOSHorizontal(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        LUIUtil.changeTabsFont(tabLayout, vn.loitp.core.common.Constants.FONT_PATH);
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
        return R.layout.activity_uz_player;
    }

    @Override
    public void onResume() {
        if (adView != null) {
            adView.resume();
        }
        super.onResume();
        uzVideo.onResume();
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
        uzVideo.onPause();
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        uzVideo.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (LScreenUtil.isFullScreen(activity)) {
            uzVideo.toggleFullscreen();
        } else {
            super.onBackPressed();
            LActivityUtil.slideDown(activity);
        }
    }

    @Override
    protected void onNetworkChange(EventBusData.ConnectEvent event) {
        super.onNetworkChange(event);
        //LLog.d(TAG, "onNetworkChange isConnected " + event.isConnected());
        if (uzVideo != null) {
            uzVideo.onNetworkChange(event);
        }
    }

    @Override
    public void onScreenRotateChange(boolean isLandscape) {
        if (isLandscape) {
            lnAdview.setVisibility(View.GONE);
        } else {
            lnAdview.setVisibility(View.VISIBLE);
        }
    }

    private String linkPlay;

    @Override
    public void onInitSuccess(String linkPlay) {
        if (linkPlay != null && !linkPlay.isEmpty()) {
            this.linkPlay = linkPlay;
            viewPager.setAdapter(new VPAdapter(getSupportFragmentManager()));
        }
    }

    private String[] tabData = {"Information", "Comment"};

    private class VPAdapter extends FragmentPagerAdapter {

        public VPAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            switch (position) {
                case 0:
                    FrmInformation frmInformation = new FrmInformation();
                    bundle.putSerializable(UZCons.ENTITY_DATA, data);
                    frmInformation.setArguments(bundle);
                    return frmInformation;
                case 1:
                    FrmFBComment frmFBComment = new FrmFBComment();
                    bundle.putString(Constants.FACEBOOK_COMMENT_URL, linkPlay);
                    frmFBComment.setArguments(bundle);
                    return frmFBComment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabData.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabData[position];
        }
    }

}
