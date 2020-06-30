package com.core.helper.gallery.albumonly

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.core.helper.gallery.photos.PhotosDataCore
import com.core.utilities.LAnimationUtil
import com.core.utilities.LLog
import com.core.utilities.LScreenUtil
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import com.github.piasy.biv.loader.ImageLoader
import com.github.piasy.biv.view.BigImageView
import com.github.piasy.biv.view.GlideImageViewFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.restapi.flickr.model.photosetgetphotos.Photo
import java.io.File
import java.util.*

/**
 * Created by loitp on 14/04/15.
 */
class PhotosOnlyAdapter(context: Context, private val callback: Callback?) :
        RecyclerView.Adapter<PhotosOnlyAdapter.ViewHolder>() {

    private val TAG = javaClass.simpleName
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val screenW: Int = LScreenUtil.screenWidth
    private val viewFactory = GlideImageViewFactory()

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.l_item_flickr_photos_core_only, viewGroup, false))
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder.hasNoImage()) {
            holder.rebind()
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val photo = PhotosDataCore.getInstance().photoList[position]
        viewHolder.bind(p = photo, position = position)
    }

    override fun getItemCount(): Int {
        return if (PhotosDataCore.getInstance().photoList == null) {
            0
        } else PhotosDataCore.getInstance().photoList.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val tvTitle: TextView = v.findViewById(R.id.tvTitle)
        private val bigImageView: BigImageView = v.findViewById(R.id.biv)
        private val btDownload: FloatingActionButton = v.findViewById(R.id.bt_download)
        private val btShare: FloatingActionButton = v.findViewById(R.id.bt_share)
        private val btReport: FloatingActionButton = v.findViewById(R.id.bt_report)
        private val btCmt: FloatingActionButton = v.findViewById(R.id.bt_cmt)
        private val pb: ProgressBar? = v.findViewById(R.id.progressBar)
        private var photo: Photo? = null

        init {
            bigImageView.setTapToRetry(false)
            bigImageView.setImageViewFactory(viewFactory)
        }

        internal fun bind(p: Photo, position: Int) {
            this.photo = p
            photo?.let {
                val height = it.heightO * screenW / it.widthO

                //viewHolder.rootView.setBackgroundColor(LStoreUtil.getRandomColorLight());
                bigImageView.layoutParams.width = screenW
                bigImageView.layoutParams.height = height
                bigImageView.requestLayout()

                bigImageView.showImage(Uri.parse(it.urlS), Uri.parse(it.urlO))
                bigImageView.setImageLoaderCallback(object : ImageLoader.Callback {
                    override fun onCacheHit(imageType: Int, image: File) {}

                    override fun onCacheMiss(imageType: Int, image: File) {}

                    override fun onStart() {
                        pb?.visibility = View.VISIBLE
                    }

                    override fun onProgress(progress: Int) {
                        pb?.progress = progress
                    }

                    override fun onFinish() {}

                    override fun onSuccess(image: File) {
                        bigImageView.ssiv?.isZoomEnabled = false
                        pb?.visibility = View.INVISIBLE
                    }

                    override fun onFail(error: Exception) {}
                })

                if (it.title.toLowerCase(Locale.getDefault()).startsWith("null")) {
                    tvTitle.visibility = View.INVISIBLE
                } else {
                    tvTitle.visibility = View.VISIBLE
                    tvTitle.text = it.title
                    LUIUtil.setTextShadow(textView = tvTitle)
                }
                bigImageView.setOnClickListener { _ ->
                    LLog.d(TAG, "setOnClickListener")
                    LAnimationUtil.play(view = bigImageView, techniques = Techniques.Pulse)
                    callback?.onClick(photo = it, pos = position)
                }
                bigImageView.setOnLongClickListener { _ ->
                    LLog.d(TAG, "onLongClick")
                    LAnimationUtil.play(view = bigImageView, techniques = Techniques.Pulse)
                    callback?.onLongClick(photo = it, pos = position)
                    true
                }
                btDownload.setOnClickListener { v ->
                    LAnimationUtil.play(view = v, techniques = Techniques.Flash)
                    callback?.onClickDownload(photo = it, pos = position)
                }
                btShare.setOnClickListener { v ->
                    LAnimationUtil.play(view = v, techniques = Techniques.Flash)
                    callback?.onClickShare(photo = it, pos = position)
                }
                btReport.setOnClickListener { v ->
                    LAnimationUtil.play(view = v, techniques = Techniques.Flash)
                    callback?.onClickReport(photo = it, pos = position)
                }
                btCmt.setOnClickListener { v ->
                    LAnimationUtil.play(view = v, techniques = Techniques.Flash)
                    callback?.onClickCmt(photo = it, pos = position)
                }
            }
        }

        internal fun rebind() {
            photo?.let {
                bigImageView.showImage(Uri.parse(it.urlS), Uri.parse(it.urlO))
            }
        }

        internal fun clear() {
            bigImageView.ssiv?.recycle()
        }

        internal fun hasNoImage(): Boolean {
            val ssiv = bigImageView.ssiv
            return ssiv == null || !ssiv.hasImage()
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
