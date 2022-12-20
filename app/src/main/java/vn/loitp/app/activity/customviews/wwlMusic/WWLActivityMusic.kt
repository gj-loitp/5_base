package vn.loitp.app.activity.customviews.wwlMusic

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.views.wwlMusic.layout.LWWLMusic
import com.loitpcore.views.wwlMusic.utils.LWWLMusicUiUtil.hideSystemUI
import com.loitpcore.views.wwlMusic.utils.LWWLMusicUiUtil.showSystemUI
import com.loitpcore.views.wwlMusic.utils.LWWLMusicUiUtil.updateStatusBarAlpha
import kotlinx.android.synthetic.main.activity_wwl_music.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.wwlMusic.fragments.WWLPlaylistFragment
import vn.loitp.app.activity.customviews.wwlMusic.fragments.WWLWatchFragment
import vn.loitp.app.activity.customviews.wwlMusic.interfaces.FragmentHost
import vn.loitp.app.activity.customviews.wwlMusic.utils.WWLMusicDataset

// https://github.com/vn-ttinc/Youtube-Watch-While-Layout
@LogTag("WWLActivityMusic")
@IsFullScreen(false)
class WWLActivityMusic : BaseFontActivity(), LWWLMusic.Listener, FragmentHost {

    private var mLastAlpha = 0f
    private var frmWatch: WWLWatchFragment? = null
    private var frmPlaylist: WWLPlaylistFragment? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_wwl_music
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
