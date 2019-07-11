package vn.loitp.app.activity.demo.fragmentnavigation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.core.base.BaseFragment;
import com.core.utilities.LLog;

import loitp.basemaster.R;


public class FN3 extends BaseFragment implements OnBackPressedListener {
    private FragmentNavigationActivity fragmentNavigationActivity;

    public FN3() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentNavigationActivity = (FragmentNavigationActivity) getActivity();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fn_3;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final Button button = view.findViewById(R.id.button3);
        button.setOnClickListener(v -> {
            fragmentNavigationActivity.getNavController().navigate(R.id.action_fn3_to_fn1);
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        LLog.d(fragmentNavigationActivity.T, "onResume FN3");
    }

    @Override
    public void onBackPressed() {
        LLog.d(fragmentNavigationActivity.T, "onBackPressed FN3");
        fragmentNavigationActivity.popThisFragment();
    }
}
