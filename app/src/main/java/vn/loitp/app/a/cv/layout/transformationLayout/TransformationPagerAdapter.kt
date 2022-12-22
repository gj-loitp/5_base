package vn.loitp.app.a.cv.layout.transformationLayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

//TODO change to viewpager 2
class TransformationPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TransformationHomeFragment()
            1 -> TransformationLibraryFragment()
            else -> TransformationRadioFragment()
        }
    }

    override fun getCount() = 3
}
