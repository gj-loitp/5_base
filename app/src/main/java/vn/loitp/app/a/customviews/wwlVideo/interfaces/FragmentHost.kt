package vn.loitp.app.a.customviews.wwlVideo.interfaces

import vn.loitp.app.a.customviews.wwlVideo.utils.WWLVideoDataset

interface FragmentHost {
    fun goToDetail(item: WWLVideoDataset.DatasetItem)

    fun onVideoCollapse()

    fun onVideoFullscreen(selected: Boolean)
}
