package vn.loitp.app.activity.customviews.wwlvideo;

import vn.loitp.app.activity.customviews.wwlvideo.utils.Dataset;

/**
 * Created by thangn on 2/26/17.
 */

public interface FragmentHost {
    void goToDetail(Dataset.DatasetItem item);

    void onVideoCollapse();

    void onVideoFullscreen(boolean selected);
}
