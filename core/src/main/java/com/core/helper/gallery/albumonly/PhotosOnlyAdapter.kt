package com.core.helper.gallery.albumonly

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.core.adapter.AnimationAdapter
import com.core.helper.gallery.photos.PhotosDataCore
import com.core.utilities.LAnimationUtil
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import com.restapi.flickr.model.photosetgetphotos.Photo
import kotlinx.android.synthetic.main.l_item_flickr_photos_core_only.view.*
import java.util.*

class PhotosOnlyAdapter(val context: Context, private val callback: Callback?) :
        AnimationAdapter() {

    private val logTag = javaClass.simpleName

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.l_item_flickr_photos_core_only, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return PhotosDataCore.instance.getPhotoList().size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val photo = PhotosDataCore.instance.getPhotoList()[position]
            holder.bind(p = photo, position = position)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal fun bind(p: Photo, position: Int) {

            LImageUtil.load(context = itemView.iv.context,
                    url = p.urlO,
                    imageView = itemView.iv,
                    resPlaceHolder = R.color.gray,
                    resError = R.color.gray,
                    drawableRequestListener = object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                            itemView.layoutControl?.visibility = View.VISIBLE
                            itemView.viewLine?.visibility = View.VISIBLE
                            return false
                        }
                    })

            if (p.title.toLowerCase(Locale.getDefault()).startsWith("null")) {
                itemView.tvTitle.visibility = View.INVISIBLE
            } else {
                itemView.tvTitle.visibility = View.VISIBLE
                itemView.tvTitle.text = p.title
                LUIUtil.setTextShadow(textView = itemView.tvTitle)
            }
            itemView.layoutRoot.setOnClickListener {
                callback?.onClick(photo = p, pos = position)
            }
            itemView.layoutRoot.setOnLongClickListener {
                callback?.onLongClick(photo = p, pos = position)
                true
            }
            itemView.btDownload.setOnClickListener {
                LAnimationUtil.play(view = it, techniques = Techniques.Flash)
                callback?.onClickDownload(photo = p, pos = position)
            }
            itemView.btShare.setOnClickListener {
                LAnimationUtil.play(view = it, techniques = Techniques.Flash)
                callback?.onClickShare(photo = p, pos = position)
            }
            itemView.btReport.setOnClickListener {
                LAnimationUtil.play(view = it, techniques = Techniques.Flash)
                callback?.onClickReport(photo = p, pos = position)
            }
            itemView.btCmt.setOnClickListener {
                LAnimationUtil.play(view = it, techniques = Techniques.Flash)
                callback?.onClickCmt(photo = p, pos = position)
            }

            setAnimation(viewToAnimate = itemView.layoutRoot, position = bindingAdapterPosition)
        }

    }

    interface Callback {
        fun onClick(photo: Photo, pos: Int)

        fun onLongClick(photo: Photo, pos: Int)

        fun onClickDownload(photo: Photo, pos: Int)

        fun onClickShare(photo: Photo, pos: Int)

        fun onClickReport(photo: Photo, pos: Int)

        fun onClickCmt(photo: Photo, pos: Int)
    }
}
