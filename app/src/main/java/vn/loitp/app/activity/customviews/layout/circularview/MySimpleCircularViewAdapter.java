package vn.loitp.app.activity.customviews.layout.circularview;

import loitp.basemaster.R;
import vn.loitp.views.layout.circularview.Marker;
import vn.loitp.views.layout.circularview.SimpleCircularViewAdapter;

/**
 * Created by LENOVO on 2/28/2018.
 */

public class MySimpleCircularViewAdapter extends SimpleCircularViewAdapter {
    @Override
    public int getCount() {
        // This count will tell the circular view how many markers to use.
        return 10;
    }

    @Override
    public void setupMarker(final int position, final Marker marker) {
        // Setup and customize markers here. This is called every time a marker is to be displayed.
        // 0 >= position > getCount()
        // The marker is intended to be reused. It will never be null.
        marker.setSrc(R.drawable.logo);
        marker.setFitToCircle(true);
        marker.setRadius(10 + 2 * position);
    }
}