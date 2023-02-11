package vn.loitp.up.a.cv.rv.footer2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import vn.loitp.databinding.IAboutMeBinding
import vn.loitp.databinding.IMovieListBinding
import vn.loitp.up.a.cv.rv.normalRv.Movie

@LogTag("Footer2Adapter")
class Footer2Adapter(
    private val moviesList: List<Movie>,
    private val callback: Callback?
) :
    BaseAdapter() {

    companion object {
        const val TYPE_ITEM = 1
        const val TYPE_BANNER = 2
    }

    interface Callback {
        fun onClick(movie: Movie, position: Int)
        fun onLongClick(movie: Movie, position: Int)
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
        }
    }

    inner class BannerViewHolder(val binding: IAboutMeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie) {
//            logD("bind BannerViewHolder: $bindingAdapterPosition")
            binding.textViewUser.text = "BANNER-" + movie.title
            binding.textViewAboutMe.text = movie.cover
            if (bindingAdapterPosition == moviesList.size - 1) {
                callback?.onLoadMore()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == TYPE_BANNER) {
            val binding =
                IAboutMeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            BannerViewHolder(binding)
        } else {
            val binding =
                IMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MovieViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            TYPE_BANNER
        } else {
            TYPE_ITEM
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is BannerViewHolder) {
            val movie = moviesList[position]
            holder.bind(movie)
        } else if (holder is MovieViewHolder) {
            val movie = moviesList[position]
            holder.bind(movie)
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}
