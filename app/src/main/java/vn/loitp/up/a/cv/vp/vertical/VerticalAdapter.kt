package vn.loitp.up.a.cv.vp.vertical

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class VerticalAdapter(
    fm: FragmentManager,
    private val stringList: List<String>
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return FrmVertical.newInstance(stringList[position])
    }

    override fun getCount(): Int {
        return stringList.size
    }
}
