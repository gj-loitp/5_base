package vn.loitp.app.a.cv.rv.parallaxRv

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_recycler_view_parallax.*
import vn.loitp.R

@LogTag("ParallaxRecyclerViewActivity")
@IsFullScreen(false)
class ParallaxRecyclerViewActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_view_parallax
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ParallaxRecyclerViewActivityFont::class.java.simpleName
        }
        rv.adapter = ParallaxAdapter(this)
    }
}
