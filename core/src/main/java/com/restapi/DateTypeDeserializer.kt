package com.restapi

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateTypeDeserializer : JsonDeserializer<Date> {

    companion object {
        private val TAG = DateTypeDeserializer::class.java.name
        private val DATE_FORMATS = arrayOf(
                "dd-MM-yyyy hh:mm",
                "dd/MM/yyyy",
                "yyyy-MM-dd"
        )
    }

    @Throws(JsonParseException::class)
    override fun deserialize(jsonElement: JsonElement, typeOF: Type, context: JsonDeserializationContext): Date? {
        for (format in DATE_FORMATS) {
            try {
                return SimpleDateFormat(
                        format, Locale.getDefault()
                ).parse(jsonElement.asString)
            } catch (e: ParseException) {
                Log.e(TAG, "Error when deserialize date")
            }
        }
        throw JsonParseException("""Can't parse date: "${jsonElement.asString}". Supported formats:${DATE_FORMATS.contentToString()}""".trimIndent())
    }
}
