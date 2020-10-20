package com.core.helper.mup.comic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.adapter.AnimationAdapter
import com.core.helper.mup.comic.model.Category
import com.core.utilities.LAppResource
import kotlinx.android.synthetic.main.view_row_comic_category.view.*

@LogTag("CategoryAdapter")
class CategoryAdapter : AnimationAdapter() {

    private val listCategory = ArrayList<Category>()

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
                itemView.cardView.setCardBackgroundColor(LAppResource.getColor(R.color.cyan))
            }

            itemView.cardView.setOnClickListener {
                listCategory.forEach {
                    it.isSelected = false
                }
                category.isSelected = true
                notifyDataSetChanged()

//                onClickRoot?.invoke(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.view_row_comic_category, parent,
                    false
            ))

    override fun getItemCount(): Int = listCategory.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(category = listCategory[position])
        }
    }

}
