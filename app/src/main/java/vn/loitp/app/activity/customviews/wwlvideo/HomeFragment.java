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

import com.core.base.BaseFragment;
import com.views.wwlmusic.utils.WWLMusicUiUtil;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.wwlvideo.interfaces.FragmentHost;
import vn.loitp.app.activity.customviews.wwlvideo.utils.WWLVideoDataset;

/**
 * Created by thangn on 2/26/17.
 */

public class HomeFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private CustomAdapter mAdapter;
    private FragmentHost mFragmentHost;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mRecyclerView = (RecyclerView) getFrmRootView().findViewById(R.id.recyclerView);
        this.mLayoutManager = new GridLayoutManager(getActivity(), WWLMusicUiUtil.getGridColumnCount(getResources()));
        this.mRecyclerView.setLayoutManager(mLayoutManager);
        //this.mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.card_spacing), true));
        //this.mRecyclerView.scrollToPosition(0);

        this.mAdapter = new CustomAdapter(WWLVideoDataset.datasetItems);
        mRecyclerView.setAdapter(mAdapter);

        updateLayoutIfNeed();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.wwl_video_home_fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mFragmentHost = (FragmentHost) activity;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateLayoutIfNeed();
    }

    private void updateLayoutIfNeed() {
        if (this.mLayoutManager != null) {
            this.mLayoutManager.setSpanCount(WWLMusicUiUtil.getGridColumnCount(getResources()));
        }
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    private void onItemClicked(WWLVideoDataset.DatasetItem item) {
        if (this.mFragmentHost != null) {
            this.mFragmentHost.goToDetail(item);
        }
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected String setTag() {
        return getClass().getSimpleName();
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
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HomeFragment.this.onItemClicked(CustomAdapter.this.mDataSet[getAdapterPosition()]);
                    }
                });
                titleView = (TextView) v.findViewById(R.id.li_title);
                subtitleView = (TextView) v.findViewById(R.id.li_subtitle);
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
