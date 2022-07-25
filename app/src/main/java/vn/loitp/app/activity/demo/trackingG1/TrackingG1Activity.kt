package vn.loitp.app.activity.demo.trackingG1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.g1.onetargetsdk.Analytics
import com.google.gson.Gson
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_tracking_g1.*
import vn.loitp.app.R

@LogTag("TrackingG1Activity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class TrackingG1Activity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_tracking_g1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = TrackingG1Activity::class.java.simpleName
        }
        btTestTracking?.setOnClickListener {
            track()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun track() {
        tvInput?.text = ""
        tvOutput?.text = "Loading..."
        Analytics.track(
            eventName = "page_view",
            properties = "{pageTitle:Passenger Information,pagePath:/passengers/}",
            onPreExecute = { input ->
                LUIUtil.printBeautyJson(input, tvInput)
            },
            onResponse = { isSuccessful, code, response ->
                tvOutput?.text =
                    "onResponse" +
                            "\nisSuccessful: $isSuccessful" +
                            "\ncode: $code" +
                            "\nresponse body: ${Gson().toJson(response)}"
            },
            onFailure = { t ->
                tvOutput?.text = "onFailure $t"
            }
        )
    }
}
