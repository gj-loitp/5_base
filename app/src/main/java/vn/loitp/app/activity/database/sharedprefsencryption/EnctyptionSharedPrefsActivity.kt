package vn.loitp.app.activity.database.sharedprefsencryption

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.utilities.LEncryptionSharedPrefsUtil
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_shared_prefs_encryption.*
import vn.loitp.app.R
import vn.loitp.app.activity.pattern.mvp.User

@LogTag("EnctyptionSharedPrefsActivity")
@IsFullScreen(false)
class EnctyptionSharedPrefsActivity : BaseFontActivity() {

    companion object {
        const val KEY_STRING = "KEY_STRING"
        const val KEY_STRING_WITH_DEFAULT_VALUE = "KEY_STRING_WITH_DEFAULT_VALUE"
        const val KEY_BOOLEAN = "KEY_BOOLEAN"
        const val KEY_FLOAT = "KEY_FLOAT"
        const val KEY_INT = "KEY_INT"
        const val KEY_LONG = "KEY_LONG"
        const val KEY_OBJECT = "KEY_OBJECT"
        const val KEY_LIST_OBJECT = "KEY_LIST_OBJECT"
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_shared_prefs_encryption
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btClearAll.setOnClickListener {
            LEncryptionSharedPrefsUtil.instance.clear()
        }
        btPutString.setOnClickListener {
            LEncryptionSharedPrefsUtil.instance.put(KEY_STRING, "This is a string!!! " + System.currentTimeMillis())
        }
        btGetString.setOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getString(KEY_STRING)
            showLongInformation(value)
        }
        btPutStringWithDefaultValue.setOnClickListener {
            LEncryptionSharedPrefsUtil.instance.put(KEY_STRING_WITH_DEFAULT_VALUE, "This is a string!!! " + System.currentTimeMillis())
        }
        btGetStringWithDefaultValue.setOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getString(KEY_STRING_WITH_DEFAULT_VALUE, "Default value")
            showLongInformation(value)
        }
        btPutBoolean.setOnClickListener {
            LEncryptionSharedPrefsUtil.instance.put(KEY_BOOLEAN, true)
        }
        btGetBoolean.setOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getBoolean(KEY_BOOLEAN)
            showLongInformation("Value: $value")
        }
        btPutFloat.setOnClickListener {
            LEncryptionSharedPrefsUtil.instance.put(KEY_FLOAT, System.currentTimeMillis().toFloat())
        }
        btGetFloat.setOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getFloat(KEY_FLOAT)
            showLongInformation("Value: $value")
        }
        btPutInt.setOnClickListener {
            LEncryptionSharedPrefsUtil.instance.put(KEY_INT, System.currentTimeMillis().toInt())
        }
        btGetInt.setOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getInt(KEY_INT)
            showLongInformation("Value: $value")
        }
        btPutLong.setOnClickListener {
            LEncryptionSharedPrefsUtil.instance.put(KEY_LONG, System.currentTimeMillis())
        }
        btGetLong.setOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getLong(KEY_LONG)
            showLongInformation("Value: $value")
        }
        btPutObject.setOnClickListener {
            val user = User()
            user.email = "Email ${System.currentTimeMillis()}"
            user.fullName = "Name ${System.currentTimeMillis()}"
            LEncryptionSharedPrefsUtil.instance.put(KEY_OBJECT, user)
        }
        btGetObject.setOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getObject(KEY_OBJECT, User::class.java)
            showLongInformation("Value: " + BaseApplication.gson.toJson(value))
        }
        btPutListObject.setOnClickListener {
            val list = ArrayList<User>()
            for (i in 0..10) {
                val user = User()
                user.email = "Email ${System.currentTimeMillis()}"
                user.fullName = "Name ${System.currentTimeMillis()}"
                list.add(user)
            }
            LEncryptionSharedPrefsUtil.instance.put(KEY_LIST_OBJECT, list)
        }
        btListGetObject.setOnClickListener {
            val type = object : TypeToken<List<User>>() {
            }.type
            val value = LEncryptionSharedPrefsUtil.instance.getObjectList(KEY_LIST_OBJECT, User::class.java, type)
            logD("list size: " + value.size)
            for (i in value.indices) {
                logD("$i -> ${value[i].fullName}")
            }
            showLongInformation("Value: " + BaseApplication.gson.toJson(value))
        }
    }

}
