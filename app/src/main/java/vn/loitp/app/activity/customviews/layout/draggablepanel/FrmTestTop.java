package vn.loitp.app.activity.customviews.layout.draggablepanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.loitp.app.R;

public class FrmTestTop extends Fragment {
    public static FrmTestTop newInstance() {
        return new FrmTestTop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frm_test, container, false);
    }
}
