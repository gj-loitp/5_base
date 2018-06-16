package vn.loitp.app.activity.customviews.wwlvideo.interfaces;

import vn.loitp.app.activity.customviews.wwlvideo.utils.WWLVideoDataset;

/**
 * Created by thangn on 2/26/17.
 */

public interface FragmentHost {
    void goToDetail(WWLVideoDataset.DatasetItem item);

    void onVideoCollapse();

    void onVideoFullscreen(boolean selected);
}
