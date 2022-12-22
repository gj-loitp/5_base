package vn.loitp.app.a.cv.viewPager.refresh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.loitp.core.utilities.LDialogUtil
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.toast.LToast
import kotlinx.android.synthetic.main.frm_view_pager_refresh.*
import vn.loitp.R

class FrmRefresh : Fragment() {

    companion object {
        const val KEY_POSITION = "KEY_POSITION"
    }

    private var mPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let { bundle ->
            mPosition = bundle.getInt(KEY_POSITION)
        }
        return inflater.inflate(R.layout.frm_view_pager_refresh, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isVisibleToUser && !isLoaded) {
            loadData()
            isLoaded = true
        }
    }

    private var isLoaded = false
    private var isVisibleToUser = false

    //TODO fix setUserVisibleHint
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        this.isVisibleToUser = isVisibleToUser
        if (isVisibleToUser && isAdded) {
            loadData()
            isLoaded = true
        }
    }

    private fun loadData() {
        textView.visibility = View.INVISIBLE
        LDialogUtil.showProgress(progressBar)

        context?.let {
            LToast.show(msg = "loadData")
        }

        LUIUtil.setDelay(
            mls = 1000,
            runnable = {
                textView?.visibility = View.VISIBLE
                LDialogUtil.hideProgress(progressBar)
            }
        )
    }
}
