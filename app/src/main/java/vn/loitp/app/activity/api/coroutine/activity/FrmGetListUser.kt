package vn.loitp.app.activity.api.coroutine.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.base.BaseFragment
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import com.interfaces.RecyclerViewCallback
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.frm_coroutine_get_list.*
import vn.loitp.app.R
import vn.loitp.app.activity.api.coroutine.viewmodel.TestViewModel
import vn.loitp.app.app.LApplication

class FrmGetListUser : BaseFragment() {
    private var testViewModel: TestViewModel? = null
    private var userListAdapter: UserListAdapter? = null
    private var page = 1

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_coroutine_get_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        LLog.d(TAG, "onViewCreated")
        setupViews()
        setupViewModels()

        testViewModel?.let { tvm ->
            if (tvm.userTestListLiveData.value == null) {
                LLog.d(TAG, "tvm.userAction.value == null")
                testViewModel?.getUserTestListByPage(page = page, isRefresh = false)
            } else {
                LLog.d(TAG, "tvm.userAction.value != null")
            }
        }
    }

    private fun setupViews() {
        context?.let {
            userListAdapter = UserListAdapter(it, callback = { _, userTest ->
                val bundle = Bundle()
                bundle.putSerializable(FrmUser.KEY_USER, userTest)
                findNavController().navigate(R.id.action_frmGetListUser_to_frmUser, bundle)
            })
        }
        rvUserTest.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvUserTest.adapter = userListAdapter
        LUIUtil.setScrollChange(rvUserTest, object : RecyclerViewCallback {
            override fun onTop() {
            }

            override fun onBottom() {
                LLog.d(TAG, "onBottom")
                page += 1
                testViewModel?.getUserTestListByPage(page = page, isRefresh = false)
            }
        })

        btBack.setSafeOnClickListener {
            LLog.d(TAG, "popBackStack")
            activity?.onBackPressed()
            //findNavController().popBackStack()
        }

        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            LLog.d(TAG, "setOnRefreshListener")
            page = 1
            testViewModel?.getUserTestListByPage(page = page, isRefresh = true)
        }
    }

    private fun setupViewModels() {
        testViewModel = getViewModel(TestViewModel::class.java)
        testViewModel?.let { tvm ->
            tvm.userActionLiveData.observe(viewLifecycleOwner, Observer { action ->
                LLog.d(TAG, "userAction.observe action.isDoing ${action.isDoing}")
                action.isDoing?.let { isDoing ->
                    //LLog.d(TAG, "observe isDoing $isDoing")
                    swipeRefreshLayout.isRefreshing = isDoing
                }

                action.data?.let { userTestList ->
                    //LLog.d(TAG, "observe data " + LApplication.gson.toJson(userTestList))
                    val isRefresh = action.isSwipeToRefresh
                    tvm.addUserList(userTestList = userTestList, isRefresh = isRefresh)
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
            tvm.userTestListLiveData.observe(viewLifecycleOwner, Observer {
                LLog.d(TAG, "userTestList.observe size: ${it?.size}")
                userListAdapter?.setList(it)
            })
        }
    }
}
