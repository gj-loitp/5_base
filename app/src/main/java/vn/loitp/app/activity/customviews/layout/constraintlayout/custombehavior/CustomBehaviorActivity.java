package vn.loitp.app.activity.customviews.layout.constraintlayout.custombehavior;

import android.os.Bundle;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.core.base.BaseFontActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import loitp.basemaster.R;

public class CustomBehaviorActivity extends BaseFontActivity {
    private FloatingActionButton mShowFabButton;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        mShowFabButton = (FloatingActionButton) findViewById(R.id.fab);
        mShowFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(mCoordinatorLayout,
                        "This is a simple Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

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
        return R.layout.activity_custom_behavior;
    }
}
