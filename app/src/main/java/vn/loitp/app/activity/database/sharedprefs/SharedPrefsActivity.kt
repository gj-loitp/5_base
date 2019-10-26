package vn.loitp.app.activity.database.sharedprefs

import android.os.Bundle
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_shared_prefs.*
import loitp.basemaster.R
import vn.loitp.app.activity.pattern.mvp.User

class SharedPrefsActivity : BaseFontActivity() {

    private val KEY_STRING = "KEY_STRING"
    private val KEY_STRING_WITH_DEFAULT_VALUE = "KEY_STRING_WITH_DEFAULT_VALUE"
    private val KEY_BOOLEAN = "KEY_BOOLEAN"
    private val KEY_FLOAT = "KEY_FLOAT"
    private val KEY_INT = "KEY_INT"
    private val KEY_LONG = "KEY_LONG"
    private val KEY_OBJECT = "KEY_OBJECT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btPutString.setOnClickListener {
            SharedPrefs.instance.putString(KEY_STRING, "This is a string!!! " + System.currentTimeMillis())
        }
        btGetString.setOnClickListener {
            val value = SharedPrefs.instance.getString(KEY_STRING)
            showLong(value)
        }

        btPutStringWithDefaultValue.setOnClickListener {
            SharedPrefs.instance.putString(KEY_STRING_WITH_DEFAULT_VALUE, "This is a string!!! " + System.currentTimeMillis())
        }
        btGetStringWithDefaultValue.setOnClickListener {
            val value = SharedPrefs.instance.getString(KEY_STRING_WITH_DEFAULT_VALUE, "Default value is Loitppp ahihi")
            showLong(value)
        }

        btPutBoolean.setOnClickListener {
            SharedPrefs.instance.putBoolean(KEY_BOOLEAN, true)
        }
        btGetBoolean.setOnClickListener {
            val value = SharedPrefs.instance.getBoolean(KEY_BOOLEAN)
            showLong("Value: $value")
        }

        btPutFloat.setOnClickListener {
            SharedPrefs.instance.putFloat(KEY_FLOAT, System.currentTimeMillis().toFloat())
        }
        btGetFloat.setOnClickListener {
            val value = SharedPrefs.instance.getFloat(KEY_FLOAT)
            showLong("Value: $value")
        }

        btPutInt.setOnClickListener {
            SharedPrefs.instance.putInt(KEY_INT, System.currentTimeMillis().toInt())
        }
        btGetInt.setOnClickListener {
            val value = SharedPrefs.instance.getInt(KEY_INT)
            showLong("Value: $value")
        }

        btPutLong.setOnClickListener {
            SharedPrefs.instance.putLong(KEY_LONG, System.currentTimeMillis())
        }
        btGetLong.setOnClickListener {
            val value = SharedPrefs.instance.getLong(KEY_LONG)
            showLong("Value: $value")
        }

        btPutObject.setOnClickListener {
            val user = User()
            user.email = "Email ${System.currentTimeMillis()}"
            user.fullName = "Name ${System.currentTimeMillis()}"
            SharedPrefs.instance.putObject(KEY_OBJECT, user)
        }
        btGetObject.setOnClickListener {
            val value = SharedPrefs.instance.getObject(KEY_OBJECT, User::class.java)
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
