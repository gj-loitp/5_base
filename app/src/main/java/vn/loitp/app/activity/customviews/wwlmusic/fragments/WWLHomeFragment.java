package vn.loitp.app.activity.customviews.wwlmusic.fragments;

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
import vn.loitp.app.activity.customviews.wwlmusic.interfaces.FragmentHost;
import vn.loitp.app.activity.customviews.wwlmusic.utils.WWLMusicDataset;

@LogTag("WWLHomeFragment")
public class WWLHomeFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private CustomAdapter mAdapter;
    private FragmentHost mFragmentHost;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.wwl_music_home_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mRecyclerView = getFrmRootView().findViewById(R.id.recyclerView);
        this.mLayoutManager = new GridLayoutManager(getActivity(), LWWLMusicUiUtil.getGridColumnCount(getResources()));
        this.mRecyclerView.setLayoutManager(mLayoutManager);
        this.mAdapter = new CustomAdapter(WWLMusicDataset.datasetItems);
        mRecyclerView.setAdapter(mAdapter);

        updateLayoutIfNeed();
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
            this.mLayoutManager.setSpanCount(LWWLMusicUiUtil.getGridColumnCount(getResources()));
        }
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    private void onItemClicked(WWLMusicDataset.DatasetItem item) {
        if (this.mFragmentHost != null) {
            this.mFragmentHost.goToDetail(item);
        }
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private WWLMusicDataset.DatasetItem[] mDataSet;

        public CustomAdapter(WWLMusicDataset.DatasetItem[] dataset) {
            this.mDataSet = dataset;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wwl_music_card_row_item, parent, false);
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
                v.setOnClickListener(v1 -> WWLHomeFragment.this.onItemClicked(CustomAdapter.this.mDataSet[getAdapterPosition()]));
                titleView = v.findViewById(R.id.li_title);
                subtitleView = v.findViewById(R.id.li_subtitle);
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
