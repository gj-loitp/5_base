package vn.loitp.app.activity.function.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LLocationUtil
import com.loitpcore.core.utilities.LUIUtil
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_func_location.*
import vn.loitp.app.BuildConfig
import vn.loitp.app.R
import java.text.DateFormat
import java.util.*

@LogTag("LocationActivity")
@IsFullScreen(false)
class LocationActivity : BaseFontActivity() {

    companion object {
        // location updates interval - 10sec
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000

        // fastest updates interval - 5 sec
        // location updates will be received if another app is requesting the locations
        // than your app can handle
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = 5000
        private const val REQUEST_CHECK_SETTINGS = 100
    }

    // location last updated time
    private var mLastUpdateTime: String? = null

    // bunch of location related apis
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mSettingsClient: SettingsClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLocationSettingsRequest: LocationSettingsRequest? = null
    private var mLocationCallback: LocationCallback? = null
    private var mCurrentLocation: Location? = null

    // boolean flag to toggle the ui
    private var mRequestingLocationUpdates: Boolean? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_location
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialize the necessary libraries
        init()

        // restore the values from saved instance state
        restoreValuesFromBundle(savedInstanceState)
        btStartLocationUpdates.setOnClickListener {
            startLocationButtonClick()
        }
        btStopLocationUpdates.setOnClickListener {
            stopLocationButtonClick()
        }
        btGetLastLocation.setOnClickListener {
            showLastKnownLocation()
        }
    }

    private fun init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mSettingsClient = LocationServices.getSettingsClient(this)
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                // location is received
                mCurrentLocation = locationResult.lastLocation
                mLastUpdateTime = DateFormat.getTimeInstance().format(Date())
                updateLocationUI()
            }
        }
        mRequestingLocationUpdates = false
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

    /**
     * Restoring values from saved instance state
     */
    private fun restoreValuesFromBundle(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates")
            }
            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location")
            }
            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on")
            }
        }
        updateLocationUI()
    }

    /**
     * Update the UI displaying the location data
     * and toggling the buttons
     */
    @SuppressLint("SetTextI18n")
    private fun updateLocationUI() {
        mCurrentLocation?.let {

            var moreInfor = ""
            LLocationUtil.getCityByLatLon(
                it.latitude,
                it.longitude
            ) { address: String?, city: String?, state: String?, country: String? ->
                moreInfor += "$address - $city - $state - $country"
            }

            tvLocationResult.text =
                "Lat: " + it.latitude + ", " + "Lng: " + it.longitude + ", more infor: $moreInfor"

            // giving a blink animation on TextView
            tvLocationResult.alpha = 0f
            tvLocationResult.animate().alpha(1f).duration = 300

            // location last updated time
            tvUpdatedOn.text = "Last updated on: $mLastUpdateTime"
        }
        toggleButtons()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mRequestingLocationUpdates?.let {
            outState.putBoolean("is_requesting_updates", it)
        }
        outState.putParcelable("last_known_location", mCurrentLocation)
        outState.putString("last_updated_on", mLastUpdateTime)
    }

    private fun toggleButtons() {
        if (mRequestingLocationUpdates == true) {
            btStartLocationUpdates.isEnabled = false
            btStopLocationUpdates.isEnabled = true
        } else {
            btStartLocationUpdates.isEnabled = true
            btStopLocationUpdates.isEnabled = false
        }
    }

    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private fun startLocationUpdates() {
        mSettingsClient?.let { settingsClient ->
            mLocationSettingsRequest?.let { locationSettingsRequest ->
                settingsClient.checkLocationSettings(locationSettingsRequest)
                    .addOnSuccessListener(this) {
                        logD("All location settings are satisfied.")
                        showShortInformation("Started location updates!")
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
                            updateLocationUI()
                        } else {
                            showShortInformation("Dont have permission ACCESS_FINE_LOCATION && ACCESS_COARSE_LOCATION")
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
                                    rae.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                                } catch (sie: SendIntentException) {
                                    sie.printStackTrace()
                                }
                            }
                            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                                val errorMessage =
                                    "Location settings are inadequate, and cannot be fixed here. Fix in Settings."
                                logD(errorMessage)
                                showShortInformation(errorMessage)
                            }
                        }
                        updateLocationUI()
                    }
            }
        }
    }

    private fun startLocationButtonClick() {
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
                    mRequestingLocationUpdates = true
                    startLocationUpdates()
                } else {
                    finish()
                    LActivityUtil.tranOut(this)
                }
            }
    }

    private fun stopLocationButtonClick() {
        mRequestingLocationUpdates = false
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        // Removing location updates
        mLocationCallback?.let { lc ->
            mFusedLocationClient?.removeLocationUpdates(lc)
                ?.addOnCompleteListener(this) {
                    showShortInformation("Location updates stopped!")
                    toggleButtons()
                }
        }
    }

    private fun showLastKnownLocation() {
        if (mCurrentLocation != null) {
            showShortInformation("Lat: " + mCurrentLocation?.latitude + ",Lng: " + mCurrentLocation?.longitude)
        } else {
            showShortInformation("Last known location is not available!")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> when (resultCode) {
                Activity.RESULT_OK -> {
                    logE("User agreed to make required location settings changes.")
                }
                Activity.RESULT_CANCELED -> {
                    logE("User chose not to make required location settings changes.")
                    mRequestingLocationUpdates = false
                }
            }
        }
    }

    private fun openSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    public override fun onResume() {
        super.onResume()

        // Resuming location updates depending on button state and
        // allowed permissions
        if (mRequestingLocationUpdates != null && checkPermissions()) {
            startLocationUpdates()
        }
        updateLocationUI()
    }

    private fun checkPermissions(): Boolean {
        val permissionState =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    override fun onPause() {
        super.onPause()
        if (mRequestingLocationUpdates != null) {
            // pausing location updates
            stopLocationUpdates()
        }
    }
}
