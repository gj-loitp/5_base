package com.core.helper.gallery.album

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.core.adapter.AnimationAdapter
import com.core.utilities.LDateUtil
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.restapi.flickr.model.photosetgetlist.Photoset
import kotlinx.android.synthetic.main.l_item_flickr_album_core.view.*

class AlbumAdapter(private val context: Context, private val photosetList: List<Photoset>, private val callback: Callback?)
    : AnimationAdapter() {

    private val logTag = javaClass.simpleName

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.l_item_flickr_album_core, viewGroup, false))
    }

//    override fun onViewRecycled(holder: ViewHolder) {
//        super.onViewRecycled(holder)
//        LImageUtil.clear(context = context, target = holder.iv)
//    }

    override fun getItemCount(): Int {
        return photosetList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val photoSet = photosetList[position]
            holder.bind(photoset = photoSet)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(photoset: Photoset) {
//            val color = photoset.colorBackground
//            LLog.d(logTag, "$bindingAdapterPosition -> $color")
            LImageUtil.load(context = itemView.imageView.context, url = photoset.flickrLinkO(), imageView = itemView.imageView, resPlaceHolder = R.color.gray)
            itemView.tvLabel.text = photoset.title?.content

            val update = LDateUtil.getDateCurrentTimeZone(timestamp = photoset.dateUpdate, format = "dd-MM-yyyy HH:mm:ss")
            itemView.tvUpdate.text = update
            itemView.tvNumber.text = photoset.photos

            LUIUtil.setTextShadow(textView = itemView.tvLabel)
            LUIUtil.setTextShadow(textView = itemView.tvUpdate)
            LUIUtil.setTextShadow(textView = itemView.tvNumber)

            itemView.frameLayout.setOnClickListener {
                callback?.onClick(bindingAdapterPosition)
            }
            itemView.frameLayout.setOnLongClickListener {
                callback?.onLongClick(bindingAdapterPosition)
                true
            }

            setAnimation(viewToAnimate = itemView.frameLayout, position = bindingAdapterPosition)
        }
    }

    interface Callback {
        fun onClick(pos: Int)

        fun onLongClick(pos: Int)
    }
}
