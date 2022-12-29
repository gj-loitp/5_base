package vn.loitp.app.a.cv.rv.normalRv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import kotlinx.android.synthetic.main.i_movie_list.view.*
import vn.loitp.R

@LogTag("MoviesAdapter")
class MoviesAdapter(
    private val moviesList: List<Movie>,
    private val callback: Callback?
) :
    BaseAdapter() {

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
            if (bindingAdapterPosition == moviesList.size - 1) {
                callback?.onLoadMore()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.i_movie_list, parent, false)
        return MovieViewHolder(itemView)
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
