package vn.puresolutions.livestar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.custom.dialog.MBFRegisterDialog;
import vn.puresolutions.livestar.fragment.OnboardingFragment;
import vn.puresolutions.livestar.helper.NotificationCenter;

/**
 * Created by khanh on 7/31/16.
 */
public class OnboardingActivity extends FacebookSignInActivity implements NotificationCenter.NotificationCenterListener {
    @BindView(R.id.onboardingActivity_vpgPager)
    ViewPager vpgContainer;

    @BindView(R.id.onboardingActivity_pageIndicator)
    CirclePageIndicator pageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        int[] titles = {R.string.onboard_title1,
                R.string.onboard_title2,
                R.string.onboard_title3,
                R.string.onboard_title4};
        int[] desciptions = {R.string.onboard_description1,
                R.string.onboard_description2,
                R.string.onboard_description3,
                R.string.onboard_description4};
        int[] backgrounds = {R.drawable.img_onboarding1,
                R.drawable.img_onboarding2,
                R.drawable.img_onboarding3,
                R.drawable.img_onboarding4};

        List<Fragment> fragments = new ArrayList<>(titles.length);
        for (int i = 0; i < titles.length; i++) {
            fragments.add(OnboardingFragment.newInstance(titles[i], desciptions[i], backgrounds[i]));
        }
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), fragments);
        vpgContainer.setAdapter(adapter);
        pageIndicator.setViewPager(vpgContainer);

//        if (!TextUtils.isEmpty(LSApplication.getPhoneNumber())) {
//            btnSignIn.setVisibility(View.INVISIBLE);
//            btnSignUp.setVisibility(View.INVISIBLE);
//            tvWelcome.setText(getString(R.string.ls_welcome_phone_number, LSApplication.getPhoneNumber()));
//            tvWelcome.setVisibility(View.VISIBLE);
//        }
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.EnterMainScreen);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.EnterMainScreen);
    }

    @OnClick(R.id.onboardActivity_tvSkip)
    void onSkip() {
        startActivity(MainActivity.class);
    }

    @OnClick(R.id.onboardActivity_btnSignInFB)
    void onSignInFb() {
        signInFacebook();
    }

    @OnClick(R.id.onboardActivity_btnSignInMBF)
    void onBtnMBFClick() {
        MBFRegisterDialog registerDialog = new MBFRegisterDialog(this);
        registerDialog.setPositiveButton(R.string.sign_in, dialog -> onSignIn());
        registerDialog.show();
    }

    @OnClick(R.id.onboardActivity_btnSignIn)
    void onSignIn() {
        startActivity(SignInActivity.class);
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.EnterMainScreen) {
            finish();
        }
    }

    static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_onboarding;
    }
}
