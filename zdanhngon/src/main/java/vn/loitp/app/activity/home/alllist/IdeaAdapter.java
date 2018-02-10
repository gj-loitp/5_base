package vn.loitp.app.activity.home.alllist;

/**
 * Created by www.muathu@gmail.com on 12/8/2017.
 */

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.app.model.Idea;
import vn.loitp.app.util.AppUtil;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LUIUtil;

public class IdeaAdapter extends RecyclerView.Adapter<IdeaAdapter.MovieViewHolder> {

    public interface Callback {
        public void onClick(Idea idea, int position);

        public void onLongClick(Idea idea, int position);

        public void onClickShare(Idea idea, int position);

        public void onClickFav(Idea idea, int position);

        public void onLoadMore();
    }

    private Callback callback;
    private Context context;
    private List<Idea> ideaList;

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView tvContent;
        public TextView tvAuthor;
        public RelativeLayout rootView;
        public ImageView btShare;
        public ImageView btFav;

        public MovieViewHolder(View view) {
            super(view);
            tvContent = (TextView) view.findViewById(R.id.tv_content);
            tvAuthor = (TextView) view.findViewById(R.id.tv_author);
            rootView = (RelativeLayout) view.findViewById(R.id.root_view);
            btShare = (ImageView) view.findViewById(R.id.bt_share);
            btFav = (ImageView) view.findViewById(R.id.bt_fav);
        }
    }


    public IdeaAdapter(Context context, List<Idea> ideaList, Callback callback) {
        this.context = context;
        this.ideaList = ideaList;
        this.callback = callback;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_idea, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Idea idea = ideaList.get(position);
        holder.tvContent.setText(idea.getContent());

        holder.tvAuthor.setBackgroundColor(AppUtil.getColor(context));
        holder.tvAuthor.setText(idea.getAuthor());
        LUIUtil.setTextShadow(holder.tvAuthor);

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClick(idea, position);
                }
            }
        });
        holder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (callback != null) {
                    callback.onLongClick(idea, position);
                }
                return true;
            }
        });
        holder.btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LAnimationUtil.play(holder.btShare, Techniques.RotateIn, new LAnimationUtil.Callback() {
                    @Override
                    public void onCancel() {
                        //do nothing
                    }

                    @Override
                    public void onEnd() {
                        if (callback != null) {
                            callback.onClickShare(idea, position);
                        }
                    }

                    @Override
                    public void onRepeat() {
                        //do nothing
                    }

                    @Override
                    public void onStart() {
                        //do nothing
                    }
                });
            }
        });
        if (idea.getIsFav() == Constants.IS_FAV) {
            holder.btFav.setColorFilter(ContextCompat.getColor(context, R.color.LightPink));
        } else {
            holder.btFav.setColorFilter(ContextCompat.getColor(context, R.color.LightGrey));
        }
        holder.btFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LAnimationUtil.play(holder.btFav, Techniques.RotateIn, new LAnimationUtil.Callback() {
                    @Override
                    public void onCancel() {
                        //do nothing
                    }

                    @Override
                    public void onEnd() {
                        if (callback != null) {
                            callback.onClickFav(idea, position);
                        }
                    }

                    @Override
                    public void onRepeat() {
                        //do nothing
                    }

                    @Override
                    public void onStart() {
                        //do nothing
                    }
                });
            }
        });
        if (position == ideaList.size() - 1) {
            if (callback != null) {
                callback.onLoadMore();
            }
        }
    }

    @Override
    public int getItemCount() {
        return ideaList == null ? 0 : ideaList.size();
    }
}