package vn.loitp.app.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.core.base.BaseFragment
import vn.loitp.app.R

class FrmDummy : BaseFragment() {
    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tv = view.findViewById<TextView>(R.id.tv)
        tv.text = "DUMMYYYYYYYYYYYYYYYYYYY"
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_bottom
    }
}