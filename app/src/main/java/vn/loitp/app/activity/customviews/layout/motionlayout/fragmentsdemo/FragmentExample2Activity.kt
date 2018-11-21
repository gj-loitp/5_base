/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vn.loitp.app.activity.customviews.layout.motionlayout.fragmentsdemo

import android.os.Bundle
import android.support.constraint.motion.MotionLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_motion_layout_fragment_example.*
import loitp.basemaster.R

class FragmentExample2Activity : AppCompatActivity(), View.OnClickListener, MotionLayout.TransitionListener {
    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, progress: Float) {
        var fragment: Fragment? = null
        if (progress - lastProgress > 0) {
            // from start to end
            val atEnd = Math.abs(progress - 1f) < 0.1f
            if (atEnd && fragment is MotionLayoutMainFragment) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction
                        .setCustomAnimations(R.animator.show, 0)
                fragment = ListFragment.newInstance()
                transaction
                        .replace(R.id.container, fragment)
                        .commitNow()
            }
        } else {
            // from end to start
            val atStart = progress < 0.9f
            if (atStart && fragment is ListFragment) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction
                        .setCustomAnimations(0, R.animator.hide)
                fragment = MotionLayoutMainFragment.newInstance()
                transaction
                        .replace(R.id.container, fragment)
                        .commitNow()
            }
        }
        lastProgress = progress
    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
    }

    private var lastProgress = 0f

    //private var fragment: Fragment? = null
    private var last: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout_fragment_example)
        var fragment: Fragment? = null
        if (savedInstanceState == null) {
            fragment = MotionLayoutMainFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow()
        }
        //toggle.setOnClickListener(this)
        //toggleAnimation.setOnClickListener(this)
        motionLayout.setTransitionListener(this)
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.toggle) {
            val transaction = supportFragmentManager.beginTransaction()
            var fragment: Fragment? = null
            fragment = if (fragment == null || fragment is MotionLayoutMainFragment) {
                last = 1f
                transaction
                        .setCustomAnimations(R.animator.show, 0)
                MotionLayoutSecondFragment.newInstance()
            } else {
                transaction
                        .setCustomAnimations(0, R.animator.hide)
                MotionLayoutMainFragment.newInstance()
            }
            transaction
                    .replace(R.id.container, fragment)
                    .commitNow()
        }
    }
}