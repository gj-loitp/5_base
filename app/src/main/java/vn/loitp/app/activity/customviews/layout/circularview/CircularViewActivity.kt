package vn.loitp.app.activity.customviews.layout.circularview

import android.os.Bundle

import com.core.base.BaseFontActivity
import com.views.LToast
import com.views.layout.circularview.CircularView
import com.views.layout.circularview.Marker

import vn.loitp.app.R

//https://github.com/sababado/CircularView?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=238
class CircularViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mAdapter = MySimpleCircularViewAdapter()
        val circularView = findViewById<CircularView>(R.id.circular_view)
        circularView.adapter = mAdapter
        circularView.setOnCircularViewObjectClickListener(object : CircularView.OnClickListener {
            override fun onClick(view: CircularView, isLongClick: Boolean) {
                LToast.show(activity, "onClick")
            }

            override fun onMarkerClick(view: CircularView, marker: Marker, position: Int, isLongClick: Boolean) {
                LToast.show(activity, "onClick $position")
            }
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_circular_view
    }

}
