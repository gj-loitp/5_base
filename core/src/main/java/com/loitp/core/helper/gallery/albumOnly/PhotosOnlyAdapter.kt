package com.loitp.core.helper.gallery.albumOnly

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.Keep
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.daimajia.androidanimations.library.Techniques
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.*
import com.loitp.core.helper.gallery.photos.PhotosDataCore
import com.loitp.databinding.LIFlickrPhotosCoreOnlyBinding
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
@LogTag("PhotosOnlyAdapter")
class PhotosOnlyAdapter(
    private val callback: Callback?
) : BaseAdapter() {

    private val widthScreen = screenWidth

    interface Callback {
        fun onClick(photo: Photo, pos: Int)
        fun onLongClick(photo: Photo, pos: Int)
        fun onClickDownload(photo: Photo, pos: Int)
        fun onClickShare(photo: Photo, pos: Int)
        fun onClickSetWallpaper(photo: Photo, pos: Int, imageView: ImageView)
        fun onClickReport(photo: Photo, pos: Int)
        fun onClickCmt(photo: Photo, pos: Int)
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        position: Int
    ): ViewHolder {
        val binding =
            LIFlickrPhotosCoreOnlyBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return PhotosDataCore.instance.getPhotoList().size
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is ViewHolder) {
            val photo = PhotosDataCore.instance.getPhotoList()[position]
            holder.bind(p = photo, position = position)
        }
    }

    inner class ViewHolder(val binding: LIFlickrPhotosCoreOnlyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal fun bind(
            p: Photo,
            position: Int
        ) {

            val screenHeight = p.calculatorHeight(widthScreen = widthScreen)
            binding.iv.setSizeOfView(
                width = widthScreen,
                height = screenHeight,
            )

            val color = randomColorLight
            binding.iv.loadGlide(
                any = p.urlO,
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

            if (p.title.lowercase(Locale.getDefault()).startsWith("null")) {
                binding.tvTitle.isVisible = false
            } else {
                binding.tvTitle.isVisible = true
                binding.tvTitle.text = p.title
            }

//            itemView.tvTitle.isVisible = true
//            itemView.tvTitle.text = "${p.widthO} x ${p.heightO}"

            binding.layoutRoot.setOnClickListener {
                callback?.onClick(photo = p, pos = position)
            }
            binding.layoutRoot.setOnLongClickListener {
                callback?.onLongClick(photo = p, pos = position)
                true
            }
            binding.btDownload.setSafeOnClickListener {
                it.play(techniques = Techniques.Flash)
                callback?.onClickDownload(photo = p, pos = position)
            }
            binding.btShare.setSafeOnClickListener {
                it.play(techniques = Techniques.Flash)
                callback?.onClickShare(photo = p, pos = position)
            }
            binding.btSetWallpaper.setSafeOnClickListener {
                it.play(techniques = Techniques.Flash)
                callback?.onClickSetWallpaper(photo = p, pos = position, imageView = binding.iv)
            }
            binding.btReport.setSafeOnClickListener {
                it.play(techniques = Techniques.Flash)
                callback?.onClickReport(photo = p, pos = position)
            }
            binding.btCmt.setSafeOnClickListener {
                it.play(techniques = Techniques.Flash)
                callback?.onClickCmt(photo = p, pos = position)
            }
        }
    }
}
