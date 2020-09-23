package com.core.helper.girl.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.core.adapter.AnimationAdapter
import com.core.helper.girl.model.GirlPage
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.view_row_girl_album.view.*

class GirlAlbumAdapter : AnimationAdapter() {
    private val logTag = javaClass.simpleName

    private var listGirlPage = ArrayList<GirlPage>()
    var onClickRootListener: ((GirlPage, Int) -> Unit)? = null

    fun setData(listGirlPage: ArrayList<GirlPage>) {
        this.listGirlPage.clear()
        this.listGirlPage.addAll(listGirlPage)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(girlPage: GirlPage) {
            itemView.tvTitle.text = girlPage.title
            LUIUtil.setTextShadow(itemView.tvTitle, Color.BLACK)
            LImageUtil.load(context = itemView.imageView.context,
                    url = girlPage.src,
                    imageView = itemView.imageView,
                    resPlaceHolder = R.color.whiteSmoke,
                    resError = R.color.whiteSmoke,
                    drawableRequestListener = object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                            setAnimation(viewToAnimate = itemView, position = bindingAdapterPosition)
                            return false
                        }
                    })
            itemView.cardView.setSafeOnClickListener {
                onClickRootListener?.invoke(girlPage, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.view_row_girl_album, parent,
                    false
            ))

    override fun getItemCount(): Int = listGirlPage.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(girlPage = listGirlPage[position])
        }
    }

}
