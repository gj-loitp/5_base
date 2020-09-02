package com.core.helper.gallery.album

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.core.utilities.LDateUtil
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.restapi.flickr.model.photosetgetlist.Photoset
import com.views.layout.squarelayout.LSquareFrameLayout

/**
 * Created by loitp on 14/04/15.
 */
class AlbumAdapter(private val context: Context, private val photosetList: List<Photoset>, private val callback: Callback?)
    : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    private val logTag = javaClass.simpleName
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.l_item_flickr_album_core, viewGroup, false))
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        LImageUtil.clear(context = context, target = holder.iv)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val photoSet = photosetList[position]
        LImageUtil.load(context = context, url = photoSet.flickrLinkO(), imageView = viewHolder.iv)

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
    }

    override fun getItemCount(): Int {
        return photosetList.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val iv: ImageView = v.findViewById(R.id.imageView)
        val tvLabel: TextView = v.findViewById(R.id.tvLabel)
        val tvUpdate: TextView = v.findViewById(R.id.tvUpdate)
        val tvNumber: TextView = v.findViewById(R.id.tvNumber)
        val rootView: LSquareFrameLayout = v.findViewById(R.id.rootView)
    }

    interface Callback {
        fun onClick(pos: Int)

        fun onLongClick(pos: Int)
    }
}
