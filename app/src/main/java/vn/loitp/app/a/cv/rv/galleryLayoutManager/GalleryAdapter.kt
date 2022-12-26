package vn.loitp.app.a.cv.rv.galleryLayoutManager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.view_item_gallery.view.*
import vn.loitp.R
import vn.loitp.app.a.cv.rv.normalRv.Movie

class GalleryAdapter internal constructor(
    private val context: Context,
    private val moviesList: List<Movie>,
    private val callback: Callback?
) : RecyclerView.Adapter<GalleryAdapter.MovieViewHolder>() {

    interface Callback {
        fun onClick(movie: Movie, position: Int)
        fun onLongClick(movie: Movie, position: Int)
        fun onLoadMore()
    }

    inner class MovieViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            LImageUtil.load(context = context, any = movie.cover, imageView = itemView.imageView)
            itemView.rootView.setOnClickListener {
                callback?.onClick(movie = movie, position = bindingAdapterPosition)
            }
            itemView.rootView.setOnLongClickListener {
                callback?.onLongClick(movie = movie, position = bindingAdapterPosition)
                true
            }
            if (bindingAdapterPosition == moviesList.size - 1) {
                callback?.onLoadMore()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.view_item_gallery, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}
