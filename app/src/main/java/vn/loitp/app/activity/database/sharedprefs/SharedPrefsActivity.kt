package vn.loitp.app.activity.database.sharedprefs

import android.os.Bundle
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LSharedPrefsUtil
import kotlinx.android.synthetic.main.activity_shared_prefs.*
import vn.loitp.app.R
import vn.loitp.app.activity.pattern.mvp.User

@LayoutId(R.layout.activity_shared_prefs)
class SharedPrefsActivity : BaseFontActivity() {

    companion object {
        const val KEY_STRING = "KEY_STRING"
        const val KEY_STRING_WITH_DEFAULT_VALUE = "KEY_STRING_WITH_DEFAULT_VALUE"
        const val KEY_BOOLEAN = "KEY_BOOLEAN"
        const val KEY_FLOAT = "KEY_FLOAT"
        const val KEY_INT = "KEY_INT"
        const val KEY_LONG = "KEY_LONG"
        const val KEY_OBJECT = "KEY_OBJECT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btPutString.setOnClickListener {
            LSharedPrefsUtil.instance.putString(KEY_STRING, "This is a string!!! " + System.currentTimeMillis())
        }
        btGetString.setOnClickListener {
            val value = LSharedPrefsUtil.instance.getString(KEY_STRING)
            showLong(value)
        }

        btPutStringWithDefaultValue.setOnClickListener {
            LSharedPrefsUtil.instance.putString(KEY_STRING_WITH_DEFAULT_VALUE, "This is a string!!! " + System.currentTimeMillis())
        }
        btGetStringWithDefaultValue.setOnClickListener {
            val value = LSharedPrefsUtil.instance.getString(KEY_STRING_WITH_DEFAULT_VALUE, "Default value")
            showLong(value)
        }

        btPutBoolean.setOnClickListener {
            LSharedPrefsUtil.instance.putBoolean(KEY_BOOLEAN, true)
        }
        btGetBoolean.setOnClickListener {
            val value = LSharedPrefsUtil.instance.getBoolean(KEY_BOOLEAN)
            showLong("Value: $value")
        }

        btPutFloat.setOnClickListener {
            LSharedPrefsUtil.instance.putFloat(KEY_FLOAT, System.currentTimeMillis().toFloat())
        }
        btGetFloat.setOnClickListener {
            val value = LSharedPrefsUtil.instance.getFloat(KEY_FLOAT)
            showLong("Value: $value")
        }

        btPutInt.setOnClickListener {
            LSharedPrefsUtil.instance.putInt(KEY_INT, System.currentTimeMillis().toInt())
        }
        btGetInt.setOnClickListener {
            val value = LSharedPrefsUtil.instance.getInt(KEY_INT)
            showLong("Value: $value")
        }

        btPutLong.setOnClickListener {
            LSharedPrefsUtil.instance.putLong(KEY_LONG, System.currentTimeMillis())
        }
        btGetLong.setOnClickListener {
            val value = LSharedPrefsUtil.instance.getLong(KEY_LONG)
            showLong("Value: $value")
        }

        btPutObject.setOnClickListener {
            val user = User()
            user.email = "Email ${System.currentTimeMillis()}"
            user.fullName = "Name ${System.currentTimeMillis()}"
            LSharedPrefsUtil.instance.putObject(KEY_OBJECT, user)
        }
        btGetObject.setOnClickListener {
            val value = LSharedPrefsUtil.instance.getObject(KEY_OBJECT, User::class.java)
            showLong("Value: $value")
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}
