package vn.puresolutions.livestar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseFragment;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 12/14/2015
 */
public class OnboardingFragment extends BaseFragment {

    private static final String BUNDLE_KEY_TITLE = "title";
    private static final String BUNDLE_KEY_DESCRIPTION = "description";
    private static final String BUNDLE_KEY_BACKGROUND = "background";

    @BindView(R.id.ls_onboardFragment_imgBackground)
    ImageView imgBackground;

    @BindView(R.id.ls_onboardFragment_tvTitle)
    TextView tvTitle;

    @BindView(R.id.ls_onboardFragment_tvDescription)
    TextView tvDescription;

    public static OnboardingFragment newInstance(int title, int description, int background) {
        Bundle args = new Bundle();
        args.putInt(BUNDLE_KEY_TITLE, title);
        args.putInt(BUNDLE_KEY_DESCRIPTION, description);
        args.putInt(BUNDLE_KEY_BACKGROUND, background);

        OnboardingFragment fragment = new OnboardingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Bundle args = getArguments();
        tvTitle.setText(args.getInt(BUNDLE_KEY_TITLE));
        tvDescription.setText(args.getInt(BUNDLE_KEY_DESCRIPTION));
        imgBackground.setImageResource(args.getInt(BUNDLE_KEY_BACKGROUND));

//        if (LSApplication.isUserMobifone()) {
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
//                    tvDescription.getLayoutParams();
//            params.bottomMargin += getResources().getDimensionPixelSize(R.dimen.ls_button_fb_margin);
//            tvDescription.setLayoutParams(params);
//        }
    }
}
