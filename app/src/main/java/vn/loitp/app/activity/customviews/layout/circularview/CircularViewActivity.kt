package vn.loitp.app.activity.customviews.layout.circularview

import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.layout.circularview.CircularView
import com.views.layout.circularview.Marker
import kotlinx.android.synthetic.main.activity_layout_circular_view.*
import vn.loitp.app.R

//https://github.com/sababado/CircularView?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=238

@LayoutId(R.layout.activity_layout_circular_view)
@LogTag("CircularViewActivity")
class CircularViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mAdapter = SimpleCircularViewAdapter()
        circularView.adapter = mAdapter
        circularView.setOnCircularViewObjectClickListener(object : CircularView.OnClickListener {
            override fun onClick(view: CircularView, isLongClick: Boolean) {
                showShort("onClick")
            }

            override fun onMarkerClick(view: CircularView, marker: Marker, position: Int, isLongClick: Boolean) {
                showShort("onClick $position")
            }
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

}
