package vn.loitp.app.activity.demo.maptracker

import android.Manifest
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.core.base.BaseFontActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import vn.loitp.app.R
import java.io.IOException
import java.text.DateFormat
import java.util.*

class MapTrackerActivity : BaseFontActivity(),
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    companion object {
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99

        // location updates interval - 10sec
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 5000

        // fastest updates interval - 5 sec
        // location updates will be received if another app is requesting the locations
        // than your app can handle
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = 2000
        private const val REQUEST_CHECK_SETTINGS = 100
    }

    private var googleApiClient: GoogleApiClient? = null
    private var currentLocationMarker: Marker? = null
    private var mGoogleMap: GoogleMap? = null

    // location last updated time
    private var mLastUpdateTime: String? = null

    // bunch of location related apis
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mSettingsClient: SettingsClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLocationSettingsRequest: LocationSettingsRequest? = null
    private var mLocationCallback: LocationCallback? = null
    private var mCurrentLocation: Location? = null

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission()
        }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        initLocation()
        startLocationUpdates()
    }

    private fun onChangeLocation() {
        logD("onChangeLocation " + mCurrentLocation?.latitude + " - " + mCurrentLocation?.longitude + ", mLastUpdateTime:" + mLastUpdateTime)

        currentLocationMarker?.remove()
        mCurrentLocation?.let { location ->
            //Showing Current Location Marker on Map
            val latLng = LatLng(location.latitude, location.longitude)
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val provider = locationManager.getBestProvider(Criteria(), true)
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            var locations: Location? = null
            provider?.let {
                locations = locationManager.getLastKnownLocation(it)
            }
            val providerList = locationManager.allProviders

            locations?.let { loc ->
                if (providerList.size > 0) {
                    val longitude = loc.longitude
                    val latitude = loc.latitude
                    val geoCoder = Geocoder(applicationContext, Locale.getDefault())
                    try {
                        val listAddresses = geoCoder.getFromLocation(latitude, longitude, 1)
                        if (listAddresses.isNullOrEmpty()) {
                            //do nothing
                        } else {
                            val state = listAddresses[0].adminArea
                            val country = listAddresses[0].countryName
                            val subLocality = listAddresses[0].subLocality
                            markerOptions.title("$latLng,$subLocality,$state,$country")
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
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
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isZoomGesturesEnabled = true
            uiSettings.isCompassEnabled = true
        }

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient()
                mGoogleMap?.isMyLocationEnabled = true
            }
        } else {
            buildGoogleApiClient()
            mGoogleMap?.isMyLocationEnabled = true
        }

//        addMakerSydney()

        //draw router
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
        // Add a marker in Sydney and move the camera
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

    override fun onConnectionSuspended(i: Int) {}

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (googleApiClient == null) {
                        buildGoogleApiClient()
                    }
                    mGoogleMap?.isMyLocationEnabled = true
                }
            } else {
                showShort("Permission denied")
            }
        }
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
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, 10)
        mGoogleMap?.animateCamera(cu)
    }

    private fun startLocationUpdates() {
        mSettingsClient?.let { settingsClient ->
            settingsClient.checkLocationSettings(mLocationSettingsRequest)
                    .addOnSuccessListener(this) {
                        logD("All location settings are satisfied.")
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            mFusedLocationClient?.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())

                            onChangeLocation()
                        } else {
                            showShort("Dont have permission ACCESS_FINE_LOCATION && ACCESS_COARSE_LOCATION")
                        }
                    }
                    .addOnFailureListener(this) { e ->
                        when ((e as ApiException).statusCode) {
                            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                                logD("Location settings are not satisfied. Attempting to upgrade location settings ")
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    val rae = e as ResolvableApiException
                                    rae.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS)
                                } catch (sie: IntentSender.SendIntentException) {
                                    sie.printStackTrace()
                                }
                            }
                            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                                val errorMessage = "Location settings are inadequate, and cannot be fixed here. Fix in Settings."
                                logD(errorMessage)
                                showShort(errorMessage)
                            }
                        }
                        onChangeLocation()
                    }
        }
    }
}
