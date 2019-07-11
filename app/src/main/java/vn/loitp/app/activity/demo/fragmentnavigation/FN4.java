package vn.loitp.app.activity.demo.fragmentnavigation;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.core.base.BaseFragment;
import com.core.utilities.LLog;

import loitp.basemaster.R;


public class FN4 extends BaseFragment implements OnBackPressedListener {
    private FragmentNavigationActivity fragmentNavigationActivity;

    public FN4() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentNavigationActivity = (FragmentNavigationActivity) getActivity();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fn_4;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onResume() {
        super.onResume();
        LLog.d(fragmentNavigationActivity.T, "onResume FN4");
    }

    @Override
    public void onBackPressed() {
        LLog.d(fragmentNavigationActivity.T, "onBackPressed FN4");
        fragmentNavigationActivity.popThisFragment();
    }
}
