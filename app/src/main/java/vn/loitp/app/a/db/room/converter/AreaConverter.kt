package vn.loitp.app.a.db.room.converter

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.loitp.core.base.BaseApplication
import vn.loitp.app.a.db.room.md.Area
import java.lang.reflect.Type

class AreaConverter {

    @TypeConverter
    fun fromAreaValuesList(optionValues: List<Area>?): String? {
        if (optionValues == null) {
            return null
        }
        val type: Type = object : TypeToken<List<Area>?>() {}.type
        return BaseApplication.gson.toJson(optionValues, type)
    }

    @TypeConverter
    fun toAreaValuesList(optionValuesString: String?): List<Area>? {
        if (optionValuesString == null) {
            return null
        }

        val type: Type = object : TypeToken<List<Area>?>() {}.type
        return BaseApplication.gson.fromJson<List<Area>>(optionValuesString, type)
    }
}
