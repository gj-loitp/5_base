package vn.loitp.up.a.cv.rv.galleryLayoutManager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.loitp.databinding.ITextViewBinding
import vn.loitp.up.a.cv.rv.normalRv.Movie

class GalleryAdapterVertical internal constructor(
    private val moviesList: List<Movie>, private val callback: Callback?
) : RecyclerView.Adapter<GalleryAdapterVertical.MovieViewHolder>() {

    interface Callback {
        fun onClick(
            movie: Movie, position: Int
        )

        fun onLongClick(
            movie: Movie, position: Int
        )

        fun onLoadMore()
    }

    inner class MovieViewHolder internal constructor(val binding: ITextViewBinding) :
        RecyclerView.ViewHolder(binding.rootView) {
        fun bind(movie: Movie) {
            binding.textView.text = movie.title

            binding.rootView.setOnClickListener {
                callback?.onClick(movie, bindingAdapterPosition)
            }
            binding.rootView.setOnLongClickListener {
                callback?.onLongClick(movie, bindingAdapterPosition)
                true
            }
            if (bindingAdapterPosition == moviesList.size - 1) {
                callback?.onLoadMore()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ITextViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movie = moviesList[position])
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}
