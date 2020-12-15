package com.core.helper.mup.girl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.BuildConfig
import com.R
import com.annotation.LogTag
import com.core.adapter.BaseAdapter
import com.core.common.Constants
import com.core.helper.mup.girl.model.GirlPage
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.view_row_girl_header.view.*

@LogTag("GirlHeaderAdapter")
class GirlHeaderAdapter : BaseAdapter() {

    private var girlPage: GirlPage? = null
//    var onClickRootListener: ((GirlPage, Int) -> Unit)? = null

    fun setData(girlPage: GirlPage) {
        this.girlPage = girlPage
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(girlPage: GirlPage) {
            val src = if (BuildConfig.DEBUG) {
                Constants.URL_IMG
            } else {
                girlPage.src
            }
            LImageUtil.load(context = itemView.imageView.context,
                    any = src,
                    imageView = itemView.imageView,
                    resError = R.color.black,
                    resPlaceHolder = R.color.black,
                    drawableRequestListener = null)
//            setAnimation(viewToAnimate = itemView, position = bindingAdapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.view_row_girl_header, parent,
                    false
            ))

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            girlPage?.let {
                holder.bind(girlPage = it)
            }
        }
    }

}
