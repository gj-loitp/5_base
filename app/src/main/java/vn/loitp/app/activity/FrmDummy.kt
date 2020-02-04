package vn.loitp.app.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.core.base.BaseFragment
import vn.loitp.app.R

class FrmDummy : BaseFragment() {
    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val tv = frmRootView.findViewById<TextView>(R.id.tv)
        tv.text = "DUMMYYYYYYYYYYYYYYYYYYY"
        //val list: List<String> = ArrayList<String>()
        return frmRootView
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_bottom
    }
}