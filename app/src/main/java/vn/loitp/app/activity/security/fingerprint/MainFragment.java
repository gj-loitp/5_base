package vn.loitp.app.activity.security.fingerprint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import loitp.basemaster.R;

/**
 * Created by loitp on 2018/02/14.
 */

public class MainFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_text, container, false);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText("You logged in!");
        return view;
    }
}
