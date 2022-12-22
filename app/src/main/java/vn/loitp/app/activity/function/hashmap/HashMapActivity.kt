package vn.loitp.app.activity.function.hashmap

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_func_hashmap.*
import vn.loitp.R

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

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = HashMapActivity::class.java.simpleName
        }
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
        textView.text = s.ifEmpty { "No data" }
    }
}
