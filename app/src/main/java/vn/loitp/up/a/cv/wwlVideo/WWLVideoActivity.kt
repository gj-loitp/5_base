package vn.loitp.up.a.cv.wwlVideo

import android.graphics.Color
import android.os.Bundle
import android.widget.FrameLayout
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivity
import com.loitp.core.common.NOT_FOUND
import com.loitp.views.wwl.music.utils.LWWLMusicUiUtil
import com.loitp.views.wwl.music.utils.LWWLMusicViewHelper
import com.loitp.views.wwl.video.LWWLVideo
import vn.loitp.R
import vn.loitp.databinding.AWwlVideoBinding
import vn.loitp.up.a.cv.wwlVideo.detail.WWLVideoMetaInfoFragment
import vn.loitp.up.a.cv.wwlVideo.detail.WWLVideoPlayerFragment
import vn.loitp.up.a.cv.wwlVideo.detail.WWLVideoUpNextFragment
import vn.loitp.up.a.cv.wwlVideo.itf.FragmentHost
import vn.loitp.up.a.cv.wwlVideo.utils.WWLVideoDataset
import kotlin.math.max
import kotlin.math.min

// https://github.com/vn-ttinc/Youtube-Watch-While-Layout
@LogTag("WWLVideoActivity")
@IsFullScreen(false)
class WWLVideoActivity : BaseActivity(), LWWLVideo.Listener, FragmentHost {
    private var mLastAlpha = 0f
    private var fmrPlayerFragmentContainer: FrameLayout? = null
    private var frmPlayer: WWLVideoPlayerFragment? = null
    private var frmUpNext: WWLVideoUpNextFragment? = null
    private var frmMetaInfo: WWLVideoMetaInfoFragment? = null
    private lateinit var binding: AWwlVideoBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AWwlVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.watchWhileLayout.setListener(this)
        frmPlayer =
            supportFragmentManager.findFragmentById(R.id.frmPlayer) as WWLVideoPlayerFragment?
        frmUpNext =
            supportFragmentManager.findFragmentById(R.id.frmUpNext) as WWLVideoUpNextFragment?
        frmMetaInfo =
            supportFragmentManager.findFragmentById(R.id.frmMetaInfo) as WWLVideoMetaInfoFragment?
    }

    override fun WWL_onSliding(offset: Float) {
        val alpha: Float
        if (offset > 2.0f) {
            alpha = mLastAlpha * (3.0f - offset)
        } else {
            alpha = if (offset > 1.0f) {
                0.25f + 0.75f * (2.0f - offset)
            } else {
                1.0f
            }
            if (offset in 0.0f..1.0f) {
                updateStatusBarAlpha(1.0f - offset)
            }
        }
        updatePlayerAlpha(alpha)
        mLastAlpha = alpha
    }

    override fun WWL_onClicked() {
        if (binding.watchWhileLayout.mState == LWWLVideo.STATE_MINIMIZED) {
            binding.watchWhileLayout.maximize(false)
        }
        if (binding.watchWhileLayout.mState == LWWLVideo.STATE_MAXIMIZED) {
            frmPlayer?.toggleControls()
        }
    }

    override fun WWL_onHided() {
        frmPlayer?.stopPlay()
    }

    override fun onDestroy() {
        frmPlayer?.stopPlay()
        super.onDestroy()
    }

    override fun WWL_minimized() {
        mLastAlpha = 0.0f
        frmPlayer?.hideControls()
    }

    override fun WWL_maximized() {
        mLastAlpha = 1.0f
    }

    override fun goToDetail(item: WWLVideoDataset.DatasetItem) {
        if (binding.watchWhileLayout.mState == LWWLVideo.STATE_HIDED) {
            binding.watchWhileLayout.mState = LWWLVideo.STATE_MAXIMIZED
            binding.watchWhileLayout.mIsFullscreen = false
            if (binding.watchWhileLayout.canAnimate()) {
                binding.watchWhileLayout.setAnimatePos(binding.watchWhileLayout.mMaxY)
            }
            binding.watchWhileLayout.reset()
        }
        binding.watchWhileLayout.maximize(false)
        frmPlayer?.startPlay(item)
        frmUpNext?.updateItem(item)
        frmMetaInfo?.updateItem(item)
    }

    override fun onVideoCollapse() {
        LWWLMusicUiUtil.showSystemUI(this)
        binding.watchWhileLayout.exitFullscreenToMinimize()
        frmPlayer?.switchFullscreen(false)
        binding.watchWhileLayout.minimize(false)
    }

    override fun onVideoFullscreen(selected: Boolean) {
        if (selected) {
            LWWLMusicUiUtil.hideSystemUI(this)
            binding.watchWhileLayout.enterFullscreen()
        } else {
            LWWLMusicUiUtil.showSystemUI(this)
            binding.watchWhileLayout.exitFullscreen()
        }
        frmPlayer?.switchFullscreen(selected)
    }

    private fun updateStatusBarAlpha(alpha: Float) {
        val color = getColor(R.color.colorPrimaryDark)
        val color2 = Color.BLACK
        val color3 =
            LWWLMusicViewHelper.evaluateColorAlpha(max(0.0f, min(1.0f, alpha)), color, color2)
        window.statusBarColor = color3
    }

    private fun updatePlayerAlpha(alpha: Float) {
        fmrPlayerFragmentContainer?.alpha = alpha
    }
}
