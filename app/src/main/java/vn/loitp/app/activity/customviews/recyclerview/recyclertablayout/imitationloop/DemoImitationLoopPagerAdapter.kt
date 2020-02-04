package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.imitationloop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import vn.loitp.app.R
import java.util.*

/**
 * Created by Shinichi Nishimura on 2015/07/24.
 */
class DemoImitationLoopPagerAdapter : PagerAdapter() {

    private var mItems: List<String> = ArrayList()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_recycler_tablayout_page, container, false)

        val textView = view.findViewById<TextView>(R.id.title)
        textView.text = "Page: " + getValueAt(position)
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return mItems.size * NUMBER_OF_LOOPS
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getPageTitle(position: Int): String? {
        return getValueAt(position)
    }

    fun addAll(items: List<String>) {
        mItems = ArrayList(items)
    }

    fun getCenterPosition(position: Int): Int {
        return mItems.size * NUMBER_OF_LOOPS / 2 + position
    }

    fun getValueAt(position: Int): String? {
        return if (mItems.isEmpty()) {
            null
        } else mItems[position % mItems.size]
    }

    companion object {

        private val NUMBER_OF_LOOPS = 10000
    }
}
