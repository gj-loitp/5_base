package vn.loitp.up.a.cv.rv.book

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.screenWidth
import com.loitp.core.ext.setMargins
import vn.loitp.R
import vn.loitp.databinding.IBookBinding
import vn.loitp.up.a.cv.rv.normalRv.Movie

@LogTag("BookAdapter")
class BookAdapter(
    private val context: Context,
    private val column: Int,
    private val moviesList: MutableList<Movie>,
    private val callback: Callback?
) : BaseAdapter() {

    private val sizeW: Int = screenWidth / column
    private val sizeH: Int = sizeW * 15 / 9
    private val sizeMarginTopBottom: Int = sizeW / 5
    private val sizeMarginTopLeftRight: Int = sizeW / 10

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
            notifyItemRangeChanged(0, itemCount)
        }
    }

    inner class MovieViewHolder(val binding: IBookBinding) :
        RecyclerView.ViewHolder(binding.rootView) {

        fun bind(movie: Movie) {
            binding.rootView.layoutParams.width = sizeW
            binding.rootView.layoutParams.height = sizeH
            binding.rootView.invalidate()
            val indexEachColumn = bindingAdapterPosition % column
            binding.textView.text = movie.title
            binding.textView.visibility = View.VISIBLE
            when (indexEachColumn) {
                0 -> {
                    binding.bkg.setImageResource(R.drawable.l_grid_item_background_left)
                }
                column - 1 -> {
                    binding.bkg.setImageResource(R.drawable.l_grid_item_background_right)
                }
                else -> {
                    binding.bkg.setImageResource(R.drawable.l_grid_item_background_center)
                }
            }
            binding.imageView.setMargins(
                leftPx = sizeMarginTopLeftRight,
                topPx = sizeMarginTopBottom,
                rightPx = sizeMarginTopLeftRight,
                bottomPx = sizeMarginTopBottom
            )
            val url = movie.cover
            if (url != null) {
                binding.imageView.loadGlide(any = url)
            } else {
                binding.imageView.setImageResource(0)
                binding.textView.visibility = View.INVISIBLE
            }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
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
