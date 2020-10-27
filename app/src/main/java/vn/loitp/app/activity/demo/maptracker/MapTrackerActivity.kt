package vn.loitp.app.activity.demo.maptracker

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.utilities.LDialogUtil
import com.core.utilities.LMathUtil
import com.core.utilities.LUIUtil
import com.core.utilities.LUIUtil.Companion.scrollToBottom
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.interfaces.Callback2
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_map_tracker.*
import vn.loitp.app.R
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

@LayoutId(R.layout.activity_map_tracker)
@LogTag("MapTrackerActivity")
@IsFullScreen(false)
class MapTrackerActivity : BaseFontActivity(),
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    companion object {
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 8000
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = 5000
    }

    private var googleApiClient: GoogleApiClient? = null
    private var currentLocationMarker: Marker? = null
    private var mGoogleMap: GoogleMap? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mSettingsClient: SettingsClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLocationSettingsRequest: LocationSettingsRequest? = null
    private var mLocationCallback: LocationCallback? = null
    private var mCurrentLocation: Location? = null
    private val listLoc = ArrayList<Loc>()
    private var isShowDialogCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        initLocation()

        btLocation.setSafeOnClickListener {
            startLocationUpdates()
        }
        btAddMaker.setSafeOnClickListener {
            addMakerSydney()
        }
        btRouter.setSafeOnClickListener {
            drawRouter()
        }

        btRouterAnim.setSafeOnClickListener {
            drawRouterAnim()
        }

        btDistance.setSafeOnClickListener {
            val startLatLng = LatLng(10.8785614, 106.8107979)
            val endLatLng = LatLng(30.8785614, 145.8107979)
            val distance = getDistance(startLatLng = startLatLng, endLatLng = endLatLng)
            showShortInformation("distance: $distance (m)")
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isShowDialogCheck) {
            checkPermission()
        }
    }

    //region permisson
    private fun checkPermission() {
        showShortInformation("checkPermission")
        isShowDialogCheck = true
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (report.areAllPermissionsGranted()) {
                            buildClient()
                        } else {
                            showShouldAcceptPermission()
                        }

                        if (report.isAnyPermissionPermanentlyDenied) {
                            showSettingsDialog()
                        }
                        isShowDialogCheck = true
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                })
                .onSameThread()
                .check()
    }

    private fun showSettingsDialog() {
        val alertDialog = LDialogUtil.showDialog2(
                context = this,
                title = "Need Permissions",
                msg = "This app needs permission to use this feature. You can grant them in app settings.",
                button1 = "GOTO SETTINGS",
                button2 = "Cancel",
                callback2 = object : Callback2 {
                    override fun onClick1() {
                        isShowDialogCheck = false
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivityForResult(intent, 101)
                    }

                    override fun onClick2() {
                        onBackPressed()
                    }
                })
        alertDialog.setCancelable(false)
    }

    private fun showShouldAcceptPermission() {
        val alertDialog = LDialogUtil.showDialog2(
                context = this,
                title = "Need Permissions",
                msg = "This app needs permission to use this feature.",
                button1 = "Okay",
                button2 = "Cancel",
                callback2 = object : Callback2 {
                    override fun onClick1() {
                        checkPermission()
                    }

                    override fun onClick2() {
                        onBackPressed()
                    }
                })
        alertDialog.setCancelable(false)
    }
    //endregion

    private fun onChangeLocation() {
        logD("onChangeLocation " + mCurrentLocation?.latitude + " - " + mCurrentLocation?.longitude)

        currentLocationMarker?.remove()
        mCurrentLocation?.let { location ->

            val latRound = LMathUtil.roundDouble(value = location.latitude, newScale = 4)
            val lngRound = LMathUtil.roundDouble(value = location.longitude, newScale = 4)

            val latLng = LatLng(latRound, lngRound)

            val beforeLoc = listLoc.lastOrNull()
            val beforeTimestamp = beforeLoc?.afterTimestamp ?: 0
            val beforeLatLng = beforeLoc?.afterLatLng
            val afterLatLng = latLng
            val loc = Loc(
                    beforeTimestamp = beforeTimestamp,
                    afterTimestamp = System.currentTimeMillis(),
                    beforeLatLng = beforeLatLng,
                    afterLatLng = afterLatLng
            )
            listLoc.add(element = loc)
            var log = ""
            listLoc.forEach {
                log += "\n\n\n${it.beforeTimestamp} : ${it.beforeLatLng?.latitude} - ${it.beforeLatLng?.longitude} ~ ${it.afterLatLng?.latitude} - ${it.afterLatLng?.longitude} -> ${it.getDistance()}(m) - ${it.getTimeInSecond()}(s) -> ${it.getSpeed()}(m/s)"
            }
            tvLog.text = log
            nsv.scrollToBottom()

            val markerOptions = MarkerOptions()
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            markerOptions.position(latLng)

            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val providerList = locationManager.allProviders

            if (providerList.size > 0) {
                val longitude = location.longitude
                val latitude = location.latitude
                val geoCoder = Geocoder(applicationContext, Locale.getDefault())
                try {
                    val listAddresses = geoCoder.getFromLocation(latitude, longitude, 1)
//                    logD("listAddresses " + LApplication.gson.toJson(listAddresses))
                    if (listAddresses.isNullOrEmpty()) {
                        //do nothing
                    } else {
                        markerOptions.title(listAddresses[0].getAddressLine(0))
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            currentLocationMarker = mGoogleMap?.addMarker(markerOptions)
            mGoogleMap?.let {
                it.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                it.animateCamera(CameraUpdateFactory.zoomTo(11f))
            }
        }
    }

    private fun initLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mSettingsClient = LocationServices.getSettingsClient(this)
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                mCurrentLocation = locationResult.lastLocation
                onChangeLocation()
            }
        }
        mLocationRequest = LocationRequest()
        mLocationRequest?.let {
            it.interval = UPDATE_INTERVAL_IN_MILLISECONDS
            it.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            it.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

            val builder = LocationSettingsRequest.Builder()
            builder.addLocationRequest(it)
            mLocationSettingsRequest = builder.build()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        mGoogleMap?.apply {
            mapType = GoogleMap.MAP_TYPE_NORMAL
            uiSettings.isZoomControlsEnabled = false
            uiSettings.isZoomGesturesEnabled = true
            uiSettings.isCompassEnabled = true
//            uiSettings.isMapToolbarEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
            uiSettings.isRotateGesturesEnabled = true
            uiSettings.isScrollGesturesEnabled = true
            uiSettings.isTiltGesturesEnabled = true
        }
        buildClient()
    }

    private fun drawRouter() {
        val list = ArrayList<LatLng>()
        list.add(LatLng(10.8785614, 106.8107979))
        list.add(LatLng(11.8785614, 107.8107979))
        list.add(LatLng(12.8785614, 108.8107979))
        list.add(LatLng(13.8785614, 109.8107979))
        list.add(LatLng(14.8785614, 110.8107979))
        list.add(LatLng(15.8785614, 115.8107979))
        list.add(LatLng(20.8785614, 120.8107979))
        list.add(LatLng(25.8785614, 125.8107979))
        list.add(LatLng(30.8785614, 130.8107979))
        drawPolyLineOnMap(list)
    }

    private fun drawPolyLineOnMap(list: List<LatLng>) {
        val polyOptions = PolylineOptions()
        polyOptions.color(Color.RED)
        polyOptions.width(5f)
        polyOptions.addAll(list)
        mGoogleMap?.clear()
        mGoogleMap?.addPolyline(polyOptions)
        val builder = LatLngBounds.Builder()
        for (latLng in list) {
            builder.include(latLng)
        }
        val bounds = builder.build()
        //BOUND_PADDING is an int to specify padding of bound.. try 100.
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 100)
        mGoogleMap?.animateCamera(cameraUpdate)
    }

    private fun drawRouterAnim() {
        val list = ArrayList<LatLng>()
        list.add(LatLng(20.8785614, 80.8107979))
        drawPolyLineOnMap(list)
        LUIUtil.setDelay(1000, Runnable {
            list.add(LatLng(25.8785614, 85.8107979))
            drawPolyLineOnMap(list)

            LUIUtil.setDelay(1000, Runnable {
                list.add(LatLng(30.8785614, 90.8107979))
                drawPolyLineOnMap(list)

                LUIUtil.setDelay(1000, Runnable {
                    list.add(LatLng(35.8785614, 95.8107979))
                    drawPolyLineOnMap(list)
                })
            })
        })
    }

    private fun addMakerSydney() {
        val sydney = LatLng(-34.0, 151.0)
        mGoogleMap?.let {
            it.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            it.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
    }

    private fun buildClient() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (googleApiClient == null) {
                    buildGoogleApiClient()
                }
                mGoogleMap?.isMyLocationEnabled = true
            }
        } else {
            if (googleApiClient == null) {
                buildGoogleApiClient()
            }
            mGoogleMap?.isMyLocationEnabled = true
        }
    }

    @Synchronized
    private fun buildGoogleApiClient() {
        googleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        googleApiClient?.connect()
    }

    override fun onConnected(bundle: Bundle?) {
        logD("onConnected")
        startLocationUpdates()
    }

    override fun onConnectionSuspended(i: Int) {
        logD("onConnectionSuspended")
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        logD("onConnectionFailed")
    }

    private fun startLocationUpdates() {
        mSettingsClient?.let { settingsClient ->
            settingsClient.checkLocationSettings(mLocationSettingsRequest)
                    .addOnSuccessListener(this) {
                        showShortInformation("All location settings are satisfied.")
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            mFusedLocationClient?.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
                            onChangeLocation()
                        } else {
                            showShortInformation("Dont have permission ACCESS_FINE_LOCATION && ACCESS_COARSE_LOCATION")
                        }
                    }
                    .addOnFailureListener(this) { e ->
                        showShortError(e.toString())
                        onChangeLocation()
                    }
        }
    }

    //return in meter
    private fun getDistance(startLatLng: LatLng, endLatLng: LatLng): Float {
        val results = floatArrayOf(0f)
        Location.distanceBetween(
                startLatLng.latitude,
                startLatLng.longitude,
                endLatLng.latitude,
                endLatLng.longitude,
                results)
        logD("getDistance results: " + BaseApplication.gson.toJson(results))
        return results[0]
    }
}
