package com.loitp.core.helper.ttt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.helper.ttt.model.comictype.ComicType
import com.loitp.databinding.LITttSelectTypeBinding

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("TTTTypeAdapter")
class TTTTypeAdapter : BaseAdapter() {

    private val listComicType = ArrayList<ComicType>()
    private var currentComicType: ComicType? = null
    var onClickRootListener: ((ComicType, Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(
        listComicType: ArrayList<ComicType>, currentComicType: ComicType?
    ) {
        this.listComicType.clear()
        this.listComicType.addAll(listComicType)
        this.currentComicType = currentComicType
        notifyDataSetChanged()
    }

    inner class DataViewHolder(val binding: LITttSelectTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comicType: ComicType) {
            if (comicType.url == currentComicType?.url) {
                binding.ivFav.visibility = View.VISIBLE
            } else {
                binding.ivFav.visibility = View.GONE
            }
            binding.tvType.text = comicType.type
            binding.cardView.setSafeOnClickListenerElastic(runnable = {
                onClickRootListener?.invoke(comicType, bindingAdapterPosition)
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            LITttSelectTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = listComicType.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) {
        if (holder is DataViewHolder) {
            holder.bind(listComicType[position])
        }
    }
}
