package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import vn.loitp.app.R
import java.util.*

class DemoColorPagerAdapter : PagerAdapter() {

    private var mItems: List<ColorItem> = ArrayList()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
                .inflate(R.layout.item_recycler_tablayout_page, container, false)

        val textView = view.findViewById<TextView>(R.id.title)
        textView.text = "Page: " + mItems[position].hex!!
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }

    override fun getCount(): Int {
        return mItems.size
    }

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view === any
    }

    override fun getPageTitle(position: Int): String? {
        return mItems[position].name
    }

    fun getColorItem(position: Int): ColorItem {
        return mItems[position]
    }

    fun addAll(items: List<ColorItem>) {
        mItems = ArrayList(items)
    }
}
