package com.core.helper.mup.comic.adapter

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.bumptech.glide.load.MultiTransformation
import com.core.adapter.AnimationAdapter
import com.core.helper.mup.comic.model.Comic
import com.core.utilities.LAppResource
import com.core.utilities.LImageUtil
import com.views.setSafeOnClickListener
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.ColorFilterTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.view_row_comic_header.view.*

@LogTag("ComicHeaderAdapter")
class ComicHeaderAdapter : AnimationAdapter() {

    private var comic: Comic? = null
    private val transform = MultiTransformation(
            BlurTransformation(25),
            ColorFilterTransformation(LAppResource.getColor(R.color.black50))
    )

    var onClickRoot: ((Comic) -> Unit)? = null

    fun setData(comic: Comic) {
        logD("setData " + comic.title)
        this.comic = comic
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(comic: Comic) {
            LImageUtil.load(
                    context = itemView.imageView.context,
                    any = comic.imageSrc,
                    imageView = itemView.imageView,
                    resError = R.color.gray,
                    resPlaceHolder = R.color.gray,
                    drawableRequestListener = null,
                    transformation = transform
            )

            LImageUtil.load(
                    context = itemView.ivAvatar.context,
                    any = comic.imageSrc,
                    imageView = itemView.ivAvatar,
                    resError = R.color.gray,
                    resPlaceHolder = R.color.gray,
                    drawableRequestListener = null
            )

            itemView.tvTitle.text = comic.title

            ValueAnimator.ofFloat(0f, 200f, 0f).apply {
                addUpdateListener { animation -> itemView.roundRect.bottomLeftRadius = (animation.animatedValue as Float) }
                duration = 800
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
            }.start()

            itemView.imageView.setSafeOnClickListener {
                onClickRoot?.invoke(comic)
            }

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
