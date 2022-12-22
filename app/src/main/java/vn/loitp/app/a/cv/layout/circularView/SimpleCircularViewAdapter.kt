package vn.loitp.app.a.cv.layout.circularView

import com.loitp.views.layout.circularView.Marker
import com.loitp.views.layout.circularView.SimpleCircularViewAdapter
import vn.loitp.R

class SimpleCircularViewAdapter : SimpleCircularViewAdapter() {


    override val count: Int
        get() = 10

    override fun setupMarker(position: Int, marker: Marker?) {
        // Setup and customize markers here. This is called every time a marker is to be displayed.
        // 0 >= position > getCount()
        // The marker is intended to be reused. It will never be null.
        marker?.apply {
            setSrc(R.drawable.logo)
            isFitToCircle = true
            radius = (10 + 2 * position).toFloat()
        }
    }
}
