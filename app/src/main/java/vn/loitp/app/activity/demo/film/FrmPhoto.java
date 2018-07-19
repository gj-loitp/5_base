package vn.loitp.app.activity.demo.film;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout.ex.VPPhoto;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LLog;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmPhoto extends BaseFragment {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LLog.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        TextView tv = (TextView) frmRootView.findViewById(R.id.tv);
        RelativeLayout bkg = (RelativeLayout) frmRootView.findViewById(R.id.bkg);
        Bundle bundle = getArguments();
        if (bundle != null) {
            VPPhoto vpPhoto = (VPPhoto) bundle.getSerializable("vpphoto");
            LLog.d(TAG, "getColor:" + vpPhoto.getColor());
            if (vpPhoto != null) {
                bkg.setBackgroundColor(vpPhoto.getColor());
                tv.setText(vpPhoto.getString());
            }
        }
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.item_photo;
    }
}