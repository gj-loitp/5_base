package vn.loitp.app.activity.customviews.watchwhilelayout.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.watchwhilelayout.interfaces.FragmentHost;
import vn.loitp.app.activity.customviews.watchwhilelayout.utils.WWLMusicDataset;
import vn.loitp.core.base.BaseFragment;

/**
 * Created by thangn on 3/1/17.
 */

public class WWLPlaylistFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CustomAdapter mAdapter;
    private TextView mTitleView;
    private TextView mSubTitleView;
    private FragmentHost mFragmentHost;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mTitleView = (TextView) frmRootView.findViewById(R.id.li_title);
        this.mSubTitleView = (TextView) frmRootView.findViewById(R.id.li_subtitle);
        this.mRecyclerView = (RecyclerView) frmRootView.findViewById(R.id.recyclerView);
        this.mLayoutManager = new LinearLayoutManager(getActivity());
        this.mRecyclerView.setLayoutManager(mLayoutManager);
        this.mRecyclerView.scrollToPosition(0);

        this.mAdapter = new CustomAdapter(WWLMusicDataset.datasetItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.wwl_music_playlist_fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.mFragmentHost = (FragmentHost) activity;
    }

    private void onItemClicked(WWLMusicDataset.DatasetItem item) {
        if (this.mFragmentHost != null) {
            this.mFragmentHost.goToDetail(item);
        }
    }

    public void updateItem(WWLMusicDataset.DatasetItem item) {
        this.mTitleView.setText(item.title);
        this.mSubTitleView.setText(item.subtitle);
    }

    private class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final WWLMusicDataset.DatasetItem[] mDataSet;

        public CustomAdapter(WWLMusicDataset.DatasetItem[] datasetItems) {
            this.mDataSet = datasetItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wwl_playlist_item, parent, false);
            return new CustomAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder) holder).getTitleView().setText(this.mDataSet[position].title);
            ((ViewHolder) holder).getSubTitleView().setText(this.mDataSet[position].subtitle);
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
                        WWLPlaylistFragment.this.onItemClicked(CustomAdapter.this.mDataSet[getAdapterPosition()]);
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
