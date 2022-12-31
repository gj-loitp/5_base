package vn.loitp.a.cv.layout.swipeRefresh

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_menu_swipe_refresh_layout.*
import vn.loitp.R
import vn.loitp.a.cv.layout.swipeRefresh.withRv.SwipeRefreshLayoutRecyclerViewActivity
import vn.loitp.a.cv.layout.swipeRefresh.withSv.SwipeRefreshLayoutScrollViewActivity

@LogTag("SwipeRefreshLayoutMenuActivity")
@IsFullScreen(false)
class MenuSwipeRefreshLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_swipe_refresh_layout
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuSwipeRefreshLayoutActivity::class.java.simpleName
        }
        btWithScrollView.setSafeOnClickListener {
            launchActivity(SwipeRefreshLayoutScrollViewActivity::class.java)
        }
        btWithRecyclerView.setSafeOnClickListener {
            launchActivity(SwipeRefreshLayoutRecyclerViewActivity::class.java)
        }
    }
}
