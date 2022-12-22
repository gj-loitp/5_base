package vn.loitp.app.activity.customviews.viewPager.easyFlipViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.loitp.R;

public class MyFragment extends Fragment {

    int color = Color.BLACK;
    int position = 0;

    public static MyFragment newInstance(int color, int position) {
        MyFragment myFragment = new MyFragment();

        Bundle args = new Bundle();
        args.putInt("color", color);
        args.putInt("pos", position);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dummy_layout, container, false);
        rootView.findViewById(R.id.textView).setBackgroundColor(color);
        rootView.setTag(position);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            color = bundle.getInt("color", Color.BLACK);
            position = bundle.getInt("pos", 0);
        }
    }
}
