package vn.loitp.app.activity.customviews.wwlmusic.interfaces

import vn.loitp.app.activity.customviews.wwlmusic.utils.WWLMusicDataset

/**
 * Created by thangn on 2/26/17.
 */

interface FragmentHost {
    fun goToDetail(item: WWLMusicDataset.DatasetItem)

    fun onVideoCollapse()

    fun onVideoFullscreen(selected: Boolean)
}
