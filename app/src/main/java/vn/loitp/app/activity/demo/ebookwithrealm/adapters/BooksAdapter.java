package vn.loitp.app.activity.demo.ebookwithrealm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.core.utilities.LUIUtil;

import io.realm.Realm;
import vn.loitp.app.R;
import vn.loitp.app.activity.demo.ebookwithrealm.model.Book;
import vn.loitp.app.activity.demo.ebookwithrealm.realm.RealmController;

public class BooksAdapter extends RealmRecyclerViewAdapter<Book> {
    final Context context;
    private Realm realm;
    private LayoutInflater inflater;

    public interface OnClick {
        void onClick(Book book, int position);

        void onLongClick(int position);
    }

    private OnClick onClick;

    public BooksAdapter(Context context, OnClick onClick) {
        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.real_item_books, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        realm = RealmController.getInstance().getRealm();

        final Book book = getItem(position);
        final CardViewHolder holder = (CardViewHolder) viewHolder;

        holder.textTitle.setText(book.getTitle());
        holder.textAuthor.setText(book.getAuthor());
        holder.textDescription.setText(book.getDescription());

        holder.imageBackground.setBackgroundColor(LUIUtil.INSTANCE.getColor(context));
        if (book.getImageUrl() != null) {
            Glide.with(context)
                    .asBitmap()
                    .load(book.getImageUrl().replace("https", "http"))
                    //.fitCenter()
                    .into(holder.imageBackground);
        }

        //remove single match from realm
        holder.card.setOnLongClickListener(v -> {
            if (onClick != null) {
                onClick.onLongClick(position);
            }
            return false;
        });

        //update single match from realm
        holder.card.setOnClickListener(v -> {
            if (onClick != null) {
                onClick.onClick(book, position);
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
        CardView card;
        TextView textTitle;
        TextView textAuthor;
        TextView textDescription;
        ImageView imageBackground;

        CardViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card_books);
            textTitle = itemView.findViewById(R.id.text_books_title);
            textAuthor = itemView.findViewById(R.id.text_books_author);
            textDescription = itemView.findViewById(R.id.text_books_description);
            imageBackground = itemView.findViewById(R.id.image_background);
        }
    }
}
