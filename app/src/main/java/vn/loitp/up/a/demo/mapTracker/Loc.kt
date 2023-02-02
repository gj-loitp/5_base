package vn.loitp.up.a.demo.mapTracker

import android.location.Location
import androidx.annotation.Keep
import com.google.android.gms.maps.model.LatLng

@Keep
data class Loc(
    var beforeTimestamp: Long = 0,
    var afterTimestamp: Long = 0,
    var beforeLatLng: LatLng? = null,
    var afterLatLng: LatLng? = null
) {
    // return in meter
    fun getDistance(): Float? {
        beforeLatLng?.let { before ->
            afterLatLng?.let { after ->
                val results = floatArrayOf(0f)
                Location.distanceBetween(
                    before.latitude,
                    before.longitude,
                    after.latitude,
                    after.longitude,
                    results
                )
                return results[0]
            }
        }
        return null
    }

    fun getTimeInSecond(): Long {
        if (beforeTimestamp == 0L) {
            return 0
        }
        return afterTimestamp / 1000L - beforeTimestamp / 1000L
    }

    // return speed m/s
    fun getSpeed(): Float {
        val s = getDistance() ?: 0F
        val t = getTimeInSecond().toFloat()
        return s / t
    }
}
