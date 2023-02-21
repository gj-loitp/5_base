package vn.loitp.up.a.cv.layout.circularView

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.layout.circularView.CircularView
import com.loitp.views.layout.circularView.Marker
import vn.loitp.R
import vn.loitp.databinding.ALayoutCircularViewBinding

@LogTag("CircularViewActivity")
@IsFullScreen(false)
class CircularViewActivity : BaseActivityFont() {
    private lateinit var binding: ALayoutCircularViewBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALayoutCircularViewBinding.inflate(layoutInflater)
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
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/sababado/CircularView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = CircularViewActivity::class.java.simpleName
        }
        val simpleCircularViewAdapter = SimpleCircularViewAdapter()
        binding.circularView.adapter = simpleCircularViewAdapter
        binding.circularView.setOnCircularViewObjectClickListener(object :
            CircularView.OnClickListener {
            override fun onClick(view: CircularView, isLongClick: Boolean) {
                showShortInformation("onClick")
            }

            override fun onMarkerClick(
                view: CircularView,
                marker: Marker,
                position: Int,
                isLongClick: Boolean
            ) {
                showShortInformation("onClick $position")
            }
        })
    }
}
