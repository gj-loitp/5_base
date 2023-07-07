package vn.loitp.up.a.demo.trackingG1

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.core.view.isVisible
import com.g1.onetargetsdk.core.Analytics
import com.g1.onetargetsdk.model.MonitorEvent
import com.google.gson.Gson
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.printBeautyJson
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.ATrackingG1Binding

@LogTag("TrackingG1Activity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class TrackingG1Activity : BaseActivityFont() {
    private lateinit var binding: ATrackingG1Binding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATrackingG1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = TrackingG1Activity::class.java.simpleName
        }
        binding.btTestTrackingByParams.setOnClickListener {
            trackEventByParams()
        }
        binding.btTestTrackingByObjects.setOnClickListener {
            trackEventByObject()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun trackEventByParams() {
        val identityId = hashMapOf<String, Any>(
            "phone" to "0766040293",
            "email" to "loitp@galaxy.one",
        )
        val profile = ArrayList<HashMap<String, Any>>()
        profile.add(
            hashMapOf(
                "index" to 1,
                "full_name" to "Loi Android Native ${Build.MODEL}",
                "gender" to "Male",
                "address" to "45A Nguyễn Thị Minh Khai, phường 3, quận 3",
                "skyclub" to "hoangkim2512",
                "city" to "Ho Chi Minh",
                "country" to "Viet Nam",
                "email" to "myemail@gmail.com",
                "phone" to "0969696969",
                "Unsubscribed from emails" to "False",
            )
        )
        val eventName = "event_name"
        val eventData = hashMapOf<String, Any>(
            "pageTitle" to "Passenger Information",
            "pagePath" to "/home"
        )

        Analytics.trackEvent(
            identityId = identityId,
            profile = profile,
            eventName = eventName,
            eventData = eventData,
            onPreExecute = { input ->
                binding.tvInput.printBeautyJson(input)
                binding.tvOutput.text = "Loading..."
            },
            onResponse = { isSuccessful, code, response ->
                binding.tvOutput.text =
                    "onResponse" +
                            "\nisSuccessful: $isSuccessful" +
                            "\ncode: $code" +
                            "\nresponse body: ${Gson().toJson(response)}"
            },
            onFailure = { t ->
                binding.tvOutput.text = "onFailure $t"
            }
        )
    }

    @SuppressLint("SetTextI18n")
    private fun trackEventByObject() {
        val monitorEvent = MonitorEvent()
        monitorEvent.workspaceId = C.workSpaceId
        monitorEvent.identityId = hashMapOf(
            "phone" to "0766040293",
            "email" to "loitp@galaxy.one",
        )
        val profile = ArrayList<HashMap<String, Any>>()
        profile.add(
            hashMapOf(
                "index" to 1,
                "full_name" to "Loi Android Native ${Build.MODEL}",
                "gender" to "Male",
                "address" to "45A Nguyễn Thị Minh Khai, phường 3, quận 3",
                "skyclub" to "hoangkim2512",
                "city" to "Ho Chi Minh",
                "country" to "Viet Nam",
                "email" to "myemail@gmail.com",
                "phone" to "0969696969",
                "Unsubscribed from emails" to "False",
            )
        )
        monitorEvent.profile = profile
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
                binding.tvInput.printBeautyJson(input)
                binding.tvOutput.text = "Loading..."
            },
            onResponse = { isSuccessful, code, response ->
                binding.tvOutput.text =
                    "onResponse" +
                            "\nisSuccessful: $isSuccessful" +
                            "\ncode: $code" +
                            "\nresponse body: ${Gson().toJson(response)}"
            },
            onFailure = { t ->
                binding.tvOutput.text = "onFailure $t"
            }
        )
    }
}
