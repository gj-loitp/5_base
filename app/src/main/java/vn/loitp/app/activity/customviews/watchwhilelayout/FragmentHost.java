package vn.loitp.app.activity.customviews.watchwhilelayout;

import vn.loitp.app.activity.customviews.watchwhilelayout.utils.WWLMusicDataset;

/**
 * Created by thangn on 2/26/17.
 */

public interface FragmentHost {
    void goToDetail(WWLMusicDataset.DatasetItem item);

    void onVideoCollapse();

    void onVideoFullscreen(boolean selected);
}
