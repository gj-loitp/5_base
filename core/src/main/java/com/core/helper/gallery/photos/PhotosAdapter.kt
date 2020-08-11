package com.core.helper.gallery.photos

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.core.helper.gallery.photos.PhotosDataCore.Companion.instance
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.restapi.flickr.model.photosetgetphotos.Photo
import com.views.layout.squarelayout.LSquareFrameLayout

/**
 * Created by loitp on 14/04/15.
 */
class PhotosAdapter internal constructor(private val context: Context, private val callback: Callback?)
    : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    private val logTag = javaClass.simpleName
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.l_item_flickr_photos_core, viewGroup, false))
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        LImageUtil.clear(context = context, target = holder.imageView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val photo = instance.getPhotoList()[position]

        LImageUtil.load(context = context, url = photo.flickrLink1024, imageView = viewHolder.imageView)
        viewHolder.tvSize.text = "${photo.widthO} x ${photo.heightO}"
        LUIUtil.setTextShadow(textView = viewHolder.tvSize)

        viewHolder.rootView.setOnClickListener {
            callback?.onClick(photo = photo, pos = position)
        }
        viewHolder.rootView.setOnLongClickListener {
            callback?.onLongClick(photo = photo, pos = position)
            true
        }
    }

    override fun getItemCount(): Int {
        return instance.getPhotoList().size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imageView: ImageView = v.findViewById(R.id.imageView)
        val tvSize: TextView = v.findViewById(R.id.tvSize)
        val rootView: LSquareFrameLayout = v.findViewById(R.id.rootView)
    }

    interface Callback {
        fun onClick(photo: Photo, pos: Int)
        fun onLongClick(photo: Photo, pos: Int)
    }
}
