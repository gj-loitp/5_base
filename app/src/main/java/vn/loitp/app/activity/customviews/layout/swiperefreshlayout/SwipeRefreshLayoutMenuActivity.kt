package vn.loitp.app.activity.customviews.layout.swiperefreshlayout

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_swipe_refresh_menu_layout.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withrecyclerview.SwipeRefreshLayoutRecyclerViewActivity
import vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withscrollview.SwipeRefreshLayoutScrollViewActivity

@LogTag("SwipeRefreshLayoutMenuActivity")
@IsFullScreen(false)
class SwipeRefreshLayoutMenuActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_swipe_refresh_menu_layout
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
            this.tvTitle?.text = SwipeRefreshLayoutMenuActivity::class.java.simpleName
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
