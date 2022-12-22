package com.loitp.core.helper.gallery.album

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.loitp.R
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.utilities.LDateUtil
import com.loitp.core.utilities.LImageUtil
import com.loitp.core.utilities.LUIUtil
import com.loitp.restApi.flickr.model.photoSetGetList.Photoset
import kotlinx.android.synthetic.main.l_i_flickr_album_core.view.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("AlbumAdapter")
class AlbumAdapter(
    private val listPhotoSet: List<Photoset>,
    private val callback: Callback?
) : BaseAdapter() {

    interface Callback {
        fun onClick(pos: Int)

        fun onLongClick(pos: Int)
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        position: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.l_i_flickr_album_core, viewGroup, false)
        )
    }

    override fun getItemCount(): Int {
        return listPhotoSet.size
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is ViewHolder) {
            val photoSet = listPhotoSet[position]
            holder.bind(p = photoSet)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(p: Photoset) {

            val color = LUIUtil.getRandomColorLight()
            LImageUtil.load(
                context = itemView.imageView.context,
                any = p.flickrLinkO(),
                imageView = itemView.imageView,
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
                }
            )

            itemView.tvLabel.text = p.title?.content

            val update = LDateUtil.getDateCurrentTimeZone(
                timestamp = p.dateUpdate,
                format = "dd-MM-yyyy HH:mm:ss"
            )
            itemView.tvUpdate.text = update
            itemView.tvNumber.text = p.photos

            LUIUtil.setTextShadow(textView = itemView.tvLabel, color = Color.BLACK)
            LUIUtil.setTextShadow(textView = itemView.tvUpdate, color = Color.BLACK)
            LUIUtil.setTextShadow(textView = itemView.tvNumber, color = Color.BLACK)

            itemView.frameLayout.setOnClickListener {
                callback?.onClick(bindingAdapterPosition)
            }
            itemView.frameLayout.setOnLongClickListener {
                callback?.onLongClick(bindingAdapterPosition)
                true
            }
        }
    }
}
