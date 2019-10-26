package com.core.utilities

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager

object LOsUtil {
    /**
     * @return True if the version of Android that we're running on is at least M
     * (API level 23).
     */
    var isAtLeastM: Boolean = false
        private set

    /**
     * @return The Android API version of the OS that we're currently running on.
     */
    val apiVersion: Int
        get() = android.os.Build.VERSION.SDK_INT

    var sRequiredLocationPermission = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

    var sRequiredStoragePermission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    var sRequiredCameraPermission = arrayOf(Manifest.permission.CAMERA)

    init {
        val v = apiVersion
        isAtLeastM = v >= 23//android.os.Build.VERSION_CODES.M
    }

    /**
     * Check if the app has the specified permission. If it does not, the app needs to use
     * [#requestPermission][android.app.Activity]. Note that if it
     * returns true, it cannot return false in the same process as the OS kills the process when
     * any permission is revoked.
     *
     * @param permission A permission from [Manifest.permission]
     */
    @TargetApi(23)
    fun hasPermission(context: Context?, permission: String): Boolean {

        if (isAtLeastM && context != null) {
            val permissionCheck = context.checkSelfPermission(permission)
            return permissionCheck == PackageManager.PERMISSION_GRANTED
        } else {
            return true
        }
    }

    /**
     * Does the app have all the specified permissions
     */
    fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (!hasPermission(context, permission)) {
                return false
            }
        }
        return true
    }


    fun hasLocationPermission(context: Context): Boolean {
        return hasPermissions(context, sRequiredLocationPermission)
    }

    fun hasStoragePermission(context: Context): Boolean {
        return hasPermissions(context, sRequiredStoragePermission)
    }

    fun hasCameraPermission(context: Context): Boolean {
        return hasPermissions(context, sRequiredCameraPermission)
    }
}
