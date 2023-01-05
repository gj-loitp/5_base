package vn.loitp.a.cv.layout.androidSlidingUpPanel

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import com.sothree.slidinguppanel.PanelSlideListener
import com.sothree.slidinguppanel.PanelState
import kotlinx.android.synthetic.main.a_android_sliding_up_panel.*
import vn.loitp.R
import java.util.*

@LogTag("AndroidSlidingUpPanelActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class AndroidSlidingUpPanelActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_android_sliding_up_panel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(view = it, runnable = {
                    LSocialUtil.openUrlInBrowser(
                        context = context, url = "https://github.com/hannesa2/AndroidSlidingUpPanel"
                    )
                })
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = AndroidSlidingUpPanelActivityFont::class.java.simpleName
        }

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, _, _ ->
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
            this,
            android.R.layout.simple_list_item_1,
            yourArrayList
        )
        listView.adapter = arrayAdapter
        slidingUpPanelLayout.addPanelSlideListener(object : PanelSlideListener {
            override fun onPanelSlide(panel: View, slideOffset: Float) {
            }

            override fun onPanelStateChanged(
                panel: View,
                previousState: PanelState,
                newState: PanelState
            ) {
            }
        })
        slidingUpPanelLayout.setFadeOnClickListener {
            slidingUpPanelLayout.panelState = PanelState.COLLAPSED
        }

        btActionToggle.setSafeOnClickListener {
            if (slidingUpPanelLayout.panelState != PanelState.HIDDEN) {
                slidingUpPanelLayout.panelState = PanelState.HIDDEN
            } else {
                slidingUpPanelLayout.panelState = PanelState.COLLAPSED
            }
        }
        btActionAnchor.setSafeOnClickListener {
            if (slidingUpPanelLayout.anchorPoint == 1.0f) {
                slidingUpPanelLayout.anchorPoint = 0.7f
                slidingUpPanelLayout.panelState = PanelState.ANCHORED
            } else {
                slidingUpPanelLayout.anchorPoint = 1.0f
                slidingUpPanelLayout.panelState = PanelState.COLLAPSED
            }
        }
    }

    override fun onBaseBackPressed() {
        if ((slidingUpPanelLayout.panelState == PanelState.EXPANDED || slidingUpPanelLayout.panelState == PanelState.ANCHORED)) {
            slidingUpPanelLayout.panelState = PanelState.COLLAPSED
        } else {
            super.onBaseBackPressed()
        }
    }

}
