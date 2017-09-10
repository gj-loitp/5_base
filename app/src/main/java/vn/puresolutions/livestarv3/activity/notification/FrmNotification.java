package vn.puresolutions.livestarv3.activity.notification;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseFragment;

/**
 * File created on 8/2/2017.
 *
 * @author Anhdv
 */
public class FrmNotification extends BaseFragment {

    public FrmNotification() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_notification, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
