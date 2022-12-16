package vn.loitp.app.activity.interviewVN

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFragment
import com.loitpcore.core.utilities.LStoreUtil
import kotlinx.android.synthetic.main.activity_recycler_view_concat_adapter.*
import kotlinx.android.synthetic.main.frm_interview_vn_iq_list_package.*
import kotlinx.android.synthetic.main.frm_interview_vn_iq_list_package.recyclerView
import vn.loitp.app.R
import vn.loitp.app.activity.interviewVN.adapter.QAAdapter

//https://drive.google.com/drive/u/0/folders/1STvbrMp_WSvPrpdm8DYzgekdlwXKsCS9
@LogTag("FrmListPackage")
class FrmListPackage : BaseFragment() {

    private var concatAdapter = ConcatAdapter()
    private var qaAdapter = QAAdapter(ArrayList())

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_interview_vn_iq_list_package
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupData()
    }

    private fun setupViews() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        qaAdapter.onClickRootListener = { qa, position ->

        }
        concatAdapter.addAdapter(qaAdapter)
        recyclerView.adapter = concatAdapter
    }

    private fun setupData() {
        LStoreUtil.getPkgFromGGDrive(
            linkGGDriveSetting = "https://drive.google.com/uc?export=download&id=1bF_xmaIGsre7c-aGeDhgSnWH7IYoqq8K",
            onGGFailure = { _, e ->
                showShortError(e.toString())
            },
            onGGResponse = { pkg ->
                activity?.runOnUiThread {
                    val list = pkg?.data
                    if (list.isNullOrEmpty()) {
                        showShortError(getString(R.string.no_data_eng))
                    } else {
                        qaAdapter.setData(list)
                    }
                }
            }
        )
    }
}
