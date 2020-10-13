package vn.loitp.app.activity.customviews.layout.constraintlayout.fabandsnackbar;

import android.os.Bundle;
import android.widget.Button;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.annotation.IsFullScreen;
import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;
import com.google.android.material.snackbar.Snackbar;

import vn.loitp.app.R;

@LayoutId(R.layout.activity_fab_and_snackbar)
@LogTag("FabAndSnackbarActivity")
@IsFullScreen(false)
public class FabAndSnackbarActivity extends BaseFontActivity {
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        Button showSnackbarButton = findViewById(R.id.showSnackbarButton);
        showSnackbarButton.setOnClickListener(view -> Snackbar.make(coordinatorLayout,
                "This is a simple Snackbar", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", v -> {
                    //
                }).show());
    }

}
