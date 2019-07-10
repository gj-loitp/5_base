package vn.loitp.app.activity.customviews.layout.constraintlayout.fabandsnackbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.core.base.BaseFontActivity;
import com.google.android.material.snackbar.Snackbar;

import loitp.basemaster.R;

public class FabAndSnackbarActivity extends BaseFontActivity {
    private Button mShowSnackbarButton;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        mShowSnackbarButton = (Button) findViewById(R.id.showSnackbarButton);
        mShowSnackbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(mCoordinatorLayout,
                        "This is a simple Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //
                            }
                        }).show();
            }
        });
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
    protected int setLayoutResourceId() {
        return R.layout.activity_fab_and_snackbar;
    }
}
