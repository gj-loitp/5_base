package vn.loitp.app.activity.api.coroutine

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import kotlinx.android.synthetic.main.activity_coroutine_api.*
import loitp.basemaster.R
import vn.loitp.app.app.LApplication

class CoroutineAPIActivity : BaseFontActivity() {
    lateinit var viewModel: AuthenViewModel

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_coroutine_api
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(AuthenViewModel::class.java)
        viewModel.loginAction.observe(this, Observer { action ->

            action.isDoing?.let { isDoing ->
                LLog.d(TAG, "loitpp isDoing $isDoing")
            }

            action.data?.let {
                LLog.d(TAG, "loitpp data " + LApplication.gson.toJson(it))
            }

            action.errorResponse?.let { error ->
                LLog.e(TAG, "loitpp error " + LApplication.gson.toJson(error))
            }
        })

        btCallAPI.setOnClickListener {
            viewModel.loginVinEcoFarm()
        }
    }

    private fun <T : ViewModel> getViewModel(className: Class<T>): T {
        return ViewModelProvider(this).get(className)
    }
}
