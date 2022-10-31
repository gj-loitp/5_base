package com.loitpcore.core.utilities.nfc;

import kotlin.Suppress;

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
public class LNFCUtilOriginal {
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        if (bytes == null) return null;

        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return "0x" + new String(hexChars);
    }

    @Suppress(names = "unused")
    public static String buildMACAddressString(byte[] macAddress) {
        char[] buffer = new char[macAddress.length * 3];
        char[] intToHex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        int destIndex = 0;
        byte byteValue;
        for (int i = 0; i < macAddress.length; i++) {
            // pull current byte value
            byteValue = (byte) (macAddress[i] & 0xff);
            // convert high nibble to hex char and store into char array..
            buffer[destIndex++] = intToHex[(byteValue & 0xf0) >> 4];
            // Convert low nibble to hex char and store into char array..
            buffer[destIndex++] = intToHex[byteValue & 0xf];
            // Inject spacer
            if (i < macAddress.length - 1)
                buffer[destIndex++] = ':';
        }
        return String.valueOf(buffer, 0, destIndex);
    }

    @Suppress(names = "unused")
    public static String bytesToHexAndString(byte[] bytes) {
        if (bytes == null) return null;

        return bytesToHex(bytes) + " (" + new String(bytes) + ")";
    }
}
