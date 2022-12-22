package vn.loitp.app.a.customviews.wwlMusic.interfaces

import vn.loitp.app.a.customviews.wwlMusic.utils.WWLMusicDataset

interface FragmentHost {
    fun goToDetail(item: WWLMusicDataset.DatasetItem)

    fun onVideoCollapse()

    fun onVideoFullscreen(selected: Boolean)
}
