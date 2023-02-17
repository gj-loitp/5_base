package vn.loitp.up.a.cv.layout.swipeRefresh

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AMenuSwipeRefreshLayoutBinding
import vn.loitp.up.a.cv.layout.swipeRefresh.withRv.SwipeRefreshLayoutRecyclerViewActivity
import vn.loitp.up.a.cv.layout.swipeRefresh.withSv.SwipeRefreshLayoutScrollViewActivity

@LogTag("SwipeRefreshLayoutMenuActivity")
@IsFullScreen(false)
class MenuSwipeRefreshLayoutActivity : BaseActivityFont() {

    private lateinit var binding: AMenuSwipeRefreshLayoutBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuSwipeRefreshLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuSwipeRefreshLayoutActivity::class.java.simpleName
        }
        binding.btWithScrollView.setSafeOnClickListener {
            launchActivity(SwipeRefreshLayoutScrollViewActivity::class.java)
        }
        binding.btWithRecyclerView.setSafeOnClickListener {
            launchActivity(SwipeRefreshLayoutRecyclerViewActivity::class.java)
        }
    }
}
