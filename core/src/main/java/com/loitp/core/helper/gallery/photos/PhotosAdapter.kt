package com.loitp.core.helper.gallery.photos

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.randomColorLight
import com.loitp.core.ext.setTextShadow
import com.loitp.core.helper.gallery.photos.PhotosDataCore.Companion.instance
import com.loitp.databinding.LIFlickrPhotosCoreBinding
import com.loitp.restApi.flickr.model.photoSetGetPhotos.Photo

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
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

        val binding = LIFlickrPhotosCoreBinding.inflate(
            /* inflater = */ LayoutInflater.from(viewGroup.context),
            /* parent = */ viewGroup,
            /* attachToParent = */ false
        )
        return ViewHolder(binding)
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

    inner class ViewHolder(val binding: LIFlickrPhotosCoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(photo: Photo) {

            val color = randomColorLight
            binding.imageView.loadGlide(
                any = photo.flickrLink1024,
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

            binding.tvSize.text = "${photo.widthO} x ${photo.heightO}"
            binding.tvSize.setTextShadow(color = null)

            binding.layoutRootView.setOnClickListener {
                callback?.onClick(photo = photo, pos = bindingAdapterPosition)
            }
            binding.layoutRootView.setOnLongClickListener {
                callback?.onLongClick(photo = photo, pos = bindingAdapterPosition)
                true
            }
        }
    }
}
