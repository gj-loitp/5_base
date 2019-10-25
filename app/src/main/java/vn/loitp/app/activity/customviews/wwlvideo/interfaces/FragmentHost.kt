package vn.loitp.app.activity.customviews.wwlvideo.interfaces

import vn.loitp.app.activity.customviews.wwlvideo.utils.WWLVideoDataset

/**
 * Created by thangn on 2/26/17.
 */

interface FragmentHost {
    fun goToDetail(item: WWLVideoDataset.DatasetItem)

    fun onVideoCollapse()

    fun onVideoFullscreen(selected: Boolean)
}
