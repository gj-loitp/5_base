package vn.loitp.app.activity.demo.trackingG1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.g1.onetargetsdk.Analytics
import com.g1.onetargetsdk.model.MonitorEvent
import com.google.gson.Gson
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.core.utilities.LUIUtil.Companion.printBeautyJson
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
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = TrackingG1Activity::class.java.simpleName
        }
        btTestTrackingByParams?.setOnClickListener {
            trackEventByParams()
        }
        btTestTrackingByObjects?.setOnClickListener {
            trackEventByObject()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun trackEventByParams() {
        val workSpaceId = "490bf1f1-2e88-4d6d-8ec4-2bb7de74f9a8"
        val identityId = hashMapOf<String, Any>(
            "user_id" to "Params${System.currentTimeMillis()}",
            "phone" to "0123456789",
            "email" to "loitp@galaxy.one",
            "deviceId" to Analytics.getDeviceId(this)
        )
        val eventName = "event_name"
        val eventDate = System.currentTimeMillis()
        val eventData = hashMapOf<String, Any>(
            "pageTitle" to "Passenger Information",
            "pagePath" to "/home"
        )

        Analytics.trackEvent(
            workSpaceId = workSpaceId,
            identityId = identityId,
            eventName = eventName,
            eventDate = eventDate,
            eventData = eventData,
            onPreExecute = { input ->
                printBeautyJson(input, tvInput)
                tvOutput?.text = "Loading..."
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

    @SuppressLint("SetTextI18n")
    private fun trackEventByObject() {
        val monitorEvent = MonitorEvent()
        monitorEvent.workspaceId = "490bf1f1-2e88-4d6d-8ec4-2bb7de74f9a8"
        monitorEvent.identityId = hashMapOf(
            "user_id" to "Object${System.currentTimeMillis()}",
            "phone" to "0123456789",
            "email" to "loitp@galaxy.one",
            "deviceId" to Analytics.getDeviceId(this)
        )
        monitorEvent.eventName = "track_now_event"
        monitorEvent.eventDate = System.currentTimeMillis()
        monitorEvent.eventData = hashMapOf(
            "name" to "Loitp",
            "bod" to "01/01/2000",
            "player_id" to 123456
        )

        Analytics.trackEvent(
            monitorEvent = monitorEvent,
            onPreExecute = { input ->
                printBeautyJson(input, tvInput)
                tvOutput?.text = "Loading..."
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
