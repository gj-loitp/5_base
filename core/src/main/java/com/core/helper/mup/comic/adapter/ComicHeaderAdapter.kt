package com.core.helper.mup.comic.adapter

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.adapter.AnimationAdapter
import com.core.helper.mup.comic.model.Comic
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.view_row_comic_header.view.*

@LogTag("loitppGirlHeaderAdapter")
class ComicHeaderAdapter : AnimationAdapter() {

    private var comic: Comic? = null
    var onClickRoot: ((Comic) -> Unit)? = null

    fun setData(comic: Comic) {
        this.comic = comic
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(comic: Comic) {
            LImageUtil.load(context = itemView.imageView.context,
                    url = comic.getImageSrc(),
                    imageView = itemView.imageView,
                    resError = R.color.gray,
                    resPlaceHolder = R.color.gray,
                    drawableRequestListener = null)

            LImageUtil.load(context = itemView.ivAvatar.context,
                    url = comic.getImageSrc(),
                    imageView = itemView.ivAvatar,
                    resError = R.color.gray,
                    resPlaceHolder = R.color.gray,
                    drawableRequestListener = null)

            itemView.tvTitle.text = comic.title

            ValueAnimator.ofFloat(0f, 200f, 0f).apply {
                addUpdateListener { animation -> itemView.roundRect.bottomLeftRadius = (animation.animatedValue as Float) }
                duration = 800
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
            }.start()

            onClickRoot?.invoke(comic)

//            setAnimation(viewToAnimate = itemView, position = bindingAdapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.view_row_comic_header, parent,
                    false
            ))

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            comic?.let {
                holder.bind(comic = it)
            }
        }
    }

}
