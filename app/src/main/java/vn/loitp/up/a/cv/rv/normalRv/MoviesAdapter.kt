package vn.loitp.up.a.cv.rv.normalRv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import vn.loitp.databinding.IMovieListBinding

@LogTag("MoviesAdapter")
class MoviesAdapter(
    private val moviesList: List<Movie>,
    private val callback: Callback?
) : BaseAdapter() {

    interface Callback {
        fun onClick(
            movie: Movie,
            position: Int
        )

        fun onLongClick(
            movie: Movie,
            position: Int
        )

        fun onLoadMore()
    }

    inner class MovieViewHolder(val binding: IMovieListBinding) :
        RecyclerView.ViewHolder(binding.rootView) {
        fun bind(movie: Movie) {
            binding.title.text = movie.title
            binding.genre.text = movie.genre
            binding.year.text = movie.year
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding =
            IMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is MovieViewHolder) {
            val movie = moviesList[position]
            holder.bind(movie)
        }
    }
}
