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
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.app.model.Msg;
import vn.loitp.app.util.AppUtil;
import vn.loitp.core.utilities.LAnimationUtil;

public class IdeaAdapter extends RecyclerView.Adapter<IdeaAdapter.MovieViewHolder> {

    public interface Callback {
        public void onClickCopy(Msg msg, int position);

        public void onClickShare(Msg msg, int position);

        public void onClickFav(Msg msg, int position);

        public void onLoadMore();
    }

    private Callback callback;
    private Context context;
    private List<Msg> msgList;

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView tvContent;
        public TextView tvAuthor;
        public ImageView btCopy;
        public ImageView btShare;
        public ImageView btFav;

        public MovieViewHolder(View view) {
            super(view);
            tvContent = (TextView) view.findViewById(R.id.tv_content);
            tvAuthor = (TextView) view.findViewById(R.id.tv_author);
            btCopy = (ImageView) view.findViewById(R.id.bt_copy);
            btShare = (ImageView) view.findViewById(R.id.bt_share);
            btFav = (ImageView) view.findViewById(R.id.bt_fav);
        }
    }


    public IdeaAdapter(Context context, List<Msg> msgList, Callback callback) {
        this.context = context;
        this.msgList = msgList;
        this.callback = callback;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Msg msg = msgList.get(position);
        holder.tvContent.setText(msg.getContent());
        int color = AppUtil.getColor(context);
        //holder.tvContent.setTextColor(color);

        holder.tvAuthor.setBackgroundColor(color);
        //LUIUtil.setTextShadow(holder.tvAuthor);

        holder.btCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LAnimationUtil.play(holder.btCopy, Techniques.RotateIn, new LAnimationUtil.Callback() {
                    @Override
                    public void onCancel() {
                        //do nothing
                    }

                    @Override
                    public void onEnd() {
                        if (callback != null) {
                            callback.onClickCopy(msg, position);
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
                            callback.onClickShare(msg, position);
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
        if (msg.getIsFav() == Constants.IS_FAV) {
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
                            callback.onClickFav(msg, position);
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
        if (position == msgList.size() - 1) {
            if (callback != null) {
                callback.onLoadMore();
            }
        }
    }

    @Override
    public int getItemCount() {
        return msgList == null ? 0 : msgList.size();
    }
}