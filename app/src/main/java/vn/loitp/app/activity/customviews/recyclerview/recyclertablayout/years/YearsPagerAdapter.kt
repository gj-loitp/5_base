package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.years

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import vn.loitp.app.R
import java.util.*

class YearsPagerAdapter : PagerAdapter() {

    private var mItems: List<String> = ArrayList()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.view_item_recycler_tablayout_page, container, false)

        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        tvTitle.text = "Page: " + mItems[position]
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, ob: Any) {
        container.removeView(ob as View)
    }

    override fun getCount(): Int {
        return mItems.size
    }

    override fun isViewFromObject(view: View, ob: Any): Boolean {
        return view === ob
    }

    override fun getPageTitle(position: Int): String? {
        return mItems[position]
    }

    fun addAll(items: List<String>) {
        mItems = ArrayList(items)
    }
}
