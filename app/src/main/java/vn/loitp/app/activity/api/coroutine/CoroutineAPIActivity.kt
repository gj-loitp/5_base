package vn.loitp.app.activity.api.coroutine

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.views.OnSingleClickListener
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
        viewModel.photosetAction.observe(this, Observer { action ->

            action.isDoing?.let { isDoing ->
                LLog.d(TAG, "loitpp observe isDoing $isDoing")
            }

            action.data?.let {
                LLog.d(TAG, "loitpp observe data " + LApplication.gson.toJson(it))
            }

            action.errorResponse?.let { error ->
                LLog.e(TAG, "loitpp observe error " + LApplication.gson.toJson(error))
            }
        })

        btCallAPI.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                viewModel.getPhotoset()
            }
        })
    }
}
