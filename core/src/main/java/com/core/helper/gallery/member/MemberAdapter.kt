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
import com.core.utilities.LUIUtil
import com.restapi.flickr.model.photosetgetphotos.Photo
import java.util.*

class MemberAdapter(private val context: Context, private val callback: Callback?)
    : RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

    private val logTag = javaClass.simpleName
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.l_item_flickr_photos_member, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val photo = PhotosDataCore.instance.getPhotoList()[position]

        LImageUtil.load(context = context, url = photo.urlO, imageView = viewHolder.circleImageView, resPlaceHolder = R.color.whiteSmoke)

        if (photo.title.toLowerCase(Locale.getDefault()).startsWith("null")) {
            viewHolder.tvTitle.visibility = View.INVISIBLE
        } else {
            viewHolder.tvTitle.visibility = View.VISIBLE
            viewHolder.tvTitle.text = photo.title
            LUIUtil.setTextShadow(textView = viewHolder.tvTitle)
        }
        viewHolder.circleImageView.setOnClickListener {
            callback?.onClick(photo = photo, pos = position, imageView = viewHolder.circleImageView, textView = viewHolder.tvTitle)
        }
        viewHolder.circleImageView.setOnLongClickListener {
            callback?.onLongClick(photo = photo, pos = position)
            true
        }
    }

    override fun getItemCount(): Int {
        return PhotosDataCore.instance.getPhotoList().size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val fl: FrameLayout = v.findViewById(R.id.fl)
        val tvTitle: TextView = v.findViewById(R.id.tvTitle)
        val circleImageView: ImageView = v.findViewById(R.id.circleImageView)
    }

    interface Callback {
        fun onClick(photo: Photo, pos: Int, imageView: ImageView, textView: TextView)
        fun onLongClick(photo: Photo, pos: Int)
    }
}
