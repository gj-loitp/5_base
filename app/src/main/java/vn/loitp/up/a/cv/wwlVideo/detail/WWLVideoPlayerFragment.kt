package vn.loitp.up.a.cv.wwlVideo.detail

import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import vn.loitp.R
import vn.loitp.up.a.cv.wwlVideo.utils.WWLVideoDataset

@LogTag("DatabaseFirebaseSignInActivity")
class WWLVideoPlayerFragment : BaseFragment() {
    private var mUrl: String? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.f_wwl_video_player
    }

    fun startPlay(item: WWLVideoDataset.DatasetItem) {
        mUrl = item.url
    }

    fun stopPlay() {}

    fun switchFullscreen(selected: Boolean) {
        showShortInformation("switchFullscreen $selected", true)
    }

    fun hideControls() {}

    fun toggleControls() {
        showShortInformation("toggleControls", true)
    }
}
