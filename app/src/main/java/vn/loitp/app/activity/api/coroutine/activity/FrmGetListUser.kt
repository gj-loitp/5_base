package vn.loitp.app.activity.api.coroutine.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.core.base.BaseFragment
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import com.views.OnSingleClickListener
import kotlinx.android.synthetic.main.frm_coroutine_get_list.*
import loitp.basemaster.R
import vn.loitp.app.activity.api.coroutine.viewmodel.TestViewModel
import vn.loitp.app.app.LApplication

class FrmGetListUser : BaseFragment() {

    lateinit var testViewModel: TestViewModel

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_coroutine_get_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        testViewModel = getViewModel(TestViewModel::class.java)
        testViewModel.userAction.observe(viewLifecycleOwner, Observer { action ->

            action.isDoing?.let { isDoing ->
                LLog.d(TAG, "observe isDoing $isDoing")
            }

            action.data?.let { userTestList ->
                LLog.d(TAG, "observe data " + LApplication.gson.toJson(userTestList))
                LUIUtil.printBeautyJson(userTestList, tvJson)
            }

            action.errorResponse?.let { error ->
                LLog.e(TAG, "observe error " + LApplication.gson.toJson(error))
                LUIUtil.printBeautyJson(error, tvJson)
            }
        })

        btCallAPI.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                testViewModel.getUserList()
            }
        })
        //findNavController().popBackStack()
    }
}