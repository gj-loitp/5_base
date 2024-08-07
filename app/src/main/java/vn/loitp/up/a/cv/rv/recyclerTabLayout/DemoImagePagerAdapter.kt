package vn.loitp.up.a.cv.rv.recyclerTabLayout

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.viewpager.widget.PagerAdapter
import vn.loitp.R

class DemoImagePagerAdapter : PagerAdapter() {

    private var mItems: List<Int> = ArrayList()

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(
        container: ViewGroup, position: Int
    ): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.i_tab_layout_page, container, false)

        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        tvTitle.text = "Page: $position"
        container.addView(view)

        return view
    }

    override fun destroyItem(
        container: ViewGroup, position: Int, any: Any
    ) {
        container.removeView(any as View)
    }

    override fun getCount(): Int {
        return mItems.size
    }

    override fun isViewFromObject(
        view: View, any: Any
    ): Boolean {
        return view === any
    }

    @DrawableRes
    fun getImageResourceId(position: Int): Int {
        return mItems[position]
    }

    fun addAll(items: List<Int>) {
        mItems = ArrayList(items)
    }
}
