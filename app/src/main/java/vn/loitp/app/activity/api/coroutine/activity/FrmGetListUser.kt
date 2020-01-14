package vn.loitp.app.activity.api.coroutine.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.base.BaseFragment
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import com.interfaces.RecyclerViewCallback
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.frm_coroutine_get_list.*
import loitp.basemaster.R
import vn.loitp.app.activity.api.coroutine.viewmodel.TestViewModel
import vn.loitp.app.app.LApplication

class FrmGetListUser : BaseFragment() {

    lateinit var testViewModel: TestViewModel
    private var userListAdapter: UserListAdapter? = null
    private var page = 1

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
                userListAdapter?.setList(userTestList)
            }

            action.errorResponse?.let { error ->
                LLog.e(TAG, "observe error " + LApplication.gson.toJson(error))
                error.message?.let {
                    showDialogError(it, Runnable {
                        //do nothing
                    })
                }
            }
        })

        context?.let {
            userListAdapter = UserListAdapter(it, callback = { position, userTest ->
                //TODO
            })
        }
        rvUserTest.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvUserTest.adapter = userListAdapter
        LUIUtil.setScrollChange(rvUserTest, object : RecyclerViewCallback {
            override fun onTop() {
            }

            override fun onBottom() {
                page += 1
                testViewModel.getUserList(page = page)
            }
        })

        btBack.setSafeOnClickListener {
            LLog.d(TAG, "popBackStack")
            activity?.onBackPressed()
            //findNavController().popBackStack()
        }

        testViewModel.getUserList(page = page)
    }
}