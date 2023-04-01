package vn.loitp.up.a.cv.bb.bottomBar

import android.os.Bundle
import androidx.core.view.isVisible
import com.daimajia.androidanimations.library.Techniques
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.readTxtFromRawFolder
import com.loitp.core.ext.setDelay
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.bottomBar.LBottomBar
import vn.loitp.R
import vn.loitp.databinding.ABottomBarBlurBinding

@LogTag("BottomBarActivity")
@IsFullScreen(false)
class BottomBarActivity : BaseActivityFont() {
    private lateinit var binding: ABottomBarBlurBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ABottomBarBlurBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft?.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = BottomBarActivity::class.java.simpleName
        }
        binding.textView.text = readTxtFromRawFolder(nameOfRawFile = R.raw.loitp)
        with(binding.bottomBar) {
            paddingOnInDp = context.resources.getDimension(R.dimen.w_10).toInt()
            paddingOffInDp = context.resources.getDimension(R.dimen.margin_padding_medium).toInt()
            colorIvOn = R.color.red
            colorIvOff = R.color.pink
            setTextMarginBottom(context.resources.getDimension(R.dimen.w_5).toInt())
            setItem0(R.drawable.ic_bug_report_black_48dp, "Bug report")
            setItem1(R.drawable.ic_add_black_48dp, "Add")
            setItem2(R.drawable.ic_chat_black_48dp, "Chat")
            setItem3(R.drawable.ic_close_black_48dp, "Clear")
            setItem4(R.drawable.ic_cloud_download_black_48dp, "Cloud")
            setItem5(R.drawable.ic_picture_in_picture_alt_black_48dp, "Picture")
            setTechniques(Techniques.Pulse)
            setOnItemClick(object : LBottomBar.Callback {
                override fun onClickItem(position: Int) {
                    showShortInformation("Touch $position")
                }
            })
        }
        binding.btBlurViewRed.setOnClickListener {
            binding.bottomBar.getRealTimeBlurView().setOverlayColor(getColor(R.color.red50))
        }
        binding.btBlurViewGreen.setOnClickListener {
            binding.bottomBar.getRealTimeBlurView().setOverlayColor(getColor(R.color.green33))
        }
        binding.btCount1.setOnClickListener {
            binding.bottomBar.setCount(1)
        }
        binding.btCount3.setOnClickListener {
            binding.bottomBar.setCount(3)
        }
        binding.btCount5.setOnClickListener {
            binding.bottomBar.setCount(5)
        }
        binding.btCount6.setOnClickListener {
            binding.bottomBar.setCount(6)
        }
        binding.btShowText.setOnClickListener {
            binding.bottomBar.isAlwaysShowText = true
        }
        binding.btHideText.setOnClickListener {
            binding.bottomBar.isAlwaysShowText = false
        }
        setDelay(5000) {
            binding.bottomBar.setPerformItemClick(4)
        }
    }
}
