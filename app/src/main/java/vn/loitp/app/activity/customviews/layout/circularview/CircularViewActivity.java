package vn.loitp.app.activity.customviews.layout.circularview;

import android.app.Activity;
import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.LToast;
import vn.loitp.views.layout.circularview.CircularView;
import vn.loitp.views.layout.circularview.Marker;

//https://github.com/sababado/CircularView?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=238
public class CircularViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MySimpleCircularViewAdapter mAdapter = new MySimpleCircularViewAdapter();
        CircularView circularView = (CircularView) findViewById(R.id.circular_view);
        circularView.setAdapter(mAdapter);
        circularView.setOnCircularViewObjectClickListener(new CircularView.OnClickListener() {
            public void onClick(final CircularView view, boolean isLongClick) {
                LToast.show(activity, "onClick");
            }

            public void onMarkerClick(CircularView view, Marker marker, int position, boolean isLongClick) {
                LToast.show(activity, "onClick " + position);
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
        return R.layout.activity_circular_view;
    }

}
