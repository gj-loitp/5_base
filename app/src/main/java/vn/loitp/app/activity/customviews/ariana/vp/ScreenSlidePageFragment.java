package vn.loitp.app.activity.customviews.ariana.vp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.core.utilities.LStoreUtil;
import com.views.ariana.ArianaTextListener;
import com.views.ariana.GradientAngle;

import vn.loitp.app.R;

public class ScreenSlidePageFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    TextView textView;
    private String mParam1;

    public ScreenSlidePageFragment() {
        // Required empty public constructor
    }

    public static ScreenSlidePageFragment newInstance(String param1) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        textView = rootView.findViewById(R.id.textView);
        textView.setText(mParam1);
        ((ArianaViewPagerActivity) getActivity()).viewPager.addOnPageChangeListener(
                new ArianaTextListener(LStoreUtil.INSTANCE.getColors(), textView,
                        ((ArianaViewPagerActivity) getActivity()).viewPager,
                        GradientAngle.RIGHT_TOP_TO_LEFT_BOTTOM));

        return rootView;
    }

}