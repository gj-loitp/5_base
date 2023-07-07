package vn.loitp.up.a.cv.layout.swipeRefresh.withSv

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.readTxtFromRawFolder
import com.loitp.core.ext.setColorForSwipeRefreshLayout
import com.loitp.core.ext.setDelay
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ALayoutSwipeRefreshSvBinding

@LogTag("SwipeRefreshLayoutScrollViewActivity")
@IsFullScreen(false)
class SwipeRefreshLayoutScrollViewActivity : BaseActivityFont() {
    private lateinit var binding: ALayoutSwipeRefreshSvBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALayoutSwipeRefreshSvBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = SwipeRefreshLayoutScrollViewActivity::class.java.simpleName
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            doTask()
        }
        binding.swipeRefreshLayout.setColorForSwipeRefreshLayout()
        val poem = readTxtFromRawFolder(R.raw.loitp)
        binding.textView.text = poem
    }

    private fun doTask() {
        setDelay(5000) {
            binding.swipeRefreshLayout.isRefreshing = false
            showShortInformation("Finish", true)
        }
    }
}
