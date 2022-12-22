package vn.loitp.app.a.cv.rv.recyclerTabLayout.customView01

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.loitp.views.rv.recyclerTabLayout.RecyclerTabLayout
import vn.loitp.R
import vn.loitp.app.a.cv.rv.recyclerTabLayout.DemoColorPagerAdapter

class RvTabCustomView01Adapter internal constructor(viewPager: ViewPager) :
    RecyclerTabLayout.Adapter<RvTabCustomView01Adapter.ViewHolder>(viewPager) {

    private val mAdapter: DemoColorPagerAdapter? = mViewPager.adapter as DemoColorPagerAdapter?

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_rv_tab_view01_tab, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val colorItem = mAdapter?.getColorItem(position)
        holder.tvTitle.text = colorItem?.name
        holder.color.setBackgroundColor(colorItem?.color ?: 0)

        val name = SpannableString(colorItem?.name)
        if (position == currentIndicatorPosition) {
            name.setSpan(StyleSpan(Typeface.BOLD), 0, name.length, 0)
        }
        holder.tvTitle.text = name
    }

    override fun getItemCount(): Int {
        return mAdapter?.count ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var color: View = itemView.findViewById(R.id.color)
        var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)

        init {
            itemView.setOnClickListener {
                viewPager.currentItem = bindingAdapterPosition
            }
        }
    }
}
