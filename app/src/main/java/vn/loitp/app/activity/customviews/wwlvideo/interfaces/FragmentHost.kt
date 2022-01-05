package vn.loitp.app.activity.customviews.wwlvideo.interfaces

import vn.loitp.app.activity.customviews.wwlvideo.utils.WWLVideoDataset

interface FragmentHost {
    fun goToDetail(item: WWLVideoDataset.DatasetItem)

    fun onVideoCollapse()

    fun onVideoFullscreen(selected: Boolean)
}
