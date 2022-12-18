package vn.loitp.app.activity.interviewVN

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.utilities.LStoreUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.frm_interview_vn_iq_detail.*
import kotlinx.android.synthetic.main.frm_interview_vn_iq_detail.lActionBar
import kotlinx.android.synthetic.main.frm_interview_vn_iq_detail.recyclerView
import kotlinx.android.synthetic.main.frm_interview_vn_iq_list_qa.*
import vn.loitp.app.R
import vn.loitp.app.activity.demo.fragmentFlow.BaseFragmentFlow
import vn.loitp.app.activity.interviewVN.adapter.QAAdapter

//https://drive.google.com/drive/u/0/folders/1STvbrMp_WSvPrpdm8DYzgekdlwXKsCS9
@LogTag("FrmDetail")
class FrmDetail : BaseFragmentFlow() {

    companion object {
        const val KEY_NEXT_LINK = "KEY_NEXT_LINK"
    }

    private var concatAdapter = ConcatAdapter()
    private var qaAdapter = QAAdapter(
        listQA = ArrayList(),
        isShowADefault = false,
        isShowNextLink = false
    )
    private var linkGGDrive: String? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_interview_vn_iq_detail
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            linkGGDrive = it.getString(KEY_NEXT_LINK)
        }
        setupViews()
        setupData()
    }

    override fun onBackPressed(): Boolean {
        popThisFragment()
        return true
    }

    override fun onFragmentResume() {
        super.onFragmentResume()
        print("onFragmentResume")
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                popThisFragment()
            })
            ivIconRight?.isVisible = false
            this.tvTitle?.text = FrmDetail::class.java.simpleName
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        qaAdapter.onClickRootListener = { qa, _ ->
            showDialogMsg(
                title = "Question: ${qa.q}",
                msg = "Answer: ${qa.a}"
            )
        }
        concatAdapter.addAdapter(qaAdapter)
        recyclerView.adapter = concatAdapter
    }

    private fun setupData() {
        if (linkGGDrive.isNullOrEmpty()) {
            return
        }
        showDialogProgress()
        LStoreUtil.getPkgFromGGDrive(
            linkGGDriveSetting = linkGGDrive,
            onGGFailure = { _, e ->
                hideDialogProgress()
                showShortError(e.toString())
            },
            onGGResponse = { pkg ->
                activity?.runOnUiThread {
                    hideDialogProgress()
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
