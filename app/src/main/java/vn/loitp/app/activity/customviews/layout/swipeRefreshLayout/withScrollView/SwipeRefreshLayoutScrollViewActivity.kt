package vn.loitp.app.activity.customviews.layout.swipeRefreshLayout.withScrollView

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LStoreUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_swipe_refresh_scroll_view_layout.*
import vn.loitp.app.R

@LogTag("SwipeRefreshLayoutScrollViewActivity")
@IsFullScreen(false)
class SwipeRefreshLayoutScrollViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_swipe_refresh_scroll_view_layout
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
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = SwipeRefreshLayoutScrollViewActivity::class.java.simpleName
        }
        swipeRefreshLayout.setOnRefreshListener {
            doTask()
        }
        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout)
        val poem = LStoreUtil.readTxtFromRawFolder(R.raw.loitp)
        textView.text = poem
    }

    private fun doTask() {
        LUIUtil.setDelay(5000) {
            swipeRefreshLayout?.isRefreshing = false
            showShortInformation("Finish", true)
        }
    }
}
