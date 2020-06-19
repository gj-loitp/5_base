package vn.loitp.app.activity.customviews.recyclerview.bookview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.core.utilities.LImageUtil
import com.core.utilities.LScreenUtil
import com.core.utilities.LUIUtil
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie

class BookAdapter(private val context: Context,
                  private val column: Int,
                  private val moviesList: MutableList<Movie>,
                  private val callback: Callback?) : RecyclerView.Adapter<BookAdapter.MovieViewHolder>() {

    private val sizeW: Int = LScreenUtil.screenWidth / column
    private val sizeH: Int
    private val sizeMarginTopBottom: Int
    private val sizeMarginTopLeftRight: Int

    init {
        sizeH = sizeW * 15 / 9
        sizeMarginTopBottom = sizeW / 5
        sizeMarginTopLeftRight = sizeW / 10
    }

    interface Callback {
        fun onClick(movie: Movie, position: Int)
        fun onLongClick(movie: Movie, position: Int)
        fun onLoadMore()
    }

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
        var tv: TextView = view.findViewById(R.id.textView)
        var iv: ImageView = view.findViewById(R.id.imageView)
        var bkg: ImageView = view.findViewById(R.id.bkg)
        var rootView: RelativeLayout = view.findViewById(R.id.rootView)

        fun bind(movie: Movie) {
            rootView.layoutParams.width = sizeW
            rootView.layoutParams.height = sizeH
            rootView.invalidate()
            val indexEachColumn = bindingAdapterPosition % column
            tv.text = movie.title
            tv.visibility = View.VISIBLE
            when (indexEachColumn) {
                0 -> {
                    bkg.setImageResource(R.drawable.l_grid_item_background_left)
                }
                column - 1 -> {
                    bkg.setImageResource(R.drawable.l_grid_item_background_right)
                }
                else -> {
                    bkg.setImageResource(R.drawable.l_grid_item_background_center)
                }
            }
            LUIUtil.setMargins(view = iv, leftPx = sizeMarginTopLeftRight, topPx = sizeMarginTopBottom, rightPx = sizeMarginTopLeftRight, bottomPx = sizeMarginTopBottom)
            val url = movie.cover
            if (url != null) {
                LImageUtil.load(context = context, url = url, imageView = iv)
            } else {
                iv.setImageResource(0)
                tv.visibility = View.INVISIBLE
            }
            rootView.setOnClickListener { v: View? ->
                callback?.onClick(movie, bindingAdapterPosition)
            }
            rootView.setOnLongClickListener { v: View? ->
                callback?.onLongClick(movie, bindingAdapterPosition)
                true
            }
            if (bindingAdapterPosition == moviesList.size - 1) {
                callback?.onLoadMore()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_row_item_book, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}