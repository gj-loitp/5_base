package vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout.ex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.loitp.app.base.BaseFragment;
import vn.loitp.livestar.R;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmPhoto extends BaseFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_photo, container, false);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        RelativeLayout bkg = (RelativeLayout) view.findViewById(R.id.bkg);

        Bundle bundle = getArguments();
        if (bundle != null) {
            VPPhoto vpPhoto = (VPPhoto) bundle.getSerializable("vpphoto");
            if (vpPhoto != null) {
                bkg.setBackgroundColor(vpPhoto.getColor());
                tv.setText(vpPhoto.getString());
            }
        }

        return view;


    }
}