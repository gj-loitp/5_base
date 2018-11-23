package vn.loitp.app.activity.customviews.layout.motionlayout.fragmentsdemo

import android.os.Bundle
import android.support.constraint.motion.MotionLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import loitp.basemaster.R

class MotionLayoutSecondFragment : Fragment() {

    private lateinit var motionLayout: MotionLayout

    companion object {
        fun newInstance() = MotionLayoutSecondFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.motion_21_second_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        motionLayout = view.findViewById(R.id.main)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        Log.i(MotionLayoutSecondFragment::class.java.simpleName, "onStart of fragment...")
    }
}
