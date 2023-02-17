package vn.loitp.up.a.cv.layout.transformation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

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
