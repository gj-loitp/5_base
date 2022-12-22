package vn.loitp.app.a.customviews.recyclerview.book

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.utilities.LImageUtil
import com.loitp.core.utilities.LScreenUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.view_item_book.view.*
import vn.loitp.R
import vn.loitp.app.a.customviews.recyclerview.normalRecyclerView.Movie

@LogTag("BookAdapter")
class BookAdapter(
    private val context: Context,
    private val column: Int,
    private val moviesList: MutableList<Movie>,
    private val callback: Callback?
) : BaseAdapter() {

    private val sizeW: Int = LScreenUtil.screenWidth / column
    private val sizeH: Int = sizeW * 15 / 9
    private val sizeMarginTopBottom: Int = sizeW / 5
    private val sizeMarginTopLeftRight: Int = sizeW / 10

    interface Callback {
        fun onClick(movie: Movie, position: Int)
        fun onLongClick(movie: Movie, position: Int)
        fun onLoadMore()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun checkData() {
        val indexEachColumn = itemCount % column
        val missItemCount = column - indexEachColumn
        if (missItemCount != column) {
            for (i in 0 until missItemCount) {
                val movie = Movie()
                moviesList.add(movie)
            }
            notifyDataSetChanged()
        }
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) {
            itemView.rootView.layoutParams.width = sizeW
            itemView.rootView.layoutParams.height = sizeH
            itemView.rootView.invalidate()
            val indexEachColumn = bindingAdapterPosition % column
            itemView.textView.text = movie.title
            itemView.textView.visibility = View.VISIBLE
            when (indexEachColumn) {
                0 -> {
                    itemView.bkg.setImageResource(R.drawable.l_grid_item_background_left)
                }
                column - 1 -> {
                    itemView.bkg.setImageResource(R.drawable.l_grid_item_background_right)
                }
                else -> {
                    itemView.bkg.setImageResource(R.drawable.l_grid_item_background_center)
                }
            }
            LUIUtil.setMargins(
                view = itemView.imageView,
                leftPx = sizeMarginTopLeftRight,
                topPx = sizeMarginTopBottom,
                rightPx = sizeMarginTopLeftRight,
                bottomPx = sizeMarginTopBottom
            )
            val url = movie.cover
            if (url != null) {
                LImageUtil.load(context = context, any = url, imageView = itemView.imageView)
            } else {
                itemView.imageView.setImageResource(0)
                itemView.textView.visibility = View.INVISIBLE
            }
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.view_item_book, parent, false)
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
