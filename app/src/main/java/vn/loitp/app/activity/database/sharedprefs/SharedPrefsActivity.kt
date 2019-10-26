package vn.loitp.app.activity.database.sharedprefs

import android.os.Bundle
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_shared_prefs.*
import loitp.basemaster.R

class SharedPrefsActivity : BaseFontActivity() {

    private val KEY_STRING = "KEY_STRING"
    private val KEY_BOOLEAN = "KEY_BOOLEAN"
    private val KEY_FLOAT = "KEY_FLOAT"
    private val KEY_INT = "KEY_INT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btPutString.setOnClickListener {
            SharedPrefs.instance.put(KEY_STRING, "This is a string!!! " + System.currentTimeMillis())
        }
        btGetString.setOnClickListener {
            val value = SharedPrefs.instance.get(KEY_STRING, String::class.java)
            showLong(value)
        }

        btPutBoolean.setOnClickListener {
            SharedPrefs.instance.put(KEY_BOOLEAN, true)
        }
        btGetBoolean.setOnClickListener {
            val value = SharedPrefs.instance.get(KEY_BOOLEAN, Boolean::class.java)
            showLong("Value: $value")
        }

        btPutFloat.setOnClickListener {
            SharedPrefs.instance.put(KEY_FLOAT, System.currentTimeMillis().toFloat())
        }
        btGetFloat.setOnClickListener {
            val value = SharedPrefs.instance.get(KEY_FLOAT, Float::class.java)
            showLong("Value: $value")
        }

        btPutInt.setOnClickListener {
            SharedPrefs.instance.put(KEY_INT, System.currentTimeMillis().toInt())
        }
        btGetInt.setOnClickListener {
            val value = SharedPrefs.instance.get(KEY_INT, Int::class.java)
            showLong("Value: $value")
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_shared_prefs
    }
}
