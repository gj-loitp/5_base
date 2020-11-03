package com.core.helper.mup.comic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.adapter.AnimationAdapter
import com.core.helper.mup.comic.model.ChapterComicsDetail
import com.core.utilities.LImageUtil
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
            logD("$bindingAdapterPosition -> " + chapterComicsDetail.getImageSrc())
            LImageUtil.load(
                    context = itemView.ivChapterDetail.context,
                    any = chapterComicsDetail.getImageSrc(),
                    imageView = itemView.ivChapterDetail,
                    resError = R.color.gray,
                    resPlaceHolder = R.color.gray,
                    drawableRequestListener = null
            )

            itemView.ivChapterDetail.setSafeOnClickListener {

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