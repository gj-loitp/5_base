package vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.core.base.BaseFragment;
import com.core.utilities.LLog;

import loitp.basemaster.R;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmPhoto extends BaseFragment {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LLog.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        TextView tv = getFrmRootView().findViewById(R.id.tv);
        RelativeLayout bkg = getFrmRootView().findViewById(R.id.bkg);
        Bundle bundle = getArguments();
        if (bundle != null) {
            VPPhoto vpPhoto = (VPPhoto) bundle.getSerializable("vpphoto");
            if (vpPhoto != null) {
                bkg.setBackgroundColor(vpPhoto.getColor());
                tv.setText(vpPhoto.getString());
            }
        }
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LLog.d(TAG, "onCreateView");
        *//*TextView tv = (TextView) frmRootView.findViewById(R.id.tv);
        RelativeLayout bkg = (RelativeLayout) frmRootView.findViewById(R.id.bkg);
        Bundle bundle = getArguments();
        if (bundle != null) {
            VPPhoto vpPhoto = (VPPhoto) bundle.getSerializable("vpphoto");
            if (vpPhoto != null) {
                bkg.setBackgroundColor(vpPhoto.getColor());
                tv.setText(vpPhoto.getString());
            }
        }*//*
        return frmRootView;
    }*/

    @Override
    protected int setLayoutResourceId() {
        return R.layout.item_photo;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }
}