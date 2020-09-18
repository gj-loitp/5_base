package vn.loitp.app.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.core.base.BaseFragment
import kotlinx.android.synthetic.main.frm_bottom.*
import vn.loitp.app.R

@LayoutId(R.layout.frm_bottom)
class FrmDummy : BaseFragment() {
    override fun setTag(): String? {
        return javaClass.simpleName
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView.text = "DUMMYYYYYYYYYYYYYYYYYYY"
    }
}