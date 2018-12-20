package vn.loitp.core.loitp.uiza;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LDateUtils;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.data.EventBusData;
import vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata.Data;
import vn.loitp.views.layout.swipeback.SwipeBackLayout;
import vn.loitp.views.uzvideo.UZVideo;

//custom UI exo_playback_control_view.xml
public class UZPlayerActivity extends BaseFontActivity implements UZVideo.UZCallback {
    private UZVideo uzVideo;
    private Data data;
    private SwipeBackLayout swipeBackLayout;
    private AdView adView;
    private LinearLayout lnAdview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        swipeBackLayout = (SwipeBackLayout) findViewById(R.id.swipe_back_layout);
        uzVideo = (UZVideo) findViewById(R.id.uz_video);
        uzVideo.getRlRootView().setBackgroundColor(Color.TRANSPARENT);
        uzVideo.setTvTitle(data.getName() + "");
        uzVideo.playEntity(entityId);
        uzVideo.setUzCallback(this);

        ScrollView sv = (ScrollView) findViewById(R.id.sv);
        LUIUtil.setPullLikeIOSVertical(sv);

        TextView tvName = (TextView) findViewById(R.id.tv_name);
        TextView tvCreatedAt = (TextView) findViewById(R.id.tv_created_at);
        TextView tvDuration = (TextView) findViewById(R.id.tv_duration);
        TextView tvUpdatedAt = (TextView) findViewById(R.id.tv_updated_at);
        TextView tvView = (TextView) findViewById(R.id.tv_view);
        TextView tvDescription = (TextView) findViewById(R.id.tv_description);
        tvName.setText(data.getName() + "");
        tvCreatedAt.setText("Created At: " + LDateUtils.getDateWithoutTime(data.getCreatedAt()));
        tvUpdatedAt.setText("Created At: " + LDateUtils.getDateWithoutTime(data.getUpdatedAt()));
        tvDuration.setText(LDateUtils.convertDate(data.getDuration(), "hh:mm:ss"));
        tvView.setText(data.getView() + "");
        tvDescription.setText("Description: " + (data.getDescription() == null ? " - " : data.getDescription()));

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
    }

    @Override
    protected boolean setFullScreen() {
        return true;
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
            swipeBackLayout.setSwipeFromEdge(true);
            lnAdview.setVisibility(View.GONE);
        } else {
            swipeBackLayout.setSwipeFromEdge(false);
            lnAdview.setVisibility(View.VISIBLE);
        }
    }
}
