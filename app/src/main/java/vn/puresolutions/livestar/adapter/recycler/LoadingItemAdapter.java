package vn.puresolutions.livestar.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.base.RecyclerAdapter;
import vn.puresolutions.livestar.core.api.model.BaseModel;

/**
 * @author hoangphu
 * @since 8/20/16
 */
public abstract class LoadingItemAdapter<Model extends BaseModel, VH extends LoadingItemAdapter.ViewHolder>
        extends RecyclerAdapter<Model, VH> {

    public static final int NO_LOADING_POSITION = -1;
    protected int loadingPos = NO_LOADING_POSITION;

    public void setLoadingPos(int loadingPos) {
        this.loadingPos = loadingPos;
        notifyItemChanged(loadingPos);
    }

    public boolean isLoading() {
        return loadingPos != NO_LOADING_POSITION;
    }

    public void endLoading() {
        int pos = loadingPos;
        loadingPos = NO_LOADING_POSITION;
        notifyItemChanged(pos);
    }

    @Override
    protected void updateView(Model model, VH viewHolder, int position) {
        boolean isLoading = loadingPos == position;
        viewHolder.progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
        onLoadingModeChange(viewHolder, isLoading);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.loadingItem_pgbLoading)
        public ProgressBar progressBar;

        public ViewHolder(View container) {
            super(container);
            ButterKnife.bind(this, container);
        }
    }

    protected abstract void onLoadingModeChange(VH viewHolder, boolean isEnable);
}
