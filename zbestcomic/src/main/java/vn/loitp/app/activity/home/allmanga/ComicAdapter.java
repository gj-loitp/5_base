package vn.loitp.app.activity.home.allmanga;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import io.realm.Realm;
import loitp.basemaster.R;
import vn.loitp.app.activity.home.allmanga.realm.RealmController;
import vn.loitp.app.model.comic.Comic;
import vn.loitp.core.utilities.LUIUtil;

public class ComicAdapter extends RealmRecyclerViewAdapter<Comic> {
    final Context context;
    private Realm realm;
    private LayoutInflater inflater;

    public interface OnClick {
        public void onClick(Comic book, int position);

        public void onLongClick(int position);
    }

    private OnClick onClick;

    public ComicAdapter(Context context, OnClick onClick) {
        this.context = context;
        this.onClick = onClick;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.real_item_books, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        realm = RealmController.getInstance().getRealm();

        final Comic comic = getItem(position);
        final CardViewHolder holder = (CardViewHolder) viewHolder;

        holder.textTitle.setText(comic.getTitle());
        holder.textAuthor.setText("xxxx");
        holder.textDescription.setText("dddd");

        holder.imageBackground.setBackgroundColor(LUIUtil.getColor(context));
        if (comic.getUrlImg() != null) {
            Glide.with(context)
                    .load(comic.getUrlImg())
                    .asBitmap()
                    .centerCrop()
                    .into(holder.imageBackground);
        }

        //remove single match from realm
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onClick != null) {
                    onClick.onLongClick(position);
                }
                return false;
            }
        });

        //update single match from realm
        holder.card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onClick != null) {
                    onClick.onClick(comic, position);
                }
            }
        });
    }

    // return the size of your data set (invoked by the layout manager)
    public int getItemCount() {
        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public CardView card;
        public TextView textTitle;
        public TextView textAuthor;
        public TextView textDescription;
        public ImageView imageBackground;

        public CardViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card_books);
            textTitle = (TextView) itemView.findViewById(R.id.text_books_title);
            textAuthor = (TextView) itemView.findViewById(R.id.text_books_author);
            textDescription = (TextView) itemView.findViewById(R.id.text_books_description);
            imageBackground = (ImageView) itemView.findViewById(R.id.image_background);
        }
    }
}
