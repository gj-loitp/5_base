package vn.loitp.app.activity.customviews.layout.circularview;

import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.views.LToast;
import com.views.layout.circularview.CircularView;
import com.views.layout.circularview.Marker;

import loitp.basemaster.R;

//https://github.com/sababado/CircularView?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=238
public class CircularViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MySimpleCircularViewAdapter mAdapter = new MySimpleCircularViewAdapter();
        CircularView circularView = (CircularView) findViewById(R.id.circular_view);
        circularView.setAdapter(mAdapter);
        circularView.setOnCircularViewObjectClickListener(new CircularView.OnClickListener() {
            public void onClick(final CircularView view, boolean isLongClick) {
                LToast.INSTANCE.show(getActivity(), "onClick");
            }

            public void onMarkerClick(CircularView view, Marker marker, int position, boolean isLongClick) {
                LToast.INSTANCE.show(getActivity(), "onClick " + position);
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
