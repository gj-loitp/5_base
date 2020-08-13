package vn.loitp.app.activity.demo.maptracker

import com.google.android.gms.maps.model.LatLng

data class Loc(
        var timestamp: Long = 0,
        var latLng: LatLng? = null
)
