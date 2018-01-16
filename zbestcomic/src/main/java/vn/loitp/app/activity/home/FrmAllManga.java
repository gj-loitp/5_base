package vn.loitp.app.activity.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFragment;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmAllManga extends BaseFragment {

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
        View view = inflater.inflate(R.layout.frm_all_manga, container, false);
        return view;
    }
}