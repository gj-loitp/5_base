package vn.loitp.app.activity.customviews.ariana.vp;

/**
 * Created by www.muathu@gmail.com on 1/12/2018.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.views.ariana.ArianaTextListener;
import vn.loitp.views.ariana.GradientAngle;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScreenSlidePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
        textView = (TextView) rootView.findViewById(R.id.textView);
        textView.setText(mParam1);
        ((ArianaViewPagerActivity) getActivity()).viewPager.addOnPageChangeListener(
                new ArianaTextListener(LStoreUtil.getColors(), textView,
                        ((ArianaViewPagerActivity) getActivity()).viewPager,
                        GradientAngle.RIGHT_TOP_TO_LEFT_BOTTOM));

        return rootView;
    }

}