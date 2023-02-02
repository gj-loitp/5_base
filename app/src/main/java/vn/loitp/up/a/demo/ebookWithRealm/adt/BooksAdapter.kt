package vn.loitp.up.a.demo.ebookWithRealm.adt

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.loitp.R
import vn.loitp.up.a.demo.ebookWithRealm.md.Book

class BooksAdapter(
    val context: Context,
    private val onClick: OnClick?
) : RealmRecyclerViewAdapter<Book?>() {

    interface OnClick {
        fun onClick(
            book: Book,
            position: Int
        )
        fun onLongClick(position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.v_item_books_realm, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val book = getItem(position)

        book?.let { b ->
            if (viewHolder is CardViewHolder) {
                viewHolder.textBooksTitle.text = b.title
                viewHolder.textBooksAuthor.text = b.author
                viewHolder.textBooksDescription.text = b.description
                viewHolder.ivBackground.setBackgroundColor(Color.RED)

                Glide.with(context)
                    .asBitmap()
                    .load(b.imageUrl?.replace("https", "http")) // .fitCenter()
                    .into(viewHolder.ivBackground)

                // remove single match from realm
                viewHolder.cardBooks.setOnLongClickListener {
                    onClick?.onLongClick(position)
                    false
                }

                // update single match from realm
                viewHolder.cardBooks.setOnClickListener {
                    onClick?.onClick(b, position)
                }
            }
        }
    }

    // return the size of your data set (invoked by the layout manager)
    override fun getItemCount(): Int {
        return if (realmAdapter != null) {
            realmAdapter.count
        } else {
            0
        }
    }

    class CardViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardBooks: CardView = itemView.findViewById(R.id.cardBooks)
        var textBooksTitle: TextView = itemView.findViewById(R.id.textBooksTitle)
        var textBooksAuthor: TextView = itemView.findViewById(R.id.textBooksAuthor)
        var textBooksDescription: TextView = itemView.findViewById(R.id.textBooksDescription)
        var ivBackground: ImageView = itemView.findViewById(R.id.ivBackground)
    }
}
