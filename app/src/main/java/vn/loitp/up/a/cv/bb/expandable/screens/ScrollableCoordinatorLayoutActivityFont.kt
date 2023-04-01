package vn.loitp.up.a.cv.bb.expandable.screens

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.WorkerThread
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.loadGlide
import vn.loitp.R
import vn.loitp.databinding.AScrollableCoordinatorLayoutBinding

@LogTag("ScrollableCoordinatorLayoutActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ScrollableCoordinatorLayoutActivityFont : BaseActivityFont() {
    private lateinit var binding: AScrollableCoordinatorLayoutBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AScrollableCoordinatorLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = Adapter()
        }

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Meow", Snackbar.LENGTH_LONG).show()
        }
    }

    internal fun loadBitmap(@DrawableRes id: Int, view: AppCompatImageView) {
        view.loadGlide(
            any = id,
        )
    }

    @Suppress("unused")
    @WorkerThread
    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<AppCompatImageView>(R.id.ivItem)

        fun setResource(@DrawableRes id: Int) {
            loadBitmap(id, image)
        }

    }

    private inner class Adapter : RecyclerView.Adapter<ViewHolder>() {

        private val cats = listOf(
            R.drawable.cat1,
            R.drawable.cat2,
            R.drawable.cat3,
            R.drawable.cat4,
            R.drawable.cat5,
            R.drawable.cat6,
            R.drawable.cat7,
            R.drawable.cat8
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val ctx = parent.context
            val v = LayoutInflater.from(ctx).inflate(R.layout.item_cat, parent, false)
            return ViewHolder(v)
        }

        override fun getItemCount(): Int = 100

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val cat = cats[position % cats.size]
            holder.setResource(cat)
        }
    }
}
