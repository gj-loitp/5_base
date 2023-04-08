package com.loitp.core.helper.gallery.album

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.getDateCurrentTimeZone
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.randomColorLight
import com.loitp.core.ext.setTextShadow
import com.loitp.databinding.LIFlickrAlbumCoreBinding
import com.loitp.restApi.flickr.model.photoSetGetList.Photoset

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("AlbumAdapter")
class AlbumAdapter(
    private val listPhotoSet: List<Photoset>,
    private val callback: Callback?
) : BaseAdapter() {

    interface Callback {
        fun onClick(pos: Int)

        fun onLongClick(pos: Int)
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        position: Int
    ): ViewHolder {
        val binding = LIFlickrAlbumCoreBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listPhotoSet.size
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is ViewHolder) {
            val photoSet = listPhotoSet[position]
            holder.bind(p = photoSet)
        }
    }

    inner class ViewHolder(val binding: LIFlickrAlbumCoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(p: Photoset) {

            val color = randomColorLight
            binding.imageView.loadGlide(
                any = p.flickrLinkO(),
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

            binding.tvLabel.text = p.title?.content

            val update = p.dateUpdate.getDateCurrentTimeZone(
                format = "dd-MM-yyyy HH:mm:ss"
            )
            binding.tvUpdate.text = update
            binding.tvNumber.text = p.photos

            binding.tvLabel.setTextShadow(color = Color.BLACK)
            binding.tvUpdate.setTextShadow(color = Color.BLACK)
            binding.tvNumber.setTextShadow(color = Color.BLACK)

            binding.frameLayout.setOnClickListener {
                callback?.onClick(bindingAdapterPosition)
            }
            binding.frameLayout.setOnLongClickListener {
                callback?.onLongClick(bindingAdapterPosition)
                true
            }
        }
    }
}
