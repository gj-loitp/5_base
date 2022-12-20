package com.loitp.core.helper.gallery.photos

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.loitpcore.R
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.helper.gallery.photos.PhotosDataCore.Companion.instance
import com.loitp.core.utilities.LImageUtil
import com.loitp.core.utilities.LUIUtil
import com.loitpcore.restApi.flickr.model.photoSetGetPhotos.Photo
import kotlinx.android.synthetic.main.l_item_flickr_photos_core.view.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("PhotosAdapter")
class PhotosAdapter internal constructor(
    private val callback: Callback?
) : BaseAdapter() {

    interface Callback {
        fun onClick(
            photo: Photo,
            pos: Int
        )

        fun onLongClick(
            photo: Photo,
            pos: Int
        )
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        position: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.l_item_flickr_photos_core, viewGroup, false)
        )
    }

    override fun getItemCount(): Int {
        return instance.getPhotoList().size
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is ViewHolder) {
            val photo = instance.getPhotoList()[position]
            holder.bind(photo = photo)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        @SuppressLint("SetTextI18n")
        fun bind(photo: Photo) {

            val color = LUIUtil.getRandomColorLight()
            LImageUtil.load(
                context = itemView.imageView.context,
                any = photo.flickrLink1024,
                imageView = itemView.imageView,
                resPlaceHolder = color,
                resError = color,
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

            itemView.tvSize.text = "${photo.widthO} x ${photo.heightO}"
            LUIUtil.setTextShadow(textView = itemView.tvSize, color = null)

            itemView.layoutRootView.setOnClickListener {
                callback?.onClick(photo = photo, pos = bindingAdapterPosition)
            }
            itemView.layoutRootView.setOnLongClickListener {
                callback?.onLongClick(photo = photo, pos = bindingAdapterPosition)
                true
            }
        }
    }
}
