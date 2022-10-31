package com.loitpcore.utils.util

import android.os.Environment
import android.os.StatFs
import com.loitpcore.utils.util.CloseUtils.Companion.closeIO
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class SDCardUtils {

    class SDCardInfo {
        var isExist = false
        var totalBlocks: Long = 0
        var freeBlocks: Long = 0
        var availableBlocks: Long = 0
        var blockByteSize: Long = 0
        var totalBytes: Long = 0
        var freeBytes: Long = 0
        var availableBytes: Long = 0

        override fun toString(): String {
            return """
                isExist=$isExist
                totalBlocks=$totalBlocks
                freeBlocks=$freeBlocks
                availableBlocks=$availableBlocks
                blockByteSize=$blockByteSize
                totalBytes=$totalBytes
                freeBytes=$freeBytes
                availableBytes=$availableBytes
            """.trimIndent()
        }
    }

    companion object {
        fun isSDCardEnable(): Boolean {
            return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
        }

        private fun sDCardPath(): String? {
            if (!isSDCardEnable()) {
                return null
            }
            val cmd = "cat /proc/mounts"
            val run = Runtime.getRuntime()
            var bufferedReader: BufferedReader? = null
            try {
                val p = run.exec(cmd)
                bufferedReader =
                    BufferedReader(InputStreamReader(BufferedInputStream(p.inputStream)))
                var lineStr: String
                while (bufferedReader.readLine().also { lineStr = it } != null) {
                    if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
                        val strArray = lineStr.split(" ".toRegex()).toTypedArray()
                        if (strArray.size >= 5) {
                            return strArray[1].replace("/.android_secure", "") + File.separator
                        }
                    }
                    if (p.waitFor() != 0 && p.exitValue() == 1) {
                        break
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                closeIO(bufferedReader)
            }
            return Environment.getExternalStorageDirectory().path + File.separator
        }

        @Suppress("unused")
        fun dataPath(): String? {
            return if (!isSDCardEnable()) {
                null
            } else {
                Environment.getExternalStorageDirectory().path + File.separator + "data" + File.separator
            }
        }

        @Suppress("unused")
        fun freeSpace(): String? {
            if (!isSDCardEnable()) {
                return null
            }
            val stat = StatFs(sDCardPath())
            val availableBlocks: Long = stat.availableBlocksLong
            val blockSize: Long = stat.blockSizeLong
            return ConvertUtils.byte2FitMemorySize(availableBlocks * blockSize)
        }

        @Suppress("unused")
        fun sDCardInfo(): String? {
            if (!isSDCardEnable()) {
                return null
            }
            val sd = SDCardInfo()
            sd.isExist = true
            val sf = StatFs(Environment.getExternalStorageDirectory().path)
            sd.totalBlocks = sf.blockCountLong
            sd.blockByteSize = sf.blockSizeLong
            sd.availableBlocks = sf.availableBlocksLong
            sd.availableBytes = sf.availableBytes
            sd.freeBlocks = sf.freeBlocksLong
            sd.freeBytes = sf.freeBytes
            sd.totalBytes = sf.totalBytes
            return sd.toString()
        }
    }
}
