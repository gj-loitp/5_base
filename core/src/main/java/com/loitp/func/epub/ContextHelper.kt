package com.loitp.func.epub

import com.loitp.func.epub.exception.ReadingException
import java.io.* // ktlint-disable no-wildcard-imports
import java.net.URLDecoder
import java.net.URLEncoder

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
internal object ContextHelper {

    @JvmStatic
    @Throws(ReadingException::class)
    fun encodeToUtf8(stringToEncode: String): String {
        var encodedString: String?
        try {
            encodedString =
                URLDecoder.decode(stringToEncode, "UTF-8") // Charset.forName("UTF-8").name()
            encodedString = URLEncoder.encode(encodedString, "UTF-8")
                .replace("+", "%20") // Charset.forName("UTF-8").name()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            throw ReadingException("UnsupportedEncoding while encoding, " + stringToEncode + ", : " + e.message)
        }
        return encodedString
    }

    @JvmStatic
    @Throws(IOException::class)
    fun convertIsToByteArray(inputStream: InputStream): ByteArray {
        val buffer = ByteArray(size = 8192)
        var bytesRead: Int
        val output = ByteArrayOutputStream()
        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            output.write(buffer, 0, bytesRead)
        }
        return output.toByteArray()
    }

    @JvmStatic
    fun getTextAfterCharacter(
        text: String,
        character: Char
    ): String {
        val lastCharIndex = text.lastIndexOf(character)
        return text.substring(startIndex = lastCharIndex + 1)
    }

    @JvmStatic
    fun getTagsRegex(
        tagName: String?,
        isIncludingEmptyTags: Boolean
    ): String { // <style.*?</style> or <img.*?/>|<img.*?</img>
        return if (isIncludingEmptyTags) String.format("<%1\$s.*?/>|<%1\$s.*?</%1\$s>", tagName)
        else String.format("<%1\$s.*?</%1\$s>", tagName)
    }

    @JvmStatic
    @Throws(IOException::class)
    fun copy(
        input: InputStream,
        output: OutputStream
    ) {
        val bytes = ByteArray(size = 4096 * 1024)
        var bytesRead: Int
        while (input.read(bytes).also { bytesRead = it } != -1) {
            output.write(bytes, 0, bytesRead)
        }
    }
}
