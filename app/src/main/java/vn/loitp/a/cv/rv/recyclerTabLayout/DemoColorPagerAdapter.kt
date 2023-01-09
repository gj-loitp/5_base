package vn.loitp.a.cv.rv.recyclerTabLayout

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.i_tab_layout_page.view.*
import vn.loitp.R

class DemoColorPagerAdapter : PagerAdapter() {

    private var mItems: List<ColorItem> = ArrayList()

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(
        container: ViewGroup,
        position: Int
    ): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.i_tab_layout_page, container, false)

        view.tvTitle.text = "Page: " + mItems[position].hex
        container.addView(view)

        return view
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        any: Any
    ) {
        container.removeView(any as View)
    }

    override fun getCount(): Int {
        return mItems.size
    }

    override fun isViewFromObject(
        view: View,
        any: Any
    ): Boolean {
        return view === any
    }

    override fun getPageTitle(position: Int): String {
        return mItems[position].name
    }

    fun getColorItem(position: Int): ColorItem {
        return mItems[position]
    }

    fun addAll(items: List<ColorItem>) {
        mItems = ArrayList(items)
    }
}
