package com.core.helper.gallery.album

import android.content.Context
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
import com.core.adapter.BaseAdapter
import com.core.utilities.LDateUtil
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.restapi.flickr.model.photosetgetlist.Photoset
import kotlinx.android.synthetic.main.l_item_flickr_album_core.view.*

@LogTag("AlbumAdapter")
class AlbumAdapter(private val context: Context, private val photosetList: List<Photoset>, private val callback: Callback?)
    : BaseAdapter() {

    val color = if (LUIUtil.isDarkTheme()) {
        R.color.dark900
    } else {
        R.color.whiteSmoke
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.l_item_flickr_album_core, viewGroup, false))
    }

//    override fun onViewRecycled(holder: ViewHolder) {
//        super.onViewRecycled(holder)
//        LImageUtil.clear(context = context, target = holder.iv)
//    }

    override fun getItemCount(): Int {
        return photosetList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val photoSet = photosetList[position]
            holder.bind(photoset = photoSet)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(photoset: Photoset) {
//            val color = photoset.colorBackground

            itemView.tvUpdate.visibility = View.INVISIBLE
            itemView.tvLabel.visibility = View.INVISIBLE
            itemView.tvNumber.visibility = View.INVISIBLE

            LImageUtil.load(context = itemView.imageView.context,
                    any = photoset.flickrLinkO(),
                    imageView = itemView.imageView,
                    resPlaceHolder = color,
                    resError = color,
                    drawableRequestListener = object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                            itemView.tvUpdate?.visibility = View.VISIBLE
                            itemView.tvLabel?.visibility = View.VISIBLE
                            itemView.tvNumber?.visibility = View.VISIBLE
                            return false
                        }
                    })

            itemView.tvLabel.text = photoset.title?.content

            val update = LDateUtil.getDateCurrentTimeZone(timestamp = photoset.dateUpdate, format = "dd-MM-yyyy HH:mm:ss")
            itemView.tvUpdate.text = update
            itemView.tvNumber.text = photoset.photos

//            LUIUtil.setTextShadow(textView = itemView.tvLabel)
//            LUIUtil.setTextShadow(textView = itemView.tvUpdate)
//            LUIUtil.setTextShadow(textView = itemView.tvNumber)

            itemView.frameLayout.setOnClickListener {
                callback?.onClick(bindingAdapterPosition)
            }
            itemView.frameLayout.setOnLongClickListener {
                callback?.onLongClick(bindingAdapterPosition)
                true
            }
        }
    }

    interface Callback {
        fun onClick(pos: Int)

        fun onLongClick(pos: Int)
    }
}
