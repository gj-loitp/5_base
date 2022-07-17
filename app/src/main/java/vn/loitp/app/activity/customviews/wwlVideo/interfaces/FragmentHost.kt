package vn.loitp.app.activity.customviews.wwlVideo.interfaces

import vn.loitp.app.activity.customviews.wwlVideo.utils.WWLVideoDataset

interface FragmentHost {
    fun goToDetail(item: WWLVideoDataset.DatasetItem)

    fun onVideoCollapse()

    fun onVideoFullscreen(selected: Boolean)
}
