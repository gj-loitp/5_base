package vn.loitp.app.activity.database.room.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import vn.loitp.app.activity.database.room.model.Area
import java.lang.reflect.Type

class AreaConverter {

    @TypeConverter
    fun fromAreaValuesList(optionValues: List<Area>?): String? {
        if (optionValues == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Area>?>() {}.type
        return gson.toJson(optionValues, type)
    }

    @TypeConverter
    fun toAreaValuesList(optionValuesString: String?): List<Area>? {
        if (optionValuesString == null) {
            return null
        }

        val gson = Gson()
        val type: Type = object : TypeToken<List<Area>?>() {}.type
        return gson.fromJson<List<Area>>(optionValuesString, type)
    }
}
