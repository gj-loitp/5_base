package vn.loitp.app.activity.demo.gallery;

import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.albumonly.GalleryCorePhotosOnlyFrm;
import vn.loitp.core.utilities.LScreenUtil;

public class FlicrkFrmActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switchPage();
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
        return R.layout.activity_flickr_frm;
    }

    private void switchPage() {
        GalleryCorePhotosOnlyFrm frmPhoto = new GalleryCorePhotosOnlyFrm();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_ENGLISH);
        frmPhoto.setArguments(bundle);
        LScreenUtil.replaceFragment(activity, R.id.fl_container_photo, frmPhoto, false);
    }
}
