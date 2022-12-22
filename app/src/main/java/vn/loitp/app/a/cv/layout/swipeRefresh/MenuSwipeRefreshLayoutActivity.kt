package vn.loitp.app.a.cv.layout.swipeRefresh

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_swipe_refresh_layout.*
import vn.loitp.R
import vn.loitp.app.a.cv.layout.swipeRefresh.withRv.SwipeRefreshLayoutRecyclerViewActivity
import vn.loitp.app.a.cv.layout.swipeRefresh.withSv.SwipeRefreshLayoutScrollViewActivity

@LogTag("SwipeRefreshLayoutMenuActivity")
@IsFullScreen(false)
class MenuSwipeRefreshLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_swipe_refresh_layout
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
            val intent = Intent(this, SwipeRefreshLayoutScrollViewActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btWithRecyclerView.setSafeOnClickListener {
            val intent = Intent(this, SwipeRefreshLayoutRecyclerViewActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
