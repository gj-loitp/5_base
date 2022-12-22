package vn.loitp.app.activity.customviews.layout.circularView

import com.loitp.views.layout.circularView.Marker
import com.loitp.views.layout.circularView.SimpleCircularViewAdapter
import vn.loitp.app.R

class SimpleCircularViewAdapter : SimpleCircularViewAdapter() {
    override fun getCount(): Int {
        // This count will tell the circular view how many markers to use.
        return 10
    }

    override fun setupMarker(position: Int, marker: Marker) {
        // Setup and customize markers here. This is called every time a marker is to be displayed.
        // 0 >= position > getCount()
        // The marker is intended to be reused. It will never be null.
        marker.setSrc(R.drawable.logo)
        marker.isFitToCircle = true
        marker.radius = (10 + 2 * position).toFloat()
    }
}
