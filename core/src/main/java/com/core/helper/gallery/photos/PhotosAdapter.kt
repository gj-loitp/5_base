package com.core.helper.gallery.photos

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.core.adapter.AnimationAdapter
import com.core.helper.gallery.photos.PhotosDataCore.Companion.instance
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.restapi.flickr.model.photosetgetphotos.Photo
import kotlinx.android.synthetic.main.l_item_flickr_photos_core.view.*

/**
 * Created by loitp on 14/04/15.
 */
class PhotosAdapter internal constructor(private val context: Context, private val callback: Callback?)
    : AnimationAdapter() {

    private val logTag = javaClass.simpleName

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
            LImageUtil.load(context = itemView.imageView.context, url = photo.flickrLink1024, imageView = itemView.imageView, resPlaceHolder = R.color.gray)
            itemView.tvSize.text = "${photo.widthO} x ${photo.heightO}"
            LUIUtil.setTextShadow(textView = itemView.tvSize)

            itemView.layoutRootView.setOnClickListener {
                callback?.onClick(photo = photo, pos = bindingAdapterPosition)
            }
            itemView.layoutRootView.setOnLongClickListener {
                callback?.onLongClick(photo = photo, pos = bindingAdapterPosition)
                true
            }

            setAnimation(viewToAnimate = itemView.layoutRootView, position = bindingAdapterPosition)
        }
    }

    interface Callback {
        fun onClick(photo: Photo, pos: Int)
        fun onLongClick(photo: Photo, pos: Int)
    }
}
