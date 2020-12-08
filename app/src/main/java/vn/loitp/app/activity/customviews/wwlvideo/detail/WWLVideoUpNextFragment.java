package vn.loitp.app.activity.customviews.wwlvideo.detail;

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

import org.jetbrains.annotations.NotNull;

import vn.loitp.app.R;
import vn.loitp.app.activity.customviews.wwlvideo.interfaces.FragmentHost;
import vn.loitp.app.activity.customviews.wwlvideo.utils.WWLVideoDataset;

//TODO convert kotlin
@LogTag("WWLVideoUpNextFragment")
public class WWLVideoUpNextFragment extends BaseFragment {
    private FragmentHost mFragmentHost;
    private GridLayoutManager mLayoutManager;
    private CustomAdapter mAdapter;
    private boolean mIsLandscape;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.wwl_video_up_next_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mRecyclerView = getFrmRootView().findViewById(R.id.recyclerView);
        this.mLayoutManager = new GridLayoutManager(getActivity(), LWWLMusicUiUtil.getGridColumnCount(getResources()));
        mRecyclerView.setLayoutManager(mLayoutManager);
        //this.mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.card_spacing), true));
        //this.mRecyclerView.scrollToPosition(0);

        this.mAdapter = new CustomAdapter(WWLVideoDataset.datasetItems);
        mRecyclerView.setAdapter(mAdapter);

        this.mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return WWLVideoUpNextFragment.this.getSpanSize(position);
            }
        });

        updateLayoutIfNeed();
    }

    private int getSpanSize(int position) {
        int spanSize = this.mLayoutManager.getSpanCount();
        if (this.mIsLandscape) {
            return spanSize;
        }
        if (position == 0) {
            return spanSize;
        }
        position--;
        if (position == 0) {
            return spanSize;
        }
        return 1;
    }

    @Override
    public void onAttach(@NotNull Activity activity) {
        super.onAttach(activity);

        this.mFragmentHost = (FragmentHost) activity;
    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateLayoutIfNeed();
    }

    public void updateItem(WWLVideoDataset.DatasetItem item) {
        if (this.mAdapter != null) {
            this.mAdapter.updateHeader(item);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    private void updateLayoutIfNeed() {
        boolean enable = true;
        int orientation = getResources().getConfiguration().orientation;
        this.mIsLandscape = orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (this.mIsLandscape) {
            enable = false;
        }
        if (this.mLayoutManager != null) {
            if (this.mIsLandscape) {
                this.mLayoutManager.setSpanCount(1);
            } else {
                this.mLayoutManager.setSpanCount(LWWLMusicUiUtil.getGridColumnCount(getResources()));
            }
        }
        if (this.mAdapter != null) {
            this.mAdapter.setHeader(enable);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    private void onItemClicked(WWLVideoDataset.DatasetItem item) {
        if (this.mFragmentHost != null) {
            this.mFragmentHost.goToDetail(item);
        }
    }

    public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int HEADER = 0;
        private static final int TITLE = 1;
        private static final int OTHER = 2;
        public boolean mHasHeader;
        private final String mTitle;
        private final WWLVideoDataset.DatasetItem[] mDataSet;
        private WWLVideoDataset.DatasetItem mHeaderItem;

        public CustomAdapter(WWLVideoDataset.DatasetItem[] dataset) {
            this.mDataSet = dataset;
            this.mHasHeader = true;
            this.mTitle = "Up next";
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
            if (viewType == HEADER) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wwl_video_card_header_item, parent, false);
                return new HeaderViewHolder(v);
            }
            if (viewType == TITLE) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wwl_video_card_title_item, parent, false);
                return new TitleViewHolder(v);
            }
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wwl_video_card_row_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof HeaderViewHolder) {
                if (this.mHeaderItem != null) {
                    ((HeaderViewHolder) holder).getTitleView().setText(this.mHeaderItem.title);
                    ((HeaderViewHolder) holder).getSubTitleView().setText(this.mHeaderItem.subtitle);
                }
            } else if (holder instanceof TitleViewHolder) {
                ((TitleViewHolder) holder).getTitleView().setText(this.mTitle);
            } else if (holder instanceof ViewHolder) {
                ((ViewHolder) holder).getTitleView().setText(getItem(position).title);
                ((ViewHolder) holder).getSubTitleView().setText(getItem(position).subtitle);
            }
        }

        @Override
        public int getItemCount() {
            return (mHasHeader ? 1 : 0) + 1 + this.mDataSet.length;
        }

        @Override
        public int getItemViewType(int position) {
            if (mHasHeader) {
                if (position == 0) {
                    return HEADER;
                }
                position--;
            }
            if (position == 0) {
                return TITLE;
            }
            return OTHER;
        }

        public void setHeader(boolean enable) {
            this.mHasHeader = enable;
        }

        public void updateHeader(WWLVideoDataset.DatasetItem item) {
            this.mHeaderItem = item;
        }

        private WWLVideoDataset.DatasetItem getItem(int position) {
            return this.mDataSet[position - 1 - (this.mHasHeader ? 1 : 0)];
        }

        class HeaderViewHolder extends RecyclerView.ViewHolder {
            private final TextView titleView;
            private final TextView subtitleView;

            public HeaderViewHolder(View v) {
                super(v);
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

        class TitleViewHolder extends RecyclerView.ViewHolder {
            private final TextView titleView;

            public TitleViewHolder(View v) {
                super(v);
                titleView = v.findViewById(R.id.li_title);
            }

            public TextView getTitleView() {
                return titleView;
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView titleView;
            private final TextView subtitleView;

            public ViewHolder(View v) {
                super(v);
                v.setOnClickListener(v1 -> WWLVideoUpNextFragment.this.onItemClicked(CustomAdapter.this.getItem(getAdapterPosition())));
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
