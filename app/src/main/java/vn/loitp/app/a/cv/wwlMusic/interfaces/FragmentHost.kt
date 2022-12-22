package vn.loitp.app.a.cv.wwlMusic.interfaces

import vn.loitp.app.a.cv.wwlMusic.utils.WWLMusicDataset

interface FragmentHost {
    fun goToDetail(item: WWLMusicDataset.DatasetItem)

    fun onVideoCollapse()

    fun onVideoFullscreen(selected: Boolean)
}
