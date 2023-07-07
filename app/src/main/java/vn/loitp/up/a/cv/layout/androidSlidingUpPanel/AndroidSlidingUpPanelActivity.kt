package vn.loitp.up.a.cv.layout.androidSlidingUpPanel

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.sothree.slidinguppanel.PanelSlideListener
import com.sothree.slidinguppanel.PanelState
import vn.loitp.R
import vn.loitp.databinding.AAndroidSlidingUpPanelBinding
import java.util.*

@LogTag("AndroidSlidingUpPanelActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class AndroidSlidingUpPanelActivity : BaseActivityFont() {
    private lateinit var binding: AAndroidSlidingUpPanelBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAndroidSlidingUpPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/hannesa2/AndroidSlidingUpPanel"
                    )
                })
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = AndroidSlidingUpPanelActivity::class.java.simpleName
        }

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            showShortInformation("onItemClick")
        }
        val yourArrayList = listOf(
            "This",
            "Is",
            "An",
            "Example",
            "ListView",
            "That",
            "You",
            "Can",
            "Scroll",
            ".",
            "It",
            "Shows",
            "How",
            "Any",
            "Scrollable",
            "View",
            "Can",
            "Be",
            "Included",
            "As",
            "A",
            "Child",
            "Of",
            "SlidingUpPanelLayout"
        )
        val arrayAdapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, yourArrayList
        )
        binding.listView.adapter = arrayAdapter
        binding.slidingUpPanelLayout.addPanelSlideListener(object : PanelSlideListener {
            override fun onPanelSlide(panel: View, slideOffset: Float) {
            }

            override fun onPanelStateChanged(
                panel: View, previousState: PanelState, newState: PanelState
            ) {
            }
        })
        binding.slidingUpPanelLayout.setFadeOnClickListener {
            binding.slidingUpPanelLayout.panelState = PanelState.COLLAPSED
        }

        binding.btActionToggle.setSafeOnClickListener {
            if (binding.slidingUpPanelLayout.panelState != PanelState.HIDDEN) {
                binding.slidingUpPanelLayout.panelState = PanelState.HIDDEN
            } else {
                binding.slidingUpPanelLayout.panelState = PanelState.COLLAPSED
            }
        }
        binding.btActionAnchor.setSafeOnClickListener {
            if (binding.slidingUpPanelLayout.anchorPoint == 1.0f) {
                binding.slidingUpPanelLayout.anchorPoint = 0.7f
                binding.slidingUpPanelLayout.panelState = PanelState.ANCHORED
            } else {
                binding.slidingUpPanelLayout.anchorPoint = 1.0f
                binding.slidingUpPanelLayout.panelState = PanelState.COLLAPSED
            }
        }
    }

    override fun onBaseBackPressed() {
        if ((binding.slidingUpPanelLayout.panelState == PanelState.EXPANDED || binding.slidingUpPanelLayout.panelState == PanelState.ANCHORED)) {
            binding.slidingUpPanelLayout.panelState = PanelState.COLLAPSED
        } else {
            super.onBaseBackPressed()
        }
    }

}
