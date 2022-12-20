package vn.loitp.app.activity.database.sharedPrefsEncryption

import android.os.Bundle
import com.google.gson.reflect.TypeToken
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseApplication
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LEncryptionSharedPrefsUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_shared_prefs_encryption.*
import vn.loitp.app.R
import vn.loitp.app.activity.pattern.mvp.User

/**
 * Created by Loitp on 15.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

@LogTag("EncryptionSharedPrefsActivity")
@IsFullScreen(false)
class EncryptionSharedPrefsActivity : BaseFontActivity() {

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
            this.tvTitle?.text = EncryptionSharedPrefsActivity::class.java.simpleName
        }

        btClearAll.setSafeOnClickListener {
            LEncryptionSharedPrefsUtil.instance.clear()
        }

        btPutString.setSafeOnClickListener {
            LEncryptionSharedPrefsUtil.instance.put(
                KEY_STRING,
                "This is a string!!! " + System.currentTimeMillis()
            )
        }
        btGetString.setSafeOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getString(KEY_STRING)
            showLongInformation(value)
        }

        btPutStringWithDefaultValue.setSafeOnClickListener {
            LEncryptionSharedPrefsUtil.instance.put(
                KEY_STRING_WITH_DEFAULT_VALUE,
                "This is a string!!! " + System.currentTimeMillis()
            )
        }
        btGetStringWithDefaultValue.setSafeOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getString(
                KEY_STRING_WITH_DEFAULT_VALUE,
                "Default value"
            )
            showLongInformation(value)
        }

        btPutBoolean.setSafeOnClickListener {
            LEncryptionSharedPrefsUtil.instance.put(KEY_BOOLEAN, true)
        }
        btGetBoolean.setSafeOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getBoolean(KEY_BOOLEAN)
            showLongInformation("Value: $value")
        }

        btPutFloat.setSafeOnClickListener {
            LEncryptionSharedPrefsUtil.instance.put(KEY_FLOAT, System.currentTimeMillis().toFloat())
        }
        btGetFloat.setSafeOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getFloat(KEY_FLOAT)
            showLongInformation("Value: $value")
        }

        btPutInt.setSafeOnClickListener {
            LEncryptionSharedPrefsUtil.instance.put(KEY_INT, System.currentTimeMillis().toInt())
        }
        btGetInt.setSafeOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getInt(KEY_INT)
            showLongInformation("Value: $value")
        }

        btPutLong.setSafeOnClickListener {
            LEncryptionSharedPrefsUtil.instance.put(KEY_LONG, System.currentTimeMillis())
        }
        btGetLong.setSafeOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getLong(KEY_LONG)
            showLongInformation("Value: $value")
        }

        btPutObject.setSafeOnClickListener {
            val user = User()
            user.email = "Email ${System.currentTimeMillis()}"
            user.fullName = "Name ${System.currentTimeMillis()}"
            LEncryptionSharedPrefsUtil.instance.put(KEY_OBJECT, user)
        }
        btGetObject.setSafeOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getObject(KEY_OBJECT, User::class.java)
            showLongInformation("Value: " + BaseApplication.gson.toJson(value))
        }

        btPutListObject.setSafeOnClickListener {
            val list = ArrayList<User>()
            for (i in 0..10) {
                val user = User()
                user.email = "Email ${System.currentTimeMillis()}"
                user.fullName = "Name ${System.currentTimeMillis()}"
                list.add(user)
            }
            LEncryptionSharedPrefsUtil.instance.put(KEY_LIST_OBJECT, list)
        }
        btListGetObject.setSafeOnClickListener {
            val type = object : TypeToken<List<User>>() {
            }.type
            val value: ArrayList<User> = LEncryptionSharedPrefsUtil.instance.getObjectList(
                key = KEY_LIST_OBJECT,
                typeOfT = type
            )
            logD("list size: " + value.size)
            for (i in value.indices) {
                logD("$i -> ${value[i].fullName}")
            }
            showLongInformation("Value: " + BaseApplication.gson.toJson(value))
        }
    }
}
