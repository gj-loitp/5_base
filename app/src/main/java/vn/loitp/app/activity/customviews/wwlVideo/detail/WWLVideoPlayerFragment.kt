package vn.loitp.app.activity.customviews.wwlVideo.detail

import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFragment
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.wwlVideo.utils.WWLVideoDataset

@LogTag("DatabaseFirebaseSignInActivity")
class WWLVideoPlayerFragment : BaseFragment() {
    private var mUrl: String? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.wwl_video_player_fragment
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
