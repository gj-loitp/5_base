package vn.loitp.app.activity.customviews.layout.draggablepanel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import vn.loitp.app.R

class FrmTestTop : Fragment() {

    companion object {
        fun newInstance(): FrmTestTop {
            return FrmTestTop()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frm_test, container, false)
    }

}
