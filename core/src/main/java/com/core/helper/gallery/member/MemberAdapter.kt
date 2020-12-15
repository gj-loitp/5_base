package com.core.helper.gallery.member

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.core.adapter.BaseAdapter
import com.core.helper.gallery.photos.PhotosDataCore
import com.core.utilities.LImageUtil
import com.restapi.flickr.model.photosetgetphotos.Photo
import kotlinx.android.synthetic.main.l_item_flickr_photos_member.view.*
import java.util.*

@LogTag("MemberAdapter")
class MemberAdapter(private val context: Context, private val callback: Callback?)
    : BaseAdapter() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.l_item_flickr_photos_member, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return PhotosDataCore.instance.getPhotoList().size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val photo = PhotosDataCore.instance.getPhotoList()[position]
            holder.bind(photo)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        fun bind(photo: Photo) {

            itemView.tvTitle.visibility = View.INVISIBLE

            LImageUtil.load(context = itemView.circleImageView.context,
                    any = photo.urlO,
                    imageView = itemView.circleImageView,
                    resPlaceHolder = R.color.transparent,
                    resError = R.color.transparent,
                    drawableRequestListener = object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                            itemView.tvTitle?.visibility = View.VISIBLE
                            return false
                        }
                    })

            if (photo.title.toLowerCase(Locale.getDefault()).startsWith("null")) {
                itemView.tvTitle.visibility = View.INVISIBLE
            } else {
                itemView.tvTitle.visibility = View.VISIBLE
                itemView.tvTitle.text = photo.title
//                LUIUtil.setTextShadow(textView = itemView.tvTitle)
            }
            itemView.fl.setOnClickListener {
                callback?.onClick(photo = photo, pos = bindingAdapterPosition, imageView = itemView.circleImageView, textView = itemView.tvTitle)
            }
            itemView.fl.setOnLongClickListener {
                callback?.onLongClick(photo = photo, pos = bindingAdapterPosition)
                true
            }
        }
    }

    interface Callback {
        fun onClick(photo: Photo, pos: Int, imageView: ImageView, textView: TextView)
        fun onLongClick(photo: Photo, pos: Int)
    }
}
