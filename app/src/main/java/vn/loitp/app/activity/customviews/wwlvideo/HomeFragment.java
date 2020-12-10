package vn.loitp.app.activity.customviews.wwlvideo;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annotation.LogTag;
import com.core.base.BaseFragment;
import com.views.wwlmusic.utils.LWWLMusicUiUtil;

import vn.loitp.app.R;
import vn.loitp.app.activity.customviews.wwlvideo.interfaces.FragmentHost;
import vn.loitp.app.activity.customviews.wwlvideo.utils.WWLVideoDataset;

//TODO convert kotlin
@LogTag("HomeFragment")
public class HomeFragment extends BaseFragment {
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter customAdapter;
    private FragmentHost fragmentHost;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.wwl_video_home_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mRecyclerView = getFrmRootView().findViewById(R.id.recyclerView);
        this.gridLayoutManager = new GridLayoutManager(getActivity(), LWWLMusicUiUtil.getGridColumnCount(getResources()));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        //this.mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.card_spacing), true));
        //this.mRecyclerView.scrollToPosition(0);

        this.customAdapter = new CustomAdapter(WWLVideoDataset.datasetItems);
        mRecyclerView.setAdapter(customAdapter);

        updateLayoutIfNeed();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.fragmentHost = (FragmentHost) activity;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateLayoutIfNeed();
    }

    private void updateLayoutIfNeed() {
        if (this.gridLayoutManager != null) {
            this.gridLayoutManager.setSpanCount(LWWLMusicUiUtil.getGridColumnCount(getResources()));
        }
        if (this.customAdapter != null) {
            this.customAdapter.notifyDataSetChanged();
        }
    }

    private void onItemClicked(WWLVideoDataset.DatasetItem item) {
        if (this.fragmentHost != null) {
            this.fragmentHost.goToDetail(item);
        }
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private WWLVideoDataset.DatasetItem[] mDataSet;

        public CustomAdapter(WWLVideoDataset.DatasetItem[] dataset) {
            this.mDataSet = dataset;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wwl_video_card_row_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.getTitleView().setText(this.mDataSet[position].title);
            holder.getSubTitleView().setText(this.mDataSet[position].subtitle);
        }

        @Override
        public int getItemCount() {
            return this.mDataSet.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView titleView;
            private final TextView subtitleView;

            public ViewHolder(View v) {
                super(v);
                v.setOnClickListener(v1 -> HomeFragment.this.onItemClicked(CustomAdapter.this.mDataSet[getAdapterPosition()]));
                titleView = v.findViewById(R.id.liTitle);
                subtitleView = v.findViewById(R.id.liSubtitle);
            }

            public TextView getTitleView() {
                return titleView;
            }

            public TextView getSubTitleView() {
                return subtitleView;
            }
        }
    }
}
