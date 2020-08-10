package com.core.helper.gallery.member

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.R

import com.core.helper.gallery.photos.PhotosDataCore
import com.core.utilities.LImageUtil
import com.core.utilities.LScreenUtil
import com.core.utilities.LUIUtil
import com.restapi.flickr.model.photosetgetphotos.Photo
import java.util.*

class MemberAdapter(private val context: Context, numCount: Int, private val callback: Callback?)
    : RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

    private val logTag = javaClass.simpleName
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val sizeW: Int = LScreenUtil.screenWidth / numCount
    private val sizeH: Int = LScreenUtil.screenHeight / numCount

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.l_item_flickr_photos_member, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.fl.layoutParams.width = sizeW
        viewHolder.fl.layoutParams.height = sizeH
        viewHolder.fl.requestLayout()
        val photo = PhotosDataCore.instance.getPhotoList()[position]

        LImageUtil.loadNoAmin(context = context, url = photo.urlO, urlThumbnal = photo.urlS, imageView = viewHolder.touchImageView)

        if (photo.title.toLowerCase(Locale.getDefault()).startsWith("null")) {
            viewHolder.tvTitle.visibility = View.INVISIBLE
        } else {
            viewHolder.tvTitle.visibility = View.VISIBLE
            viewHolder.tvTitle.text = photo.title
            LUIUtil.setTextShadow(textView = viewHolder.tvTitle)
        }
        viewHolder.touchImageView.setOnClickListener {
            callback?.onClick(photo = photo, pos = position, imageView = viewHolder.touchImageView, textView = viewHolder.tvTitle)
        }
        viewHolder.touchImageView.setOnLongClickListener {
            callback?.onLongClick(photo = photo, pos = position)
            true
        }

        if (position == 0 || position == 1) {
            viewHolder.viewSpaceTop.visibility = View.VISIBLE
            viewHolder.viewSpaceBottom.visibility = View.GONE
        } else if (itemCount % 2 == 0 && (position == itemCount - 1 || position == itemCount - 2)) {
            viewHolder.viewSpaceTop.visibility = View.GONE
            viewHolder.viewSpaceBottom.visibility = View.VISIBLE
        } else if (itemCount % 2 != 0 && position == itemCount - 1) {
            viewHolder.viewSpaceTop.visibility = View.GONE
            viewHolder.viewSpaceBottom.visibility = View.VISIBLE
        } else {
            viewHolder.viewSpaceTop.visibility = View.GONE
            viewHolder.viewSpaceBottom.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return PhotosDataCore.instance.getPhotoList().size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val fl: FrameLayout = v.findViewById(R.id.fl)
        val tvTitle: TextView = v.findViewById(R.id.tvTitle)
        val touchImageView: ImageView = v.findViewById(R.id.touchImageView)
        val viewSpaceTop: View = v.findViewById(R.id.viewSpaceTop)
        val viewSpaceBottom: View = v.findViewById(R.id.viewSpaceBottom)
    }

    interface Callback {
        fun onClick(photo: Photo, pos: Int, imageView: ImageView, textView: TextView)
        fun onLongClick(photo: Photo, pos: Int)
    }
}
