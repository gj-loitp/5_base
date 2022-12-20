package com.loitpcore.core.helper.gallery.albumOnly

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.daimajia.androidanimations.library.Techniques
import com.loitpcore.R
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.adapter.BaseAdapter
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.helper.gallery.photos.PhotosDataCore
import com.loitpcore.core.utilities.LAnimationUtil
import com.loitpcore.core.utilities.LImageUtil
import com.loitpcore.core.utilities.LScreenUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.restApi.flickr.model.photoSetGetPhotos.Photo
import kotlinx.android.synthetic.main.l_item_flickr_photos_core_only.view.*
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("PhotosOnlyAdapter")
class PhotosOnlyAdapter(
    private val callback: Callback?
) : BaseAdapter() {

    private val widthScreen = LScreenUtil.screenWidth

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
        return ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.l_item_flickr_photos_core_only, viewGroup, false)
        )
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

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal fun bind(
            p: Photo,
            position: Int
        ) {

            val screenHeight = p.calculatorHeight(widthScreen = widthScreen)
            LUIUtil.setSizeOfView(
                view = itemView.iv,
                width = widthScreen,
                height = screenHeight,
            )

            val color = LUIUtil.getRandomColorLight()
            LImageUtil.load(
                context = itemView.iv.context,
                any = p.urlO,
                imageView = itemView.iv,
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
                })

            if (p.title.lowercase(Locale.getDefault()).startsWith("null")) {
                itemView.tvTitle.isVisible = false
            } else {
                itemView.tvTitle.isVisible = true
                itemView.tvTitle.text = p.title
            }

            itemView.tvTitle.isVisible = true
            itemView.tvTitle.text = "${p.widthO} x ${p.heightO}"

            itemView.layoutRoot.setOnClickListener {
                callback?.onClick(photo = p, pos = position)
            }
            itemView.layoutRoot.setOnLongClickListener {
                callback?.onLongClick(photo = p, pos = position)
                true
            }
            itemView.btDownload.setSafeOnClickListener {
                LAnimationUtil.play(view = it, techniques = Techniques.Flash)
                callback?.onClickDownload(photo = p, pos = position)
            }
            itemView.btShare.setSafeOnClickListener {
                LAnimationUtil.play(view = it, techniques = Techniques.Flash)
                callback?.onClickShare(photo = p, pos = position)
            }
            itemView.btSetWallpaper.setSafeOnClickListener {
                LAnimationUtil.play(view = it, techniques = Techniques.Flash)
                callback?.onClickSetWallpaper(photo = p, pos = position, imageView = itemView.iv)
            }
            itemView.btReport.setSafeOnClickListener {
                LAnimationUtil.play(view = it, techniques = Techniques.Flash)
                callback?.onClickReport(photo = p, pos = position)
            }
            itemView.btCmt.setSafeOnClickListener {
                LAnimationUtil.play(view = it, techniques = Techniques.Flash)
                callback?.onClickCmt(photo = p, pos = position)
            }
        }
    }
}
