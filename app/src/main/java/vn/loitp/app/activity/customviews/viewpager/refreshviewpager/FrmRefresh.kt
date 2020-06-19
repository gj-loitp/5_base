package vn.loitp.app.activity.customviews.viewpager.refreshviewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.core.utilities.LUIUtil
import com.views.LToast
import kotlinx.android.synthetic.main.frm_view_pager_refresh.*
import vn.loitp.app.R

class FrmRefresh : Fragment() {
    private var mPosition = 0
    private val TAG = "loitpp" + javaClass.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        avl.smoothToShow()

        context?.let {
            LToast.showShort(context = it, msg = "loadData $mPosition")
        }

        LUIUtil.setDelay(1000, Runnable {
            textView.visibility = View.VISIBLE
            avl.smoothToHide()
        })
    }

    companion object {
        const val KEY_POSITION = "KEY_POSITION"
    }
}