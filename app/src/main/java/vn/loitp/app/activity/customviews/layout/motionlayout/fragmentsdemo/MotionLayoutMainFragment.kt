package vn.loitp.app.activity.customviews.layout.motionlayout.fragmentsdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import vn.loitp.app.R

class MotionLayoutMainFragment : Fragment() {

    companion object {
        fun newInstance() = MotionLayoutMainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.motion_21_main_fragment, container, false)
    }
}
