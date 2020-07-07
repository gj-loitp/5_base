package vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerview

import android.os.Bundle
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_parallax_recycler_view.*
import vn.loitp.app.R

class ParallaxRecyclerViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rv.adapter = ParallaxAdapter(this)
        //LUIUtil.setPullLikeIOSVertical(rv)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_parallax_recycler_view
    }
}
