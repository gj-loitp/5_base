package vn.loitp.app.activity.function.hashmap

import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_func_hashmap.*
import vn.loitp.app.R
import java.util.* // ktlint-disable no-wildcard-imports

@LogTag("HashMapActivity")
@IsFullScreen(false)
class HashMapActivity : BaseFontActivity(), View.OnClickListener {
    private val map: MutableMap<String, String> = HashMap()
    private var autoKey = 0

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_hashmap
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btAdd.setOnClickListener(this)
        btGetKey0.setOnClickListener(this)
        btRemoveKey0.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btAdd -> {
                map[autoKey.toString()] = "Value $autoKey"
                printMap()
                autoKey++
            }
            btGetKey0 -> {
                val value = map[0.toString()]
                showShortInformation("Click value= $value")
            }
            btRemoveKey0 -> {
                map.remove(0.toString())
                printMap()
            }
        }
    }

    private fun printMap() {
        var s = ""
        for ((key, value) in map) {
            s += "$key -> $value"
        }
        textView.text = if (s.isEmpty()) "No data" else s
    }
}
