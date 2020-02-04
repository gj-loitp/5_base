package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.customview01

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.views.recyclerview.recyclertablayout.RecyclerTabLayout
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.DemoColorPagerAdapter

/**
 * Created by Shinichi Nishimura on 2015/07/22.
 */
class DemoCustomView01Adapter internal constructor(viewPager: ViewPager) : RecyclerTabLayout.Adapter<DemoCustomView01Adapter.ViewHolder>(viewPager) {

    private val mAdapater: DemoColorPagerAdapter? = mViewPager.adapter as DemoColorPagerAdapter?

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_custom_view01_tab, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val colorItem = mAdapater?.getColorItem(position)
        holder.title.text = colorItem?.name
        holder.color.setBackgroundColor(colorItem?.color ?: 0)

        val name = SpannableString(colorItem?.name)
        if (position == currentIndicatorPosition) {
            name.setSpan(StyleSpan(Typeface.BOLD), 0, name.length, 0)
        }
        holder.title.text = name
    }

    override fun getItemCount(): Int {
        return mAdapater?.count ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var color: View
        var title: TextView = itemView.findViewById(R.id.title)

        init {
            color = itemView.findViewById(R.id.color)
            itemView.setOnClickListener { v -> viewPager.currentItem = adapterPosition }
        }
    }
}
