package vn.loitp.app.activity;

/**
 * Created by www.muathu@gmail.com on 12/24/2017.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFragment;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmDummy extends BaseFragment {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView tv = (TextView) frmRootView.findViewById(R.id.tv);
        tv.setText("DUMMYYYYYYYYYYYYYYYYYYY");
        return frmRootView;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_bottom;
    }
}