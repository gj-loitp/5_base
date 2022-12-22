package vn.loitp.app.activity.customviews.layout.greedoLayout

import android.content.Context
import android.graphics.BitmapFactory
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.fivehundredpx.greedolayout.GreedoLayoutSizeCalculator.SizeCalculatorDelegate
import com.loitp.core.utilities.LImageUtil
import vn.loitp.R
import vn.loitp.app.activity.customviews.layout.greedoLayout.PhotosAdapter.PhotoViewHolder

class PhotosAdapter(private val mContext: Context) : RecyclerView.Adapter<PhotoViewHolder>(),
    SizeCalculatorDelegate {

    companion object {
        private const val IMAGE_COUNT = 500 // number of images adapter will show
    }

    private val mImageResIds = Constants.IMAGES
    private val mImageAspectRatios = DoubleArray(Constants.IMAGES.size)

    override fun aspectRatioForIndex(index: Int): Double {
        // Precaution, have better handling for this in greedo-layout
        return if (index >= itemCount) 1.0 else mImageAspectRatios[getLoopedIndex(index)]
    }

    class PhotoViewHolder(val mImageView: ImageView) : RecyclerView.ViewHolder(
        mImageView
    )

    init {
        calculateImageAspectRatios()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val imageView = ImageView(mContext)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return PhotoViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        LImageUtil.load(
            context = mContext,
            any = mImageResIds[getLoopedIndex(position)],
            imageView = holder.mImageView,
            resPlaceHolder = R.color.transparent,
            resError = R.color.red,
            transformation = null,
            drawableRequestListener = null
        )
    }

    override fun getItemCount(): Int {
        return IMAGE_COUNT
    }

    private fun calculateImageAspectRatios() {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        for (i in mImageResIds.indices) {
            BitmapFactory.decodeResource(mContext.resources, mImageResIds[i], options)
            mImageAspectRatios[i] = options.outWidth / options.outHeight.toDouble()
        }
    }

    // Index gets wrapped around <code>Constants.IMAGES.length</code> so we can loop content.
    private fun getLoopedIndex(index: Int): Int {
        return index % Constants.IMAGES.size // wrap around
    }
}
