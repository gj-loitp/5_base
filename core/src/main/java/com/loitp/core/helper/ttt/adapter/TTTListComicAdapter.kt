package com.loitp.core.helper.ttt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.helper.ttt.model.comic.Comic
import com.loitp.databinding.LITttComicBinding

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("TTTListComicAdapter")
class TTTListComicAdapter : BaseAdapter() {

    private val listComic = ArrayList<Comic>()
    var onClickRootListener: ((Comic, Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listComic: ArrayList<Comic>) {
        this.listComic.clear()
        this.listComic.addAll(listComic)
        notifyDataSetChanged()
    }

    inner class DataViewHolder(val binding: LITttComicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comic: Comic) {
            if (comic.urlImg.isEmpty()) {
                binding.ivCover.visibility = View.GONE
            } else {
                binding.ivCover.visibility = View.VISIBLE
                binding.ivCover.loadGlide(
                    any = comic.urlImg,
                )
            }
            binding.tvTitle.text = comic.title
            binding.cardView.setSafeOnClickListenerElastic(runnable = {
                onClickRootListener?.invoke(comic, bindingAdapterPosition)
            })
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecyclerView.ViewHolder {
        val binding = LITttComicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = listComic.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) {
        if (holder is TTTListComicAdapter.DataViewHolder) {
            holder.bind(listComic[position])
        }
    }
}
