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
import com.core.base.BaseFontActivity
import com.core.utilities.LDialogUtil
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
import vn.loitp.app.app.LApplication
import java.io.IOException
import java.text.DateFormat
import java.util.*

class MapTrackerActivity : BaseFontActivity(),
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    companion object {
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 5000
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = 2000
    }

    private var googleApiClient: GoogleApiClient? = null
    private var currentLocationMarker: Marker? = null
    private var mGoogleMap: GoogleMap? = null
    private var mLastUpdateTime: String? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mSettingsClient: SettingsClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLocationSettingsRequest: LocationSettingsRequest? = null
    private var mLocationCallback: LocationCallback? = null
    private var mCurrentLocation: Location? = null

    private var isShowDialogCheck = false

    override fun setTag(): String? {
        return "loitpp" + javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_map_tracker
    }

    override fun setFullScreen(): Boolean {
        return false
    }

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
    }

    override fun onResume() {
        super.onResume()
        if (!isShowDialogCheck) {
            checkPermission()
        }
    }

    private fun onChangeLocation() {
        logD("onChangeLocation " + mCurrentLocation?.latitude + " - " + mCurrentLocation?.longitude + ", mLastUpdateTime:" + mLastUpdateTime)

        currentLocationMarker?.remove()
        mCurrentLocation?.let { location ->
            val latLng = LatLng(location.latitude, location.longitude)
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)

            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val providerList = locationManager.allProviders

            if (providerList.size > 0) {
                val longitude = location.longitude
                val latitude = location.latitude
                val geoCoder = Geocoder(applicationContext, Locale.getDefault())
                try {
                    val listAddresses = geoCoder.getFromLocation(latitude, longitude, 1)
                    logD("listAddresses " + LApplication.gson.toJson(listAddresses))
                    if (listAddresses.isNullOrEmpty()) {
                        //do nothing
                    } else {
                        markerOptions.title(listAddresses[0].getAddressLine(0))
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
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
                mLastUpdateTime = DateFormat.getTimeInstance().format(Date())
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient()
                mGoogleMap?.isMyLocationEnabled = true
            }
        } else {
            buildGoogleApiClient()
            mGoogleMap?.isMyLocationEnabled = true
        }
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

    private fun addMakerSydney() {
        val sydney = LatLng(-34.0, 151.0)
        mGoogleMap?.let {
            it.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            it.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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

    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    private fun checkPermission() {
        isShowDialogCheck = true
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (report.areAllPermissionsGranted()) {
                            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                if (googleApiClient == null) {
                                    buildGoogleApiClient()
                                }
                                mGoogleMap?.isMyLocationEnabled = true
                            }
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
                context = activity,
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
                context = activity,
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

    private fun startLocationUpdates() {
        mSettingsClient?.let { settingsClient ->
            settingsClient.checkLocationSettings(mLocationSettingsRequest)
                    .addOnSuccessListener(this) {
                        showShort("All location settings are satisfied.")
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            mFusedLocationClient?.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
                            onChangeLocation()
                        } else {
                            showShort("Dont have permission ACCESS_FINE_LOCATION && ACCESS_COARSE_LOCATION")
                        }
                    }
                    .addOnFailureListener(this) { e ->
                        showShort(e.toString())
                        onChangeLocation()
                    }
        }
    }
}
