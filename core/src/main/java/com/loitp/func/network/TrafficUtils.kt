package com.loitp.func.network

import android.content.Context
import android.net.TrafficStats
import android.net.wifi.WifiManager
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class TrafficUtils {

    companion object {
        private const val GB: Long = 1000000000
        private const val MB: Long = 1000000
        private const val KB: Long = 1000

        fun getNetworkSpeed(): String {

            val downloadSpeedOutput: String
            val units: String
            val mBytesPrevious = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes()

            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            val mBytesCurrent = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes()
            val mNetworkSpeed = mBytesCurrent - mBytesPrevious
            val mDownloadSpeedWithDecimals: Float

            when {
                mNetworkSpeed >= GB -> {
                    mDownloadSpeedWithDecimals = mNetworkSpeed.toFloat() / GB.toFloat()
                    units = " GB"
                }
                mNetworkSpeed >= MB -> {
                    mDownloadSpeedWithDecimals = mNetworkSpeed.toFloat() / MB.toFloat()
                    units = " MB"

                }
                else -> {
                    mDownloadSpeedWithDecimals = mNetworkSpeed.toFloat() / KB.toFloat()
                    units = " KB"
                }
            }

            downloadSpeedOutput = if (units != " KB" && mDownloadSpeedWithDecimals < 100) {
                String.format(Locale.US, "%.1f", mDownloadSpeedWithDecimals)
            } else {
                mDownloadSpeedWithDecimals.toInt().toString()
            }

            return (downloadSpeedOutput + units)

        }

        @Suppress("unused")
        fun convertToBytes(
            value: Float,
            unit: String
        ): Long {
            return when (unit) {
                "KB" -> {
                    (value.toLong()) * KB
                }
                "MB" -> {
                    (value.toLong()) * MB
                }
                "GB" -> {
                    (value.toLong()) * GB
                }
                else -> 0
            }
        }

        fun isWifiConnected(context: Context): Boolean {
            val wifiMgr =
                context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?

            return if (wifiMgr?.isWifiEnabled == true) { // Wi-Fi adapter is ON
                val wifiInfo = wifiMgr.connectionInfo
                wifiInfo.networkId != -1
            } else {
                false // Wi-Fi adapter is OFF
            }
        }

        @Suppress("unused")
        fun getMetricData(bytes: Long): String {
            val dataWithDecimals: Float
            val units: String
            when {
                bytes >= GB -> {
                    dataWithDecimals = bytes.toFloat() / GB.toFloat()
                    units = " GB"
                }
                bytes >= MB -> {
                    dataWithDecimals = bytes.toFloat() / MB.toFloat()
                    units = " MB"

                }
                else -> {
                    dataWithDecimals = bytes.toFloat() / KB.toFloat()
                    units = " KB"
                }
            }

            val output = if (units != " KB" && dataWithDecimals < 100) {
                String.format(Locale.US, "%.1f", dataWithDecimals)
            } else {
                dataWithDecimals.toInt().toString()
            }

            return output + units
        }

        @Suppress("unused")
        fun getMonthlyWifiUsage(listDailyConsumption: List<DailyConsumption>): Long {
            var total = 0L
            for (dailyConsumption in listDailyConsumption) {
                total += dailyConsumption.wifi
            }

            return total
        }

        @Suppress("unused")
        fun getMonthlyMobileUsage(listDailyConsumption: List<DailyConsumption>): Long {
            var total = 0L
            for (dailyConsumption in listDailyConsumption) {
                total += dailyConsumption.mobile
            }

            return total
        }

        @Suppress("unused")
        fun getMonthlyTotalUsage(listDailyConsumption: List<DailyConsumption>): Long {
            var total = 0L
            for (dailyConsumption in listDailyConsumption) {
                total += dailyConsumption.total
            }

            return total
        }

    }
}
