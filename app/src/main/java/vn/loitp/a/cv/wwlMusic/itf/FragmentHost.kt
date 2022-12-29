package vn.loitp.a.cv.wwlMusic.itf

import vn.loitp.a.cv.wwlMusic.utils.WWLMusicDataset

interface FragmentHost {
    fun goToDetail(item: WWLMusicDataset.DatasetItem)

    fun onVideoCollapse()

    fun onVideoFullscreen(selected: Boolean)
}
