package vn.loitp.app.activity.customviews.viewpager.autoviewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import loitp.basemaster.R

class FrmIv : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_iv, container, false)
    }

    companion object {
        fun newInstance(): FrmIv {
            return FrmIv()
        }
    }
}
