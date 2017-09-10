package vn.puresolutions.livestar.adapter.recycler;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.activity.RoomActivity;
import vn.puresolutions.livestar.activity.SearchActivity;
import vn.puresolutions.livestar.adapter.recycler.base.RecyclerAdapter;
import vn.puresolutions.livestar.core.api.model.Broadcaster;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;

/**
 * File created on 7/4/2017.
 *
 * @author Anhdv
 */

public class SearchResultAdapter extends RecyclerAdapter<Broadcaster,SearchResultAdapter.SearchResultViewHolder> {
    //List<Broadcaster> mlstObject = new ArrayList<>();
    private boolean reachToEnd;
    private boolean loadingMore;
    private SearchActivity.SearchLoadMoreListener listener;
    private Context mcontext;
    public SearchResultAdapter() {
        super();
    }
    public void setListener(SearchActivity.SearchLoadMoreListener listener) {
        this.listener = listener;
    }
    public void finishLoadingMore() {
        loadingMore = false;
        notifyDataSetChanged();
    }

    public void reachToEnd() {
        reachToEnd = true;
        notifyDataSetChanged();
    }

    @Override
    protected int getLayoutItem() {
        return R.layout.list_item_search_result;
    }

    @Override
    protected void updateView(Broadcaster model, SearchResultViewHolder viewHolder, int position) {
        if (isLastPosition(position)) {
            ///
            //viewHolder.prgLoading.setVisibility(View.VISIBLE);
            if (!loadingMore) {
                loadingMore = true;
                listener.onLoadingMore();
            }
        }else{
            //viewHolder.prgLoading.setVisibility(View.GONE);
            viewHolder.tvHeartOfIdol.setText(String.valueOf(items.get(position).getHeart()));
            viewHolder.tvUserInroom.setText(String.valueOf(items.get(position).getRoom().getTotalUser()));
            viewHolder.tvNameIDol.setText(items.get(position).getName());
           /* if (items.get(position).getRoom().isOnAir()){
                viewHolder.searchResulItem_lnlOnAir.setVisibility(View.VISIBLE);
            }else {
                viewHolder.searchResulItem_lnlOnAir.setVisibility(View.GONE);
            }*/
            LImageUtils.loadImage(viewHolder.imgBCCover, items.get(position).getRoom().getThumb());
        }
        //viewHolder.itemView.setOnClickListener(view -> startRoomScreen(view.getContext(), (View) view.getTag(), position));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    startRoomScreen(v.getContext(), (View) v.getTag(), position);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
       /* viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Room room = items.get(position).getRoom();
                startRoomScreen(v.getContext(),room);
            }
        });*/
    }
    private void startRoomScreen(Context context, View transitionView, int position) {
        Intent intent = new Intent(context, RoomActivity.class);
        intent.putExtra(RoomActivity.BUNDLE_KEY_ROOM, items.get(position).getRoom());
    /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions transitionActivityOptions = ActivityOptions
                    .makeSceneTransitionAnimation((Activity) context, transitionView, transitionView.getTransitionName());
            context.startActivity(intent, transitionActivityOptions.toBundle());
        } else {
            context.startActivity(intent);
        }*/
        context.startActivity(intent);
    }

    @Override
    protected SearchResultViewHolder createViewHolder(View container) {
         return new SearchResultAdapter.SearchResultViewHolder(container);
    }
    public int getModelSize() {
        return items.size();
    }
    public boolean isLastPosition(int position) {
        Log.i("SearchResultAdapter","position: "+position);
        Log.i("SearchResultAdapter","getModelSize: "+getModelSize());
        return position >= getModelSize();
    }

    @Override
    public Broadcaster getItem(int position) {
        if (isLastPosition(position)) {
            return null;
        } else {
            return super.getItem(position);
        }
    }

    public class SearchResultViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.searchResultItem_imgBCCover)
        SimpleDraweeView imgBCCover;
        @BindView(R.id.tvNameIdol)
        TextView tvNameIDol;
        @BindView(R.id.tvHeartOfIdol)
        TextView tvHeartOfIdol;
        @BindView(R.id.tvUserInroom)
        TextView tvUserInroom;
        @BindView(R.id.searchResultItem_prgLoading)
        ProgressBar prgLoading;
     /*   @BindView(R.id.searchResulItem_lnlOnAir)
        RelativeLayout searchResulItem_lnlOnAir;*/
        public SearchResultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
