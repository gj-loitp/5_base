package vn.loitp.app.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFragment
import kotlinx.android.synthetic.main.frm_bottom.*
import vn.loitp.app.R

@LogTag("FrmDummy")
class FrmDummy : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_bottom
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        textView.text = FrmDummy::class.simpleName
    }
}
