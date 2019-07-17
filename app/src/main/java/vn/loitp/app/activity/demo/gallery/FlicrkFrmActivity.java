package vn.loitp.app.activity.demo.gallery;

import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.core.common.Constants;
import com.core.loitp.gallery.albumonly.GalleryCorePhotosOnlyFrm;
import com.core.utilities.LScreenUtil;

import loitp.basemaster.R;

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
        bundle.putString(Constants.INSTANCE.getSK_PHOTOSET_ID(), Constants.INSTANCE.getFLICKR_ID_ENGLISH());
        frmPhoto.setArguments(bundle);
        LScreenUtil.INSTANCE.replaceFragment(getActivity(), R.id.fl_container_photo, frmPhoto, false);
    }
}
