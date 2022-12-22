package vn.loitp.app.activity.customviews.recyclerview.recyclerTabLayout.customView02

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.loitp.core.utilities.LAppResource
import com.loitp.views.recyclerView.recyclerTabLayout.RecyclerTabLayout
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.recyclerTabLayout.DemoImagePagerAdapter

class RvTabCustomView02Adapter internal constructor(viewPager: ViewPager) :
    RecyclerTabLayout.Adapter<RvTabCustomView02Adapter.ViewHolder>(viewPager) {

    private val mAdapter: DemoImagePagerAdapter = mViewPager.adapter as DemoImagePagerAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_layout_custom_view02_tab, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val drawable = loadIconWithTint(resourceId = mAdapter.getImageResourceId(position))

        holder.imageView.setImageDrawable(drawable)
        holder.imageView.isSelected = position == currentIndicatorPosition
    }

    private fun loadIconWithTint(@DrawableRes resourceId: Int): Drawable {
        var icon = LAppResource.getDrawable(resourceId)
        val colorStateList = LAppResource.getColorStateList(R.color.custom_view02_tint)
        icon = DrawableCompat.wrap(icon!!)
        DrawableCompat.setTintList(icon, colorStateList)
        return icon
    }

    override fun getItemCount(): Int {
        return mAdapter.count
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView.findViewById(R.id.imageView)

        init {
            itemView.setOnClickListener {
                viewPager.currentItem = bindingAdapterPosition
            }
        }
    }
}
