package vn.loitp.app.a.cv.indicator.ex

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter

class ExamplePagerAdapter(
    private val mDataList: List<String>
) : PagerAdapter() {
    override fun getCount(): Int {
        return mDataList.size
    }

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view === any
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val textView = TextView(container.context)
        textView.text = mDataList[position]
        textView.gravity = Gravity.CENTER
        textView.setTextColor(Color.BLACK)
        textView.textSize = 24f
        container.addView(textView)
        return textView
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        if (any is View) {
            container.removeView(any)
        }
    }

    override fun getItemPosition(any: Any): Int {
        val textView = any as TextView
        val text = textView.text.toString()
        val index = mDataList.indexOf(text)
        return if (index >= 0) {
            index
        } else POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mDataList[position]
    }
}
