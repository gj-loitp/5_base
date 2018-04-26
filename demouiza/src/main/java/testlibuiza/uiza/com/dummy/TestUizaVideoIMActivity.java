package testlibuiza.uiza.com.dummy;

import android.os.Bundle;
import android.widget.FrameLayout;

import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.views.uizavideo.view.frm.FrmUizaIMAVideo;

public class TestUizaVideoIMActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout fl = (FrameLayout) findViewById(R.id.fl);
        final FrmUizaIMAVideo frmUizaIMAVideo = new FrmUizaIMAVideo();
        LScreenUtil.replaceFragment(activity, fl.getId(), frmUizaIMAVideo, false);
        frmUizaIMAVideo.setFragmentCallback(new BaseFragment.FragmentCallback() {
            @Override
            public void onViewCreated() {
                String linkPlay = getString(loitp.core.R.string.url_dash);
                String urlIMAAd = getString(loitp.core.R.string.ad_tag_url);
                String urlThumnailsPreviewSeekbar = getString(loitp.core.R.string.url_thumbnails);
                frmUizaIMAVideo.initData(linkPlay, urlIMAAd, urlThumnailsPreviewSeekbar);
            }
        });
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
        return R.layout.test_uiza_ima_video_activity;
    }
}
