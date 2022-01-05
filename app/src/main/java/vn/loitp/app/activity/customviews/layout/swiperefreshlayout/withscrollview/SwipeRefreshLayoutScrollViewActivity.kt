package vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withscrollview

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
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
