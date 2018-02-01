package vn.loitp.app.activity.home.favmanga;

/**
 * Created by www.muathu@gmail.com on 1/2/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.app.data.ComicData;
import vn.loitp.app.model.comic.Comic;
import vn.loitp.app.util.AppUtil;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.views.recyclerview.parallaxrecyclerviewyayandroid.ParallaxViewHolder;

public class ComicFavAdapter extends RecyclerView.Adapter<ComicFavAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;

    public ComicFavAdapter(Context context, Callback callback) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
    }

    public interface Callback {
        public void onClick(Comic comic, int position);

        public void onLongClick(Comic comic, int position);
    }

    private Callback callback;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ViewHolder(inflater.inflate(R.layout.item_comic, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.rootView.setBackgroundColor(AppUtil.getColor(context));
        LImageUtil.load((Activity) context, ComicData.getInstance().getComicFavList().get(position).getUrlImg(), viewHolder.getBackgroundImage());
        viewHolder.tvTitle.setText(ComicData.getInstance().getComicFavList().get(position).getTitle());
        viewHolder.tvDate.setText(ComicData.getInstance().getComicFavList().get(position).getDate());
        //viewHolder.ivIsFav.setVisibility(ComicData.getInstance().getComicFavList().get(position).isFav() == Constants.IS_FAV ? android.view.View.VISIBLE : android.view.View.GONE);
        viewHolder.ivIsFav.setVisibility(View.GONE);

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClick(ComicData.getInstance().getComicFavList().get(position), position);
                }
            }
        });
        viewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (callback != null) {
                    callback.onLongClick(ComicData.getInstance().getComicFavList().get(position), position);
                }
                return true;
            }
        });

        // # CAUTION:
        // Important to call this method
        viewHolder.getBackgroundImage().reuse();
    }

    @Override
    public int getItemCount() {
        return ComicData.getInstance().getComicFavList().size();
    }

    /**
     * # CAUTION:
     * ViewHolder must extend from ParallaxViewHolder
     */
    public static class ViewHolder extends ParallaxViewHolder {
        public RelativeLayout rootView;
        public ImageView ivIsFav;
        public TextView tvTitle;
        public TextView tvDate;
        public View view;

        public ViewHolder(View v) {
            super(v);
            rootView = (RelativeLayout) v.findViewById(R.id.root_view);
            ivIsFav = (ImageView) v.findViewById(R.id.iv_is_fav);
            tvTitle = (TextView) v.findViewById(R.id.tv_title);
            tvDate = (TextView) v.findViewById(R.id.tv_date);
            view = (View) v.findViewById(R.id.view);
        }

        @Override
        public int getParallaxImageId() {
            return R.id.backgroundImage;
        }

    }
}