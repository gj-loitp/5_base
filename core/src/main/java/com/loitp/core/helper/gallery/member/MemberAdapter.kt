package com.loitp.core.helper.gallery.member

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
import com.loitp.core.helper.gallery.photos.PhotosDataCore
import com.loitp.databinding.LIFlickrPhotosMemberBinding
import com.loitp.restApi.flickr.model.photoSetGetPhotos.Photo
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
@LogTag("MemberAdapter")
class MemberAdapter(
    private val callback: Callback?
) : BaseAdapter() {

    interface Callback {
        fun onClick(
            photo: Photo, pos: Int, imageView: ImageView, textView: TextView
        )

        fun onLongClick(
            photo: Photo, pos: Int
        )
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup, position: Int
    ): ViewHolder {
        val binding = LIFlickrPhotosMemberBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return PhotosDataCore.instance.getPhotoList().size
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) {
        if (holder is ViewHolder) {
            val photo = PhotosDataCore.instance.getPhotoList()[position]
            holder.bind(photo)
        }
    }

    inner class ViewHolder(val binding: LIFlickrPhotosMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {

            val color = randomColorLight
            binding.circleImageView.loadGlide(any = photo.urlO,
                resPlaceHolder = color,
                resError = color,
                drawableRequestListener = object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })

            if (photo.title.lowercase(Locale.getDefault()).startsWith("null")) {
                binding.tvTitle.visibility = View.INVISIBLE
            } else {
                binding.tvTitle.visibility = View.VISIBLE
                binding.tvTitle.text = photo.title
            }
            binding.fl.setOnClickListener {
                callback?.onClick(
                    photo = photo,
                    pos = bindingAdapterPosition,
                    imageView = binding.circleImageView,
                    textView = binding.tvTitle
                )
            }
            binding.fl.setOnLongClickListener {
                callback?.onLongClick(photo = photo, pos = bindingAdapterPosition)
                true
            }
        }
    }
}
