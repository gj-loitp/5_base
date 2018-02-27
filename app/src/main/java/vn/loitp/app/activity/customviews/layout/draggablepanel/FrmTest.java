package vn.loitp.app.activity.customviews.layout.draggablepanel;

/**
 * Created by www.muathu@gmail.com on 12/24/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import loitp.basemaster.R;

public class FrmTest extends Fragment {
    public static FrmTest newInstance() {
        return new FrmTest();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frm_test, container, false);
    }
}
