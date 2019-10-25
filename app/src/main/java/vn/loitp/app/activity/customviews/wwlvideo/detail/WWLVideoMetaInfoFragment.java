package vn.loitp.app.activity.customviews.wwlvideo.detail;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.wwlvideo.utils.WWLVideoDataset;

/**
 * Created by thangn on 2/26/17.
 */
public class WWLVideoMetaInfoFragment extends Fragment {
    private TextView mTitleView;
    private TextView mSubTitleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wwl_video_meta_info_fragment, container, false);
        this.mTitleView = rootView.findViewById(R.id.li_title);
        this.mSubTitleView = rootView.findViewById(R.id.li_subtitle);
        return rootView;
    }

    public void updateItem(WWLVideoDataset.DatasetItem item) {
        if (this.mTitleView != null) {
            this.mTitleView.setText(item.title);
            this.mSubTitleView.setText(item.subtitle);
        }
    }
}
