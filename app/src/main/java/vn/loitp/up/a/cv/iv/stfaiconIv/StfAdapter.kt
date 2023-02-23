package vn.loitp.up.a.cv.iv.stfaiconIv

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.loadGlide
import vn.loitp.databinding.VItemStfBinding
import vn.loitp.up.a.cv.rv.normalRv.Movie

@LogTag("BookAdapter")
class StfAdapter(
    private val moviesList: MutableList<Movie>,
    private val callback: Callback?
) : BaseAdapter() {

    interface Callback {
        fun onClick(iv: ImageView, movie: Movie, moviesList: MutableList<Movie>, position: Int)
        fun onLongClick(movie: Movie, position: Int)
        fun onLoadMore()
    }

    inner class MovieViewHolder(val binding: VItemStfBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.textView.text = movie.title

            val url = movie.cover
            binding.imageView.loadGlide(
                any = url,
            )

            binding.rootView.setOnClickListener {
                callback?.onClick(binding.imageView, movie, moviesList, bindingAdapterPosition)
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding = VItemStfBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is MovieViewHolder) {
            holder.bind(moviesList[position])
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}
