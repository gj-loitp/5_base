package vn.loitp.app.activity.comicinfo.chap;

/**
 * Created by www.muathu@gmail.com on 12/8/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.model.chap.Chap;

public class ChapAdapter extends RecyclerView.Adapter<ChapAdapter.ChapHolder> {

    public interface Callback {
        public void onClick(Chap chap, int position);

        public void onLongClick(Chap chap, int position);

        public void onClickDownload(Chap chap, int position);

        public void onLoadMore();
    }

    private Callback callback;

    private List<Chap> chapList;

    public class ChapHolder extends RecyclerView.ViewHolder {
        public Button btChap;
        public ImageView btDownload;

        public ChapHolder(View view) {
            super(view);
            btChap = (Button) view.findViewById(R.id.bt_chap);
            btDownload = (ImageView) view.findViewById(R.id.bt_download);
        }
    }

    public ChapAdapter(List<Chap> chapList, Callback callback) {
        this.chapList = chapList;
        this.callback = callback;
    }

    @Override
    public ChapHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comic_chap, parent, false);
        return new ChapHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChapHolder holder, int position) {
        Chap chap = chapList.get(position);
        holder.btChap.setText(chap.getTit());
        holder.btChap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClick(chap, position);
                }
            }
        });
        holder.btChap.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (callback != null) {
                    callback.onLongClick(chap, position);
                }
                return true;
            }
        });
        holder.btDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClickDownload(chap, position);
                }
            }
        });
        if (position == chapList.size() - 1) {
            if (callback != null) {
                callback.onLoadMore();
            }
        }
    }

    @Override
    public int getItemCount() {
        return chapList.size();
    }
}