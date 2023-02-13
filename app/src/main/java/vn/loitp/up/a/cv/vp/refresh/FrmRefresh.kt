package vn.loitp.up.a.cv.vp.refresh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.loitp.core.ext.hideProgress
import com.loitp.core.ext.setDelay
import com.loitp.core.ext.showProgress
import com.loitp.views.toast.LToast
import vn.loitp.databinding.FVpRefreshBinding

class FrmRefresh : Fragment() {

    companion object {
        const val KEY_POSITION = "KEY_POSITION"
    }

    private lateinit var binding: FVpRefreshBinding

    private var mPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let { bundle ->
            mPosition = bundle.getInt(KEY_POSITION)
        }
        binding = FVpRefreshBinding.inflate(inflater, container, false)
        return binding.root
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
        binding.textView.visibility = View.INVISIBLE
        binding.progressBar.showProgress()

        context?.let {
            LToast.show(msg = "loadData")
        }

        setDelay(
            mls = 1000,
            runnable = {
                binding.textView.visibility = View.VISIBLE
                binding.progressBar.hideProgress()
            }
        )
    }
}
