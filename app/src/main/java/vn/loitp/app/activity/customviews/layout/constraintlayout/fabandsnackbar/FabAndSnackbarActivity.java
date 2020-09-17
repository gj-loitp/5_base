package vn.loitp.app.activity.customviews.layout.constraintlayout.fabandsnackbar;

import android.os.Bundle;
import android.widget.Button;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.annotation.LayoutId;
import com.core.base.BaseFontActivity;
import com.google.android.material.snackbar.Snackbar;

import vn.loitp.app.R;

@LayoutId(R.layout.activity_fab_and_snackbar)
public class FabAndSnackbarActivity extends BaseFontActivity {
    private Button mShowSnackbarButton;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoordinatorLayout = findViewById(R.id.coordinatorLayout);

        mShowSnackbarButton = findViewById(R.id.showSnackbarButton);
        mShowSnackbarButton.setOnClickListener(view -> Snackbar.make(mCoordinatorLayout,
                "This is a simple Snackbar", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", v -> {
                    //
                }).show());
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

}
