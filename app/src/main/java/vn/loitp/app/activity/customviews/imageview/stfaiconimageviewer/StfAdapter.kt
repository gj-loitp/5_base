package vn.loitp.app.activity.customviews.imageview.stfaiconimageviewer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.annotation.LogTag
import com.core.adapter.AnimationAdapter
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.view_row_item_stf.view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie

@LogTag("BookAdapter")
class StfAdapter(
        private val context: Context,
        private val moviesList: MutableList<Movie>,
        private val callback: Callback?
) : AnimationAdapter() {

    interface Callback {
        fun onClick(movie: Movie, position: Int)
        fun onLongClick(movie: Movie, position: Int)
        fun onLoadMore()
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) {
            itemView.textView.text = movie.title

            val url = movie.cover
            LImageUtil.load(context = context, any = url, imageView = itemView.imageView)

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

            setAnimation(viewToAnimate = itemView.imageView, position = bindingAdapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_row_item_stf, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            holder.bind(moviesList[position])
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}
