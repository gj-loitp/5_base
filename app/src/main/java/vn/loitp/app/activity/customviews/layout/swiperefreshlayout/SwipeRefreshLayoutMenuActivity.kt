package vn.loitp.app.activity.customviews.layout.swiperefreshlayout

import android.content.Intent
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_swipe_refresh_menu_layout.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withrecyclerview.SwipeRefreshLayoutRecyclerViewActivity
import vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withscrollview.SwipeRefreshLayoutScrollViewActivity

@LayoutId(R.layout.activity_swipe_refresh_menu_layout)
@LogTag("SwipeRefreshLayoutMenuActivity")
@IsFullScreen(false)
class SwipeRefreshLayoutMenuActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt_with_scroll_view.setOnClickListener { _ ->
            val intent = Intent(this, SwipeRefreshLayoutScrollViewActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        bt_with_recycler_view.setOnClickListener { _ ->
            val intent = Intent(this, SwipeRefreshLayoutRecyclerViewActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }

}
