package vn.loitp.app.a.cv.wwlVideo.itf

import vn.loitp.app.a.cv.wwlVideo.utils.WWLVideoDataset

interface FragmentHost {
    fun goToDetail(item: WWLVideoDataset.DatasetItem)

    fun onVideoCollapse()

    fun onVideoFullscreen(selected: Boolean)
}
