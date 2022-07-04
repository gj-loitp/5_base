package vn.loitp.app.activity.customviews.wwlMusic.interfaces

import vn.loitp.app.activity.customviews.wwlMusic.utils.WWLMusicDataset

interface FragmentHost {
    fun goToDetail(item: WWLMusicDataset.DatasetItem)

    fun onVideoCollapse()

    fun onVideoFullscreen(selected: Boolean)
}
