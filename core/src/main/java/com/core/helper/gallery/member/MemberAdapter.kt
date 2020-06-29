package com.core.helper.gallery.member

import android.app.Activity
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
import com.core.utilities.LDeviceUtil
import com.core.utilities.LImageUtil
import com.core.utilities.LScreenUtil
import com.core.utilities.LUIUtil
import com.restapi.flickr.model.photosetgetphotos.Photo
import java.util.*

/**
 * Created by loitp on 14/04/15.
 */
class MemberAdapter(private val context: Context, numCount: Int, private val callback: Callback?) : RecyclerView.Adapter<MemberAdapter.ViewHolder>() {
    private val TAG = javaClass.simpleName
    private val inflater: LayoutInflater
    private val isTablet: Boolean
    private val sizeW: Int
    private val sizeH: Int

    init {
        this.inflater = LayoutInflater.from(context)
        this.sizeW = LScreenUtil.screenWidth / numCount
        this.sizeH = LScreenUtil.screenHeight / numCount
        this.isTablet = LDeviceUtil.isTablet(context as Activity)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.l_item_photos_member, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.fl.layoutParams.width = sizeW
        viewHolder.fl.layoutParams.height = sizeH
        viewHolder.fl.requestLayout()
        val photo = PhotosDataCore.getInstance().photoList[position]

        //LLog.d(TAG, ">>>getUrlO " + photo.getUrlO());
        //LLog.d(TAG, ">>>getFlickrLink640 " + photo.getFlickrLink640());
        //LLog.d(TAG, ">>>getFlickrLink1024 " + photo.getFlickrLink1024());

        //LImageUtil.load(context, photo.getUrlM(), viewHolder.imageView);
        LImageUtil.loadNoAmin(context, photo.urlO, photo.urlS, viewHolder.imageView)

        if (photo.title.toLowerCase(Locale.getDefault()).startsWith("null")) {
            viewHolder.tvTitle.visibility = View.INVISIBLE
        } else {
            viewHolder.tvTitle.visibility = View.VISIBLE
            viewHolder.tvTitle.text = photo.title
            LUIUtil.setTextShadow(viewHolder.tvTitle)
        }
        viewHolder.imageView.setOnClickListener {
            callback?.onClick(photo, position, viewHolder.imageView, viewHolder.tvTitle)
        }
        viewHolder.imageView.setOnLongClickListener {
            callback?.onLongClick(photo, position)
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
        return if (PhotosDataCore.getInstance().photoList == null) {
            0
        } else PhotosDataCore.getInstance().photoList.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val fl: FrameLayout = v.findViewById(R.id.fl)
        val tvTitle: TextView = v.findViewById(R.id.tvTitle)
        val imageView: ImageView = v.findViewById(R.id.image_view)
        val viewSpaceTop: View = v.findViewById(R.id.viewSpaceTop)
        val viewSpaceBottom: View = v.findViewById(R.id.viewSpaceBottom)

    }

    interface Callback {
        fun onClick(photo: Photo, pos: Int, imageView: ImageView, textView: TextView)
        fun onLongClick(photo: Photo, pos: Int)
    }
}