package vn.loitp.up.a.func.hashmap

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AFuncHashmapBinding

@LogTag("HashMapActivity")
@IsFullScreen(false)
class HashMapActivity : BaseActivityFont(), View.OnClickListener {
    private lateinit var binding: AFuncHashmapBinding

    private val map: MutableMap<String, String> = HashMap()
    private var autoKey = 0

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFuncHashmapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = HashMapActivity::class.java.simpleName
        }
        binding.btAdd.setOnClickListener(this)
        binding.btGetKey0.setOnClickListener(this)
        binding.btRemoveKey0.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btAdd -> {
                map[autoKey.toString()] = "Value $autoKey"
                printMap()
                autoKey++
            }
            binding.btGetKey0 -> {
                val value = map[0.toString()]
                showShortInformation("Click value= $value")
            }
            binding.btRemoveKey0 -> {
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
        binding.textView.text = s.ifEmpty { "No data" }
    }
}
