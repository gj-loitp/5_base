package vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerview

import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_parallax_recycler_view.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_parallax_recycler_view)
@LogTag("ParallaxRecyclerViewActivity")
class ParallaxRecyclerViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rv.adapter = ParallaxAdapter(this)
        //LUIUtil.setPullLikeIOSVertical(rv)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

}
