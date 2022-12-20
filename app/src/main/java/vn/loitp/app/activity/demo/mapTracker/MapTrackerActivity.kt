package vn.loitp.app.activity.demo.mapTracker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseApplication
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LMathUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.core.utilities.LUIUtil.Companion.scrollToBottom
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_map_tracker.*
import vn.loitp.app.R
import java.io.IOException
import java.util.*

@LogTag("MapTrackerActivity")
@IsFullScreen(false)
class MapTrackerActivity :
    BaseFontActivity(),
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

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_map_tracker
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        checkPermission()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MapTrackerActivity::class.java.simpleName
        }

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

    private fun checkPermission() {
        val color = if (LUIUtil.isDarkTheme()) {
            Color.WHITE
        } else {
            Color.BLACK
        }
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
            .setDialogTintColor(color, color)
            .onExplainRequestReason { scope, deniedList, _ ->
                val message = getString(R.string.app_name) + getString(R.string.needs_per)
                scope.showRequestReasonDialog(
                    permissions = deniedList,
                    message = message,
                    positiveText = getString(R.string.allow),
                    negativeText = getString(R.string.deny)
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    permissions = deniedList,
                    message = getString(R.string.per_manually_msg),
                    positiveText = getString(R.string.ok),
                    negativeText = getString(R.string.cancel)
                )
            }
            .request { allGranted, _, _ ->
                if (allGranted) {
                    buildClient()
                } else {
                    finish()//correct
                    LActivityUtil.tranOut(this)
                }
            }
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
            val loc = Loc(
                beforeTimestamp = beforeTimestamp,
                afterTimestamp = System.currentTimeMillis(),
                beforeLatLng = beforeLatLng,
                afterLatLng = latLng
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
                        // do nothing
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
        // BOUND_PADDING is an int to specify padding of bound.. try 100.
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 100)
        mGoogleMap?.animateCamera(cameraUpdate)
    }

    private fun drawRouterAnim() {
        val list = ArrayList<LatLng>()
        list.add(LatLng(20.8785614, 80.8107979))
        drawPolyLineOnMap(list)
        LUIUtil.setDelay(
            mls = 1000,
            runnable = {
                list.add(LatLng(25.8785614, 85.8107979))
                drawPolyLineOnMap(list)

                LUIUtil.setDelay(
                    mls = 1000,
                    runnable = {
                        list.add(LatLng(30.8785614, 90.8107979))
                        drawPolyLineOnMap(list)

                        LUIUtil.setDelay(
                            mls = 1000,
                            runnable = {
                                list.add(LatLng(35.8785614, 95.8107979))
                                drawPolyLineOnMap(list)
                            }
                        )
                    }
                )
            }
        )
    }

    private fun addMakerSydney() {
        val sydney = LatLng(-34.0, 151.0)
        mGoogleMap?.let {
            it.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            it.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
    }

    private fun buildClient() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
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
            mLocationSettingsRequest?.let { locationSettingsRequest ->
                settingsClient.checkLocationSettings(locationSettingsRequest)
                    .addOnSuccessListener(this) {
                        showShortInformation("All location settings are satisfied.")
                        if (ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            mLocationRequest?.let { lr ->
                                mLocationCallback?.let { lc ->
                                    Looper.myLooper()?.let { l ->
                                        mFusedLocationClient?.requestLocationUpdates(
                                            lr,
                                            lc,
                                            l
                                        )
                                    }
                                }
                            }
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
    }

    // return in meter
    private fun getDistance(startLatLng: LatLng, endLatLng: LatLng): Float {
        val results = floatArrayOf(0f)
        Location.distanceBetween(
            startLatLng.latitude,
            startLatLng.longitude,
            endLatLng.latitude,
            endLatLng.longitude,
            results
        )
        logD("getDistance results: " + BaseApplication.gson.toJson(results))
        return results[0]
    }
}
