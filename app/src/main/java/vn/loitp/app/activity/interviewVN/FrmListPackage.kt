package vn.loitp.app.activity.interviewVN

import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFragment
import kotlinx.android.synthetic.main.frm_interview_vn_iq_list_package.*
import vn.loitp.app.R

@LogTag("FrmListPackage")
class FrmListPackage : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_interview_vn_iq_list_package
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
    }
}
