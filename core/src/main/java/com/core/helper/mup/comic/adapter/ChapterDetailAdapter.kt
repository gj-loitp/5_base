package com.core.helper.mup.comic.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.core.adapter.AnimationAdapter
import com.core.common.Constants
import com.core.helper.mup.comic.model.ChapterComicsDetail
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.view_row_comic_chapter_detail.view.*

@LogTag("loitppChapterDetailAdapter")
class ChapterDetailAdapter : AnimationAdapter() {

    private val listData = ArrayList<ChapterComicsDetail>()

    fun setListData(listChap: List<ChapterComicsDetail>) {
        this.listData.addAll(listChap)
        notifyDataSetChanged()
    }

    var onClickRoot: ((ChapterComicsDetail) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(chapterComicsDetail: ChapterComicsDetail) {
            var imgSrc = chapterComicsDetail.getImageSrc()
//            if (Constants.IS_DEBUG) {
//                imgSrc = Constants.URL_IMG_1
//            }
            logD("$bindingAdapterPosition -> imgSrc $imgSrc")
            itemView.wp7progressBar.showProgressBar()
            LImageUtil.setImageViewZoom(iv = itemView.ivChapterDetail)
            LImageUtil.load(
                    context = itemView.ivChapterDetail.context,
                    any = imgSrc,
                    imageView = itemView.ivChapterDetail,
                    resError = R.drawable.place_holder_error404,
                    drawableRequestListener = object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                            itemView.wp7progressBar.hideProgressBar()
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                            itemView.wp7progressBar.hideProgressBar()
                            return false
                        }
                    }
            )

            itemView.ivChapterDetail.setSafeOnClickListener {
                onClickRoot?.invoke(chapterComicsDetail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.view_row_comic_chapter_detail, parent,
                    false
            ))

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(chapterComicsDetail = listData[position])
        }
    }

}
