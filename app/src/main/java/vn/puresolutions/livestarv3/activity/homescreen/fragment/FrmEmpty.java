package vn.puresolutions.livestarv3.activity.homescreen.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseFragment;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmEmpty extends BaseFragment {

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
        View view = inflater.inflate(R.layout.frm_empty, container, false);
        return view;

    }
}

