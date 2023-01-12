package vn.loitp.a.cv.wwlMusic

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.views.wwl.music.layout.LWWLMusic
import com.loitp.views.wwl.music.utils.LWWLMusicUiUtil.hideSystemUI
import com.loitp.views.wwl.music.utils.LWWLMusicUiUtil.showSystemUI
import com.loitp.views.wwl.music.utils.LWWLMusicUiUtil.updateStatusBarAlpha
import kotlinx.android.synthetic.main.a_wwl_music.*
import vn.loitp.R
import vn.loitp.a.cv.wwlMusic.frm.WWLPlaylistFragment
import vn.loitp.a.cv.wwlMusic.frm.WWLWatchFragment
import vn.loitp.a.cv.wwlMusic.itf.FragmentHost
import vn.loitp.a.cv.wwlMusic.utils.WWLMusicDataset

// https://github.com/vn-ttinc/Youtube-Watch-While-Layout
@LogTag("WWLActivityMusic")
@IsFullScreen(false)
class WWLActivityMusic : BaseActivityFont(), LWWLMusic.Listener, FragmentHost {

    private var mLastAlpha = 0f
    private var frmWatch: WWLWatchFragment? = null
    private var frmPlaylist: WWLPlaylistFragment? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.a_wwl_music
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        watchWhileLayout.setListener(this)
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
        if (watchWhileLayout.mState == LWWLMusic.STATE_MINIMIZED) {
            watchWhileLayout.maximize(false)
        }
        if (watchWhileLayout.mState == LWWLMusic.STATE_MAXIMIZED) {
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
        if (watchWhileLayout.mState == LWWLMusic.STATE_HIDED) {
            watchWhileLayout.mState = LWWLMusic.STATE_MAXIMIZED
            watchWhileLayout.mIsFullscreen = false

            watchWhileLayout.apply {
                if (this.canAnimate()) {
                    this.setAnimatePos(this.mMaxY)
                }
                this.reset()
            }
        }
        watchWhileLayout.maximize(false)
        frmWatch?.startPlay(item)
        frmPlaylist?.updateItem(item)
    }

    override fun onVideoCollapse() {
        showSystemUI(this)
        watchWhileLayout.exitFullscreenToMinimize()
        frmWatch?.switchFullscreen(false)
        watchWhileLayout.minimize(false)
    }

    override fun onVideoFullscreen(selected: Boolean) {
        if (selected) {
            hideSystemUI(this)
            watchWhileLayout.enterFullscreen()
        } else {
            showSystemUI(this)
            watchWhileLayout.exitFullscreen()
        }
        frmWatch?.switchFullscreen(selected)
    }
}
