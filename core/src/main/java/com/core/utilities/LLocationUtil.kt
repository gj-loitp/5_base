package com.core.utilities

import android.location.Geocoder
import com.utils.util.Utils
import java.io.IOException
import java.util.*

object LLocationUtil {

    fun getCityByLatLon(
            latitude: Double,
            longitude: Double,
            result: ((address: String, city: String, state: String, country: String) -> Unit)? = null
    ) {
        val gc = Geocoder(Utils.getContext(), Locale.getDefault())
        try {
            val addresses = gc.getFromLocation(latitude, longitude, 1) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            val address = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            val city = addresses[0].locality
            val state = addresses[0].adminArea
            val country = addresses[0].countryName
            result?.invoke(address, city, state, country)
        } catch (e: IOException) {
            e.printStackTrace()
            result?.invoke("", "", "", "")
        }
    }
}
