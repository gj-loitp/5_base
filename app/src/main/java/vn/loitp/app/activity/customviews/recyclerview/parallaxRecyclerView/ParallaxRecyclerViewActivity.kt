package vn.loitp.app.activity.customviews.recyclerview.parallaxRecyclerView

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_recycler_view_parallax.*
import vn.loitp.app.R

@LogTag("ParallaxRecyclerViewActivity")
@IsFullScreen(false)
class ParallaxRecyclerViewActivity : BaseFontActivity() {

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
            this.tvTitle?.text = ParallaxRecyclerViewActivity::class.java.simpleName
        }
        rv.adapter = ParallaxAdapter(this)
    }
}
