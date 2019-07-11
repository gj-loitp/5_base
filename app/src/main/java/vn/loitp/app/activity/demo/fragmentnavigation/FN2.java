package vn.loitp.app.activity.demo.fragmentnavigation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;

import com.core.base.BaseFragment;
import com.core.utilities.LLog;

import loitp.basemaster.R;

public class FN2 extends BaseFragment {
    private FragmentNavigationActivity fragmentNavigationActivity;

    public FN2() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentNavigationActivity = (FragmentNavigationActivity) getActivity();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fn_2;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final Button button = view.findViewById(R.id.button2);
        button.setOnClickListener(v -> {
            final NavController navController = fragmentNavigationActivity.getNavController();
            //new NavOptions.Builder().setExitAnim(R.anim.fade_out);
            navController.navigate(R.id.action_fn2_to_fn3);
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        LLog.d(fragmentNavigationActivity.T, "onResume FN2");
    }
}
