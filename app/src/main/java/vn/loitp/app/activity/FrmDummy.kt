package vn.loitp.app.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFragment
import kotlinx.android.synthetic.main.frm_bottom.*
import vn.loitp.app.R

@LayoutId(R.layout.frm_bottom)
@LogTag("FrmDummy")
class FrmDummy : BaseFragment() {

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView.text = "DUMMYYYYYYYYYYYYYYYYYYY"
    }
}