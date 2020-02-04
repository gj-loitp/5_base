package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.utils

import android.content.Context
import android.graphics.Color
import loitp.basemaster.R
import org.json.JSONException
import org.json.JSONObject
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.ColorItem
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

object DemoData {

    fun loadDemoColorItems(context: Context): List<ColorItem> {
        val items = ArrayList<ColorItem>()

        try {
            val obj = JSONObject(loadJSONFromAsset(context, "recyclertablayoutcolors.json"))
            val keys = obj.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                val value = obj.getJSONObject(key)
                val colorItem = ColorItem()
                colorItem.name = value.getString("name")
                colorItem.hex = value.getString("hex")
                val rgb = value.getJSONArray("rgb")
                colorItem.color = Color.rgb(rgb.getInt(0), rgb.getInt(1), rgb.getInt(2))
                items.add(colorItem)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return items
    }

    @Throws(IOException::class)
    fun loadJSONFromAsset(context: Context, filename: String): String {
        val iS = context.assets.open(filename)
        val size = iS.available()
        val buffer = ByteArray(size)
        iS.read(buffer)
        iS.close()
        return String(buffer, Charset.forName("UTF-8"))
    }

    fun loadImageResourceList(): List<Int> {
        return ArrayList(Arrays.asList(
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera,
                R.drawable.picker_icon_camera))
    }
}
