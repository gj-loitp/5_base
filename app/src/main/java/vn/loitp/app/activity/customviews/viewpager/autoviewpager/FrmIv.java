package vn.loitp.app.activity.customviews.viewpager.autoviewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import loitp.basemaster.R;

public class FrmIv extends Fragment {
    public static FrmIv newInstance() {
        return new FrmIv();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_iv, container, false);
    }
}
