package vn.loitp.app.a.db.sharedPrefs

import android.os.Bundle
import com.google.gson.reflect.TypeToken
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseApplication
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LConvertUtil
import com.loitp.core.utilities.LSharedPrefsUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_shared_prefs.*
import vn.loitp.R
import vn.loitp.app.a.pattern.mvp.User

@LogTag("SharedPrefsActivity")
@IsFullScreen(false)
class SharedPrefsActivity : BaseFontActivity() {

    companion object {
        const val KEY_STRING = "KEY_STRING"
        const val KEY_STRING_WITH_DEFAULT_VALUE = "KEY_STRING_WITH_DEFAULT_VALUE"
        const val KEY_BOOLEAN = "KEY_BOOLEAN"
        const val KEY_FLOAT = "KEY_FLOAT"
        const val KEY_INT = "KEY_INT"
        const val KEY_LONG = "KEY_LONG"
        const val KEY_NUMBER = "KEY_NUMBER"
        const val KEY_OBJECT = "KEY_OBJECT"
        const val KEY_LIST_OBJECT = "KEY_LIST_OBJECT"
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_shared_prefs
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
            this.tvTitle?.text = SharedPrefsActivity::class.java.simpleName
        }

        btPutString.setSafeOnClickListener {
            LSharedPrefsUtil.instance.putString(
                KEY_STRING,
                "This is a string!!! " + System.currentTimeMillis()
            )
        }
        btGetString.setSafeOnClickListener {
            val value = LSharedPrefsUtil.instance.getString(KEY_STRING)
            showLongInformation(value)
        }

        btPutStringWithDefaultValue.setSafeOnClickListener {
            LSharedPrefsUtil.instance.putString(
                KEY_STRING_WITH_DEFAULT_VALUE,
                "This is a string!!! " + System.currentTimeMillis()
            )
        }
        btGetStringWithDefaultValue.setSafeOnClickListener {
            val value =
                LSharedPrefsUtil.instance.getString(KEY_STRING_WITH_DEFAULT_VALUE, "Default value")
            showLongInformation(value)
        }

        btPutBoolean.setSafeOnClickListener {
            LSharedPrefsUtil.instance.putBoolean(KEY_BOOLEAN, true)
        }
        btGetBoolean.setSafeOnClickListener {
            val value = LSharedPrefsUtil.instance.getBoolean(KEY_BOOLEAN)
            showLongInformation("Value: $value")
        }

        btPutFloat.setSafeOnClickListener {
            LSharedPrefsUtil.instance.putFloat(KEY_FLOAT, System.currentTimeMillis().toFloat())
        }
        btGetFloat.setSafeOnClickListener {
            val value = LSharedPrefsUtil.instance.getFloat(KEY_FLOAT)
            showLongInformation("Value: $value")
        }

        btPutInt.setSafeOnClickListener {
            LSharedPrefsUtil.instance.putInt(KEY_INT, System.currentTimeMillis().toInt())
        }
        btGetInt.setSafeOnClickListener {
            val value = LSharedPrefsUtil.instance.getInt(KEY_INT)
            showLongInformation("Value: $value")
        }

        btPutLong.setSafeOnClickListener {
            LSharedPrefsUtil.instance.putLong(KEY_LONG, System.currentTimeMillis())
        }
        btGetLong.setSafeOnClickListener {
            val value = LSharedPrefsUtil.instance.getLong(KEY_LONG)
            showLongInformation("Value: $value")
        }

        btPutNumber.setSafeOnClickListener {
            LSharedPrefsUtil.instance.putString(KEY_NUMBER, 123456.789.toString())
        }
        btGetNumber.setSafeOnClickListener {
            try {
                val value = LSharedPrefsUtil.instance.getString(KEY_NUMBER)
                showLongInformation("Value: $value -> " + LConvertUtil.convertToPrice(value.toBigDecimalOrNull()))
            } catch (e: Exception) {
                showShortError(e.toString())
            }
        }

        btPutObject.setSafeOnClickListener {
            val user = User()
            user.email = "Email ${System.currentTimeMillis()}"
            user.fullName = "Name ${System.currentTimeMillis()}"
            LSharedPrefsUtil.instance.putObject(KEY_OBJECT, user)
        }
        btGetObject.setSafeOnClickListener {
            val value = LSharedPrefsUtil.instance.getObject(KEY_OBJECT, User::class.java)
            showLongInformation("Value: $value")
        }

        btPutListObject.setSafeOnClickListener {
            val list = ArrayList<User>()
            for (i in 0..10) {
                val user = User()
                user.email = "Email ${System.currentTimeMillis()}"
                user.fullName = "Name ${System.currentTimeMillis()}"
                list.add(user)
            }
            LSharedPrefsUtil.instance.putObjectList(KEY_LIST_OBJECT, list)
        }
        btGetListObject.setSafeOnClickListener {
            val type = object : TypeToken<List<User>>() {
            }.type
            val value: ArrayList<User> =
                LSharedPrefsUtil.instance.getObjectList(KEY_LIST_OBJECT, type)
            logD("list size: " + value.size)
            for (i in value.indices) {
                logD("$i -> ${value[i].fullName}")
            }
            showLongInformation("Value: " + BaseApplication.gson.toJson(value))
        }
    }
}
