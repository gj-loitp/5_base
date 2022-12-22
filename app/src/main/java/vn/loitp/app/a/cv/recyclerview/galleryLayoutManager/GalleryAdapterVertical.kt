package vn.loitp.app.a.cv.recyclerview.galleryLayoutManager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_item_text_view.view.*
import vn.loitp.R
import vn.loitp.app.a.cv.recyclerview.normalRecyclerView.Movie

class GalleryAdapterVertical internal constructor(
    private val moviesList: List<Movie>,
    private val callback: Callback?
) : RecyclerView.Adapter<GalleryAdapterVertical.MovieViewHolder>() {

    interface Callback {
        fun onClick(movie: Movie, position: Int)
        fun onLongClick(movie: Movie, position: Int)
        fun onLoadMore()
    }

    inner class MovieViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            itemView.textView.text = movie.title

            itemView.rootView.setOnClickListener {
                callback?.onClick(movie, bindingAdapterPosition)
            }
            itemView.rootView.setOnLongClickListener {
                callback?.onLongClick(movie, bindingAdapterPosition)
                true
            }
            if (bindingAdapterPosition == moviesList.size - 1) {
                callback?.onLoadMore()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.view_item_text_view, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movie = moviesList[position])
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}
