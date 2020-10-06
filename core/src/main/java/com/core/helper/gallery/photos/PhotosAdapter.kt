package com.core.helper.gallery.photos

import android.annotation.SuppressLint
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
import com.core.adapter.AnimationAdapter
import com.core.helper.gallery.photos.PhotosDataCore.Companion.instance
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.restapi.flickr.model.photosetgetphotos.Photo
import kotlinx.android.synthetic.main.l_item_flickr_photos_core.view.*

/**
 * Created by loitp on 14/04/15.
 */
@LogTag("PhotosAdapter")
class PhotosAdapter internal constructor(private val context: Context, private val callback: Callback?)
    : AnimationAdapter() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.l_item_flickr_photos_core, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return instance.getPhotoList().size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val photo = instance.getPhotoList()[position]
            holder.bind(photo = photo)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        @SuppressLint("SetTextI18n")
        fun bind(photo: Photo) {

            itemView.tvSize.visibility = View.INVISIBLE

            LImageUtil.load(context = itemView.imageView.context,
                    url = photo.flickrLink1024,
                    imageView = itemView.imageView,
                    resPlaceHolder = R.color.black,
                    resError = R.color.black,
                    drawableRequestListener = object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                            itemView.tvSize?.visibility = View.VISIBLE
                            setAnimation(viewToAnimate = itemView.layoutRootView, position = bindingAdapterPosition)
                            return false
                        }
                    })

            itemView.tvSize.text = "${photo.widthO} x ${photo.heightO}"
            LUIUtil.setTextShadow(textView = itemView.tvSize)

            itemView.layoutRootView.setOnClickListener {
                callback?.onClick(photo = photo, pos = bindingAdapterPosition)
            }
            itemView.layoutRootView.setOnLongClickListener {
                callback?.onLongClick(photo = photo, pos = bindingAdapterPosition)
                true
            }
        }
    }

    interface Callback {
        fun onClick(photo: Photo, pos: Int)
        fun onLongClick(photo: Photo, pos: Int)
    }
}
