package vn.loitp.up.a.cv.wwlMusic

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.views.wwl.music.layout.LWWLMusic
import com.loitp.views.wwl.music.utils.LWWLMusicUiUtil.hideSystemUI
import com.loitp.views.wwl.music.utils.LWWLMusicUiUtil.showSystemUI
import com.loitp.views.wwl.music.utils.LWWLMusicUiUtil.updateStatusBarAlpha
import vn.loitp.R
import vn.loitp.databinding.AWwlMusicBinding
import vn.loitp.up.a.cv.wwlMusic.frm.WWLPlaylistFragment
import vn.loitp.up.a.cv.wwlMusic.frm.WWLWatchFragment
import vn.loitp.up.a.cv.wwlMusic.itf.FragmentHost
import vn.loitp.up.a.cv.wwlMusic.utils.WWLMusicDataset

// https://github.com/vn-ttinc/Youtube-Watch-While-Layout
@LogTag("WWLActivityMusic")
@IsFullScreen(false)
class WWLActivityMusic : BaseActivityFont(), LWWLMusic.Listener, FragmentHost {
    private lateinit var binding: AWwlMusicBinding
    private var mLastAlpha = 0f
    private var frmWatch: WWLWatchFragment? = null
    private var frmPlaylist: WWLPlaylistFragment? = null

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AWwlMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.watchWhileLayout.setListener(this)
        frmWatch = supportFragmentManager.findFragmentById(R.id.frmWatch) as WWLWatchFragment?
        frmPlaylist =
            supportFragmentManager.findFragmentById(R.id.frmPlaylist) as WWLPlaylistFragment?
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
                updateStatusBarAlpha(activity = this, alpha = 1.0f - offset)
            }
        }
        mLastAlpha = alpha
    }

    override fun WWL_onClicked() {
        if (binding.watchWhileLayout.mState == LWWLMusic.STATE_MINIMIZED) {
            binding.watchWhileLayout.maximize(false)
        }
        if (binding.watchWhileLayout.mState == LWWLMusic.STATE_MAXIMIZED) {
            frmWatch?.toggleControls()
        }
    }

    override fun WWL_onHided() {
        frmWatch?.stopPlay()
    }

    override fun onDestroy() {
        frmWatch?.stopPlay()
        super.onDestroy()
    }

    override fun WWL_minimized() {
        mLastAlpha = 0.0f
        frmWatch?.hideControls()
    }

    override fun WWL_maximized() {
        mLastAlpha = 1.0f
    }

    override fun goToDetail(item: WWLMusicDataset.DatasetItem) {
        if (binding.watchWhileLayout.mState == LWWLMusic.STATE_HIDED) {
            binding.watchWhileLayout.mState = LWWLMusic.STATE_MAXIMIZED
            binding.watchWhileLayout.mIsFullscreen = false

            binding.watchWhileLayout.apply {
                if (this.canAnimate()) {
                    this.setAnimatePos(this.mMaxY)
                }
                this.reset()
            }
        }
        binding.watchWhileLayout.maximize(false)
        frmWatch?.startPlay(item)
        frmPlaylist?.updateItem(item)
    }

    override fun onVideoCollapse() {
        showSystemUI(this)
        binding.watchWhileLayout.exitFullscreenToMinimize()
        frmWatch?.switchFullscreen(false)
        binding.watchWhileLayout.minimize(false)
    }

    override fun onVideoFullscreen(selected: Boolean) {
        if (selected) {
            hideSystemUI(this)
            binding.watchWhileLayout.enterFullscreen()
        } else {
            showSystemUI(this)
            binding.watchWhileLayout.exitFullscreen()
        }
        frmWatch?.switchFullscreen(selected)
    }
}
