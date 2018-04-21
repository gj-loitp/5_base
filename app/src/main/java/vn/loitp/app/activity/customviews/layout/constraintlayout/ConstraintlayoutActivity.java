package vn.loitp.app.activity.customviews.layout.constraintlayout;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;

public class ConstraintlayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraintlayout);

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.ll);
        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = constraintLayout.getX() - event.getRawX();
                        dY = constraintLayout.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        constraintLayout.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .scaleX(1.5f)
                                .start();
                        break;
                    default:
                        return false;
                }
                return true;
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
        return R.layout.activity_constraintlayout;
    }

    float dX, dY;
}
