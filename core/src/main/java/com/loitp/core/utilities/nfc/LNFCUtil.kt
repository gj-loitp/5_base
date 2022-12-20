package com.loitp.core.utilities.nfc

import kotlin.experimental.and

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LNFCUtil {
    companion object {
        private val hexArray = "0123456789ABCDEF".toCharArray()

        fun bytesToHex(bytes: ByteArray?): String? {
            if (bytes == null) return null
            val hexChars = CharArray(bytes.size * 2)
            for (j in bytes.indices) {
                val v: Int = bytes[j].toInt() and 0xFF
                hexChars[j * 2] = hexArray[v ushr 4]
                hexChars[j * 2 + 1] = hexArray[v and 0x0F]
            }
            return "0x" + String(hexChars)
        }

        fun buildMACAddressString(macAddress: ByteArray?): String? {
            if (macAddress == null) return null
            val buffer = CharArray(macAddress.size * 3)
            val intToHex = charArrayOf(
                '0',
                '1',
                '2',
                '3',
                '4',
                '5',
                '6',
                '7',
                '8',
                '9',
                'a',
                'b',
                'c',
                'd',
                'e',
                'f'
            )
            var destIndex = 0
            var byteValue: Byte
            for (i in macAddress.indices) {
                // pull current byte value
                byteValue = (macAddress[i] and 0xff.toByte())
                // convert high nibble to hex char and store into char array..
                buffer[destIndex++] = intToHex[byteValue.toInt() and 0xf shr 4]
                // Convert low nibble to hex char and store into char array..
                buffer[destIndex++] = intToHex[(byteValue and 0xf).toInt()]
                // Inject spacer
                if (i < macAddress.size - 1) buffer[destIndex++] = ':'
            }
            return String(chars = buffer, offset = 0, length = destIndex)
        }

        fun bytesToHexAndString(bytes: ByteArray?): String? {
            return if (bytes == null) null else bytesToHex(bytes) + " (" + String(bytes) + ")"
        }
    }
}
