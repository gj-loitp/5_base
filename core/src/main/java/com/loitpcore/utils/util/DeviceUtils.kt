package com.loitpcore.utils.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import java.io.File
import java.net.NetworkInterface
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class DeviceUtils private constructor() {

    companion object {

        val isDeviceRooted: Boolean
            get() {
                val su = "su"
                val locations = arrayOf(
                    "/system/bin/",
                    "/system/xbin/",
                    "/sbin/",
                    "/system/sd/xbin/",
                    "/system/bin/failsafe/",
                    "/data/local/xbin/",
                    "/data/local/bin/",
                    "/data/local/"
                )
                for (location in locations) {
                    if (File(location + su).exists()) {
                        return true
                    }
                }
                return false
            }

        val sDKVersion: Int
            get() = Build.VERSION.SDK_INT

        @get:SuppressLint("HardwareIds")
        val androidID: String
            get() = Settings.Secure.getString(
                Utils.getContext()?.contentResolver,
                Settings.Secure.ANDROID_ID
            )

        val macAddress: String
            get() {
                var macAddress = macAddressByWifiInfo
                if ("02:00:00:00:00:00" != macAddress) {
                    return macAddress
                }
                macAddress = macAddressByNetworkInterface
                if ("02:00:00:00:00:00" != macAddress) {
                    return macAddress
                }
                macAddress = macAddressByFile
                return if ("02:00:00:00:00:00" != macAddress) {
                    macAddress
                } else "please open wifi"
            }

        @get:SuppressLint("HardwareIds")
        private val macAddressByWifiInfo: String
            get() {
                try {
                    @SuppressLint("WifiManagerLeak")
                    val wifi =
                        Utils.getContext()?.getSystemService(Context.WIFI_SERVICE) as WifiManager?
                    val info = wifi?.connectionInfo
                    if (info != null) return info.macAddress
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return "02:00:00:00:00:00"
            }

        private val macAddressByNetworkInterface: String
            get() {
                try {
                    val nis: List<NetworkInterface> =
                        Collections.list(NetworkInterface.getNetworkInterfaces())
                    for (ni in nis) {
                        if (!ni.name.equals("wlan0", ignoreCase = true)) continue
                        val macBytes = ni.hardwareAddress
                        if (macBytes != null && macBytes.isNotEmpty()) {
                            val res1 = StringBuilder()
                            for (b in macBytes) {
                                res1.append(String.format("%02x:", b))
                            }
                            return res1.deleteCharAt(res1.length - 1).toString()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return "02:00:00:00:00:00"
            }

        private val macAddressByFile: String
            get() {
                var result = ShellUtils.execCmd("getprop wifi.interface", false)
                if (result.result == 0) {
                    val name = result.successMsg
                    if (name != null) {
                        result = ShellUtils.execCmd("cat /sys/class/net/$name/address", false)
                        if (result.result == 0) {
                            if (result.successMsg != null) {
                                return result.successMsg
                            }
                        }
                    }
                }
                return "02:00:00:00:00:00"
            }

        val manufacturer: String
            get() = Build.MANUFACTURER

        val model: String
            get() {
                var model = Build.MODEL
                model = model?.trim { it <= ' ' }?.replace("\\s*".toRegex(), "") ?: ""
                return model
            }

        @Suppress("unused")
        fun shutdown() {
            ShellUtils.execCmd("reboot -p", true)
            val intent = Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN")
            intent.putExtra("android.intent.extra.KEY_CONFIRM", false)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            Utils.getContext()?.startActivity(intent)
        }

        @Suppress("unused")
        fun reboot() {
            ShellUtils.execCmd("reboot", true)
            val intent = Intent(Intent.ACTION_REBOOT)
            intent.putExtra("nowait", 1)
            intent.putExtra("interval", 1)
            intent.putExtra("window", 0)
            Utils.getContext()?.sendBroadcast(intent)
        }

        @Suppress("unused")
        fun reboot(reason: String?) {
            val mPowerManager =
                Utils.getContext()?.getSystemService(Context.POWER_SERVICE) as PowerManager?
            try {
                mPowerManager?.reboot(reason)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @Suppress("unused")
        fun reboot2Recovery() {
            ShellUtils.execCmd("reboot recovery", true)
        }

        @Suppress("unused")
        fun reboot2Bootloader() {
            ShellUtils.execCmd("reboot bootloader", true)
        }
    }
}
