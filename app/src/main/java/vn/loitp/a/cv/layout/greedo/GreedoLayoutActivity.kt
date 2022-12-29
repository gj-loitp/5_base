package vn.loitp.a.cv.layout.greedo

import android.os.Bundle
import android.widget.ToggleButton
import androidx.core.view.isVisible
import com.fivehundredpx.greedolayout.GreedoLayoutManager
import com.fivehundredpx.greedolayout.GreedoSpacingItemDecoration
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_layout_greedo.*
import vn.loitp.R

@LogTag("GreedoLayoutActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class GreedoLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_layout_greedo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(view = it, runnable = {
                    LSocialUtil.openUrlInBrowser(
                        context = context,
                        url = "https://github.com/500px/greedo-layout-for-android"
                    )
                })
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = GreedoLayoutActivity::class.java.simpleName
        }

        val photosAdapter = PhotosAdapter(this)
        val layoutManager = GreedoLayoutManager(photosAdapter)
        layoutManager.setMaxRowHeight(MeasUtils.dpToPx(150F, this))

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = photosAdapter
        val spacing = MeasUtils.dpToPx(4F, this)
        recyclerView.addItemDecoration(GreedoSpacingItemDecoration(spacing))

        toggleFixedHeight.setOnClickListener { view ->
            layoutManager.setFixedHeight((view as ToggleButton).isChecked)
            layoutManager.requestLayout()
        }
    }
}
