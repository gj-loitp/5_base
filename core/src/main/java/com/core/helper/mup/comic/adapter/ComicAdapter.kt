package com.core.helper.mup.comic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.adapter.AnimationAdapter
import com.core.helper.mup.comic.model.Comic
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.view_row_comic.view.*

@LogTag("loitppirlHeaderAdapter")
class ComicAdapter : AnimationAdapter() {

    private var listComic = ArrayList<Comic>()
    var onClickRoot: ((Comic) -> Unit)? = null

    fun setData(listComic: List<Comic>, isSwipeToRefresh: Boolean?) {
        if (isSwipeToRefresh == true) {
            this.listComic.clear()
        }
        this.listComic.addAll(listComic)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(comic: Comic) {
            LImageUtil.load(context = itemView.ivBackground.context,
                    any = comic.imageSrc,
                    imageView = itemView.ivBackground,
                    resError = R.color.gray,
                    resPlaceHolder = R.color.gray,
                    drawableRequestListener = null)

            itemView.tvTitle.apply {
                this.text = comic.title
                LUIUtil.setTextShadow(textView = this)
            }

            comic.totalChapter?.let {
                itemView.tvTotalChapter.apply {
                    this.text = "${comic.totalChapter}"
                    LUIUtil.setTextShadow(textView = this)
                }
            }

            itemView.cardView.setSafeOnClickListener {
                onClickRoot?.invoke(comic)
            }

            setAnimation(viewToAnimate = itemView, position = bindingAdapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.view_row_comic, parent,
                    false
            ))

    override fun getItemCount(): Int = listComic.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(comic = listComic[position])
        }
    }

}
