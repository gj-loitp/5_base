package com.core.helper.gallery.album

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.R

import com.core.utilities.LDateUtil
import com.core.utilities.LImageUtil
import com.core.utilities.LScreenUtil
import com.core.utilities.LUIUtil
import com.restapi.flickr.model.photosetgetlist.Photoset

/**
 * Created by loitp on 14/04/15.
 */
class AlbumAdapter(private val context: Context, private val photosetList: List<Photoset>, private val callback: Callback?)
    : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {
    private val TAG = javaClass.simpleName
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val sizeW: Int = LScreenUtil.screenWidth
    private val sizeH: Int = sizeW

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.l_item_album_core, viewGroup, false))
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        //LLog.d(TAG, "onViewRecycled");
        LImageUtil.clear(context = context, target = holder.iv)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.rootView.layoutParams.height = sizeH
        viewHolder.rootView.requestLayout()

        val photoSet = photosetList[position]
        LImageUtil.loadNoAmin(context = context, url = photoSet.flickrLinkO(), urlThumbnal = photoSet.flickrLinkM(), imageView = viewHolder.iv)

        viewHolder.tvLabel.text = photoSet.title?.content

        val update = LDateUtil.getDateCurrentTimeZone(timestamp = photoSet.dateUpdate, format = "dd-MM-yyyy HH:mm:ss")
        viewHolder.tvUpdate.text = update
        viewHolder.tvNumber.text = photoSet.photos

        LUIUtil.setTextShadow(textView = viewHolder.tvLabel)
        LUIUtil.setTextShadow(textView = viewHolder.tvUpdate)
        LUIUtil.setTextShadow(textView = viewHolder.tvNumber)

        viewHolder.rootView.setOnClickListener {
            callback?.onClick(position)
        }
        viewHolder.rootView.setOnLongClickListener {
            callback?.onLongClick(position)
            true
        }

        when (position) {
            0 -> {
                viewHolder.viewSpaceTop.visibility = View.VISIBLE
                viewHolder.viewSpaceBottom.visibility = View.GONE
            }
            itemCount - 1 -> {
                viewHolder.viewSpaceTop.visibility = View.GONE
                viewHolder.viewSpaceBottom.visibility = View.VISIBLE
            }
            else -> {
                viewHolder.viewSpaceTop.visibility = View.GONE
                viewHolder.viewSpaceBottom.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return photosetList.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val iv: ImageView = v.findViewById(R.id.imageView)
        val tvLabel: TextView = v.findViewById(R.id.tvLabel)
        val tvUpdate: TextView = v.findViewById(R.id.tvUpdate)
        val tvNumber: TextView = v.findViewById(R.id.tvNumber)
        val rootView: LinearLayout = v.findViewById(R.id.rootView)
        val viewSpaceTop: View = v.findViewById(R.id.viewSpaceTop)
        val viewSpaceBottom: View = v.findViewById(R.id.viewSpaceBottom)
    }

    interface Callback {
        fun onClick(pos: Int)

        fun onLongClick(pos: Int)
    }
}
