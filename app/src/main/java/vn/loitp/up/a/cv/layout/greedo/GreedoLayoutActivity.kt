package vn.loitp.up.a.cv.layout.greedo

import android.os.Bundle
import android.widget.ToggleButton
import androidx.core.view.isVisible
import com.fivehundredpx.greedolayout.GreedoLayoutManager
import com.fivehundredpx.greedolayout.GreedoSpacingItemDecoration
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ALayoutGreedoBinding

@LogTag("GreedoLayoutActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class GreedoLayoutActivity : BaseActivityFont() {

    private lateinit var binding: ALayoutGreedoBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALayoutGreedoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
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

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = photosAdapter
        val spacing = MeasUtils.dpToPx(4F, this)
        binding.recyclerView.addItemDecoration(GreedoSpacingItemDecoration(spacing))

        binding.toggleFixedHeight.setOnClickListener { view ->
            layoutManager.setFixedHeight((view as ToggleButton).isChecked)
            layoutManager.requestLayout()
        }
    }
}
