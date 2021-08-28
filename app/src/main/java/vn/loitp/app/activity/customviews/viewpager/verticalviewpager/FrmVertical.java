package vn.loitp.app.activity.customviews.viewpager.verticalviewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.loitp.app.R;

public class FrmVertical extends Fragment {
    String text;
    private static final String TEXT = "text";

    public FrmVertical() {
    }


    public static FrmVertical newInstance(String data) {
        FrmVertical fragment = new FrmVertical();
        Bundle bundle = new Bundle(1);
        bundle.putString(TEXT, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.text = getArguments().getString(TEXT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager_vertical, container, false);
        TextView tv = view.findViewById(R.id.tv);
        tv.setText(text);
        return view;
    }

}
