package vn.loitp.core.loitp.uiza;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.facebookcomment.FrmFBComment;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LDeviceUtil;
import vn.loitp.core.utilities.LImageUtil;
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
    private final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LDeviceUtil.isCanOverlay(activity)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
            LActivityUtil.tranIn(activity);
        }
        isShowAnimWhenExit = false;
        data = (Data) getIntent().getSerializableExtra(UZCons.ENTITY_DATA);
        boolean isShowCover = getIntent().getBooleanExtra(UZCons.ENTITY_SHOULD_SHOW_COVER, true);
        long contentPosition = getIntent().getLongExtra(Constants.INSTANCE.getKEY_VIDEO_CURRENT_POSITION(), 0);
        if (data == null) {
            showDialogError(getString(R.string.video_not_found));
            return;
        }
        String entityId = data.getId();
        if (entityId == null || entityId.isEmpty()) {
            showDialogError(getString(R.string.video_not_found));
            return;
        }
        final String adUnitId = getIntent().getStringExtra(Constants.INSTANCE.getAD_UNIT_ID_BANNER());
        //LLog.d(TAG, "adUnitId " + adUnitId);
        ImageView ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        LImageUtil.load(activity, data.getThumbnail(), ivBkg, R.drawable.bkg_black_colorprimary);
        uzVideo = (UZVideo) findViewById(R.id.uz_video);
        //uzVideo.getRlRootView().setBackgroundColor(Color.TRANSPARENT);
        uzVideo.setData(data, isShowCover);
        uzVideo.setTvTitle(data.getName() + "");
        uzVideo.setAdmobIDBanner(adUnitId);
        uzVideo.playEntity(entityId, contentPosition);
        uzVideo.setUzCallback(this);
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
        LUIUtil.changeTabsFont(tabLayout, Constants.INSTANCE.getFONT_PATH());
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
        if (uzVideo != null) {
            uzVideo.onResume();
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
        if (uzVideo != null) {
            uzVideo.onPause();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        if (uzVideo != null) {
            uzVideo.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (LScreenUtil.isLandscape(activity)) {
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
            /*LUIUtil.setDelay(1000, new LUIUtil.DelayCallback() {
                @Override
                public void doAfter(int mls) {
                    if (uzVideo != null) {
                        uzVideo.getRlRootView().setBackgroundColor(Color.BLACK);
                    }
                }
            });*/
        } else {
            lnAdview.setVisibility(View.VISIBLE);
            /*LUIUtil.setDelay(1000, new LUIUtil.DelayCallback() {
                @Override
                public void doAfter(int mls) {
                    if (uzVideo != null) {
                        uzVideo.getRlRootView().setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            });*/
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
                    bundle.putString(Constants.INSTANCE.getFACEBOOK_COMMENT_URL(), linkPlay);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //LLog.d(TAG, "onActivityResult " + requestCode + ", " + resultCode);
    }
}
