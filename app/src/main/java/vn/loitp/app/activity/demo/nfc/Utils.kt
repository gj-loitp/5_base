package vn.loitp.app.activity.demo.nfc

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.provider.Settings
import vn.loitp.app.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.experimental.and

internal object Utils {
    private val hexArray = "0123456789ABCDEF".toCharArray()

    fun bytesToHex(bytes: ByteArray?): String {
        if (bytes == null) return ""
        val hexChars = CharArray(bytes.size * 2)
        for (j in bytes.indices) {
            val v: Int = (bytes[j] and 0xFF.toByte()).toInt()
            hexChars[j * 2] = hexArray[v ushr 4]
            hexChars[j * 2 + 1] = hexArray[v and 0x0F]
        }
        return "0x" + String(hexChars)
    }

    fun bytesToHexAndString(bytes: ByteArray?): String? {
        return if (bytes == null) null else bytesToHex(bytes) + " (" + String(bytes) + ")"
    }

    fun now(): String {
        val tz = TimeZone.getTimeZone("UTC")
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        df.timeZone = tz
        return df.format(Date())
    }

    fun showNfcSettingsDialog(app: Activity) {
        AlertDialog.Builder(app)
                .setTitle("NFC is disabled")
                .setMessage("You must enable NFC to use this app.")
                .setPositiveButton("Yes") { dialog, which -> app.startActivity(Intent(Settings.ACTION_NFC_SETTINGS)) }
                .setNegativeButton("No") { dialog, which -> app.finish() }
                .setIcon(R.drawable.ic_launcher)
                .show()
    }
}