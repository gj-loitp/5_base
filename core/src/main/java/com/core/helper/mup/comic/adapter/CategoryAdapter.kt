package com.core.helper.mup.comic.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.adapter.BaseAdapter
import com.core.helper.mup.comic.model.Category
import com.core.utilities.LAppResource
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.view_row_comic_category.view.*

@LogTag("CategoryAdapter")
class CategoryAdapter : BaseAdapter() {

    private val listCategory = ArrayList<Category>()
    private val colorBackgroundSelected = if (LUIUtil.isDarkTheme()) {
        LAppResource.getColor(R.color.white)
    } else {
        LAppResource.getColor(R.color.black)
    }
    private val colorBackgroundUnSelected = if (LUIUtil.isDarkTheme()) {
        LAppResource.getColor(R.color.black)
    } else {
        LAppResource.getColor(R.color.white)
    }
    private val colorTextSelected = if (LUIUtil.isDarkTheme()) {
        LAppResource.getColor(R.color.black)
    } else {
        LAppResource.getColor(R.color.white)
    }
    private val colorTextUnSelected = if (LUIUtil.isDarkTheme()) {
        LAppResource.getColor(R.color.white)
    } else {
        LAppResource.getColor(R.color.black)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listCategory: List<Category>) {
        this.listCategory.clear()
        this.listCategory.addAll(listCategory)
        notifyDataSetChanged()
    }

    var onClickRoot: ((Category) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(category: Category) {
            itemView.tvCategory.text = category.title

            if (category.isSelected) {
                itemView.cardView.setCardBackgroundColor(colorBackgroundSelected)
                itemView.tvCategory.setTextColor(colorTextSelected)
            } else {
                itemView.cardView.setCardBackgroundColor(colorBackgroundUnSelected)
                itemView.tvCategory.setTextColor(colorTextUnSelected)
            }

            itemView.cardView.setOnClickListener {
//                listCategory.forEach {
//                    it.isSelected = false
//                }
//                listCategory[bindingAdapterPosition].isSelected = true
//                notifyDataSetChanged()

                onClickRoot?.invoke(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_row_comic_category, parent,
                false
            )
        )

    override fun getItemCount(): Int = listCategory.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(category = listCategory[position])
        }
    }
}
