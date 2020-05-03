package vn.loitp.app.activity.customviews.layout.coordinatorlayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.core.utilities.LLog
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie

class MultiAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TAG = "loitpp" + javaClass.simpleName
    private val moviesList = ArrayList<Movie>()

    private val TYPE_MOVIE = 1
    private val TYPE_MOVIE_BOTTOM = 2

    fun setList(moviesList: ArrayList<Movie>) {
        this.moviesList.clear()
        this.moviesList.addAll(moviesList)

        val bottomView = Movie()
        bottomView.title = "BOTTOM"
        bottomView.isBottom = true
        this.moviesList.add(bottomView)

        notifyDataSetChanged()
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var year: TextView = view.findViewById(R.id.year)
        var genre: TextView = view.findViewById(R.id.genre)
        var rootView: LinearLayout = view.findViewById(R.id.rootView)

        fun bind(movie: Movie) {
            title.text = movie.title
            genre.text = movie.genre
            year.text = movie.year
        }
    }

    inner class BottomView(view: View) : RecyclerView.ViewHolder(view) {
        var tv: TextView = view.findViewById(R.id.textView)

        fun bind(movie: Movie) {
            tv.text = movie.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_MOVIE) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_row, parent, false)
            MovieViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_movie_bottom, parent, false)
            BottomView(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        LLog.d(TAG, "onBindViewHolder position $position")
        val movie = moviesList[position]
        if (holder.itemViewType == TYPE_MOVIE) {
            (holder as MovieViewHolder).bind(movie)
        } else {
            (holder as BottomView).bind(movie)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (moviesList[position].isBottom) {
            return TYPE_MOVIE_BOTTOM
        } else {
            return TYPE_MOVIE
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

}