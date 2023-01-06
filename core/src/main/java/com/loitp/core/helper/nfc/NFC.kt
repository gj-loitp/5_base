package com.loitp.core.helper.nfc

import kotlin.experimental.and

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
private val hexArray = "0123456789ABCDEF".toCharArray()

fun ByteArray?.bytesToHex(): String? {
    if (this == null) return null
    val hexChars = CharArray(this.size * 2)
    for (j in this.indices) {
        val v: Int = this[j].toInt() and 0xFF
        hexChars[j * 2] = hexArray[v ushr 4]
        hexChars[j * 2 + 1] = hexArray[v and 0x0F]
    }
    return "0x" + String(hexChars)
}

fun ByteArray?.buildMACAddressString(): String? {
    if (this == null) return null
    val buffer = CharArray(this.size * 3)
    val intToHex = charArrayOf(
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    )
    var destIndex = 0
    var byteValue: Byte
    for (i in this.indices) {
        // pull current byte value
        byteValue = (this[i] and 0xff.toByte())
        // convert high nibble to hex char and store into char array..
        buffer[destIndex++] = intToHex[byteValue.toInt() and 0xf shr 4]
        // Convert low nibble to hex char and store into char array..
        buffer[destIndex++] = intToHex[(byteValue and 0xf).toInt()]
        // Inject spacer
        if (i < this.size - 1) buffer[destIndex++] = ':'
    }
    return String(chars = buffer, offset = 0, length = destIndex)
}

fun ByteArray?.bytesToHexAndString(): String? {
    if (this == null) return null
    return this.bytesToHex() + " (" + String(this) + ")"
}
