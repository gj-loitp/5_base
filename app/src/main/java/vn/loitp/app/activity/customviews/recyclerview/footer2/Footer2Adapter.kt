package vn.loitp.app.activity.customviews.recyclerview.footer2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitpcore.core.adapter.BaseAdapter
import kotlinx.android.synthetic.main.view_item_about_me.view.*
import kotlinx.android.synthetic.main.view_movie_list.view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalRecyclerView.Movie

@LogTag("Footer2Adapter")
class Footer2Adapter(private val moviesList: List<Movie>, private val callback: Callback?) :
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

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            itemView.title.text = movie.title
            itemView.genre.text = movie.genre
            itemView.year.text = movie.year
            itemView.rootView.setOnClickListener {
                callback?.onClick(movie = movie, position = bindingAdapterPosition)
            }
            itemView.rootView.setOnLongClickListener {
                callback?.onLongClick(movie = movie, position = bindingAdapterPosition)
                true
            }
        }
    }

    inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie) {
//            logD("bind BannerViewHolder: $bindingAdapterPosition")
            itemView.textViewUser.text = "BANNER-" + movie.title
            itemView.textViewAboutMe.text = movie.cover
            if (bindingAdapterPosition == moviesList.size - 1) {
                callback?.onLoadMore()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_BANNER) {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_item_about_me, parent, false)
            BannerViewHolder(itemView)
        } else {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.view_movie_list, parent, false)
            MovieViewHolder(itemView)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            TYPE_BANNER
        } else {
            TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
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
