package vn.loitp.up.a.cv.rv.galleryLayoutManager

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.ext.loadGlide
import vn.loitp.databinding.IGalleryBinding
import vn.loitp.up.a.cv.rv.normalRv.Movie

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

    inner class MovieViewHolder internal constructor(val binding: IGalleryBinding) :
        RecyclerView.ViewHolder(binding.rootView) {
        fun bind(movie: Movie) {
            binding.imageView.loadGlide(any = movie.cover)
            binding.rootView.setOnClickListener {
                callback?.onClick(movie = movie, position = bindingAdapterPosition)
            }
            binding.rootView.setOnLongClickListener {
                callback?.onLongClick(movie = movie, position = bindingAdapterPosition)
                true
            }
            if (bindingAdapterPosition == moviesList.size - 1) {
                callback?.onLoadMore()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            IGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}
