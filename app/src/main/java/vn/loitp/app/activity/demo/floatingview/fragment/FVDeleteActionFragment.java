package vn.loitp.app.activity.demo.floatingview.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.floatingview.service.FVCustomFloatingViewService;

public class FVDeleteActionFragment extends Fragment {
    public static FVDeleteActionFragment newInstance() {
        final FVDeleteActionFragment fragment = new FVDeleteActionFragment();
        return fragment;
    }

    public FVDeleteActionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_delete_action, container, false);
        final View clearFloatingButton = rootView.findViewById(R.id.clearDemo);
        clearFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Easy way to delete a service
                final Activity activity = getActivity();
                activity.stopService(new Intent(activity, FVCustomFloatingViewService.class));
            }
        });
        return rootView;
    }
}
