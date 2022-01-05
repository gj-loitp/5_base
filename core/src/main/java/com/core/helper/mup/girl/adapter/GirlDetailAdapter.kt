package com.core.helper.mup.girl.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.BuildConfig
import com.R
import com.annotation.LogTag
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.core.adapter.BaseAdapter
import com.core.common.Constants
import com.core.helper.mup.girl.model.GirlPageDetail
import com.core.utilities.LDateUtil
import com.core.utilities.LImageUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.view_row_girl_detail.view.*

@LogTag("GirlDetailAdapter")
class GirlDetailAdapter : BaseAdapter() {

    private var listGirlPageDetail = ArrayList<GirlPageDetail>()
    var onClickRootListener: ((GirlPageDetail, Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listGirlPageDetail: ArrayList<GirlPageDetail>) {
        this.listGirlPageDetail.clear()
        this.listGirlPageDetail.addAll(listGirlPageDetail)
        notifyDataSetChanged()
    }

    fun getData(): ArrayList<GirlPageDetail> {
        return listGirlPageDetail
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(girlPageDetail: GirlPageDetail) {
            itemView.tvCreatedDate.text = LDateUtil.convertFormatDate(
                strDate = girlPageDetail.createdDate,
                fromFormat = "yyyy-MM-dd'T'HH:mm:ss",
                toFormat = "HH:mm:ss dd/MM/yyyy"
            )
//            LUIUtil.setTextShadow(textView = itemView.tvCreatedDate, color = Color.BLACK)
            val src = if (BuildConfig.DEBUG) {
                Constants.URL_IMG
            } else {
                girlPageDetail.src
            }
            LImageUtil.load(
                context = itemView.imageView.context,
                any = src,
                imageView = itemView.imageView,
                resPlaceHolder = R.color.black,
                resError = R.color.black,
                drawableRequestListener = object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                }
            )
            itemView.cardView.setSafeOnClickListener {
                onClickRootListener?.invoke(girlPageDetail, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_row_girl_detail, parent,
                false
            )
        )

    override fun getItemCount(): Int = listGirlPageDetail.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(girlPageDetail = listGirlPageDetail[position])
        }
    }
}
