package vn.loitp.app.activity.function.hashmap

import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_func_hashmap.*
import vn.loitp.app.R
import java.util.*

@LayoutId(R.layout.activity_func_hashmap)
class HashMapActivity : BaseFontActivity(), View.OnClickListener {
    private val map: MutableMap<String, String> = HashMap()
    private var autoKey = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btAdd.setOnClickListener(this)
        btGetKey0.setOnClickListener(this)
        btRemoveKey0.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
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
                showShort("Click value= $value")
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