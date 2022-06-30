package vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerview

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_parallax_recycler_view.*
import vn.loitp.app.R

@LogTag("ParallaxRecyclerViewActivity")
@IsFullScreen(false)
class ParallaxRecyclerViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_parallax_recycler_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        rv.adapter = ParallaxAdapter(this)
    }
}
