package vn.loitp.app.activity.demo.maptracker

import android.location.Location
import com.google.android.gms.maps.model.LatLng

data class Loc(
        var beforeTimestamp: Long = 0,
        var afterTimestamp: Long = 0,
        var beforeLatLng: LatLng? = null,
        var afterLatLng: LatLng? = null
) {
    //return in meter
    fun getDistance(): Float? {
        beforeLatLng?.let { before ->
            afterLatLng?.let { after ->
                val results = floatArrayOf(0f)
                Location.distanceBetween(
                        before.latitude,
                        before.longitude,
                        after.latitude,
                        after.longitude,
                        results)
                return results[0]
            }
        }
        return null
    }

    fun getTimeInSecond(): Long {
        return afterTimestamp / 1000L - beforeTimestamp / 1000L
    }
}
