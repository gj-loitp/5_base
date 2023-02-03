package vn.loitp.up.a.db.sharedPrefsEncryption

import android.os.Bundle
import com.google.gson.reflect.TypeToken
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseApplication
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.*
import vn.loitp.R
import vn.loitp.databinding.ADbSharedPrefsEncryptionBinding
import vn.loitp.up.a.pattern.mvp.User

/**
 * Created by Loitp on 15.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

@LogTag("EncryptionSharedPrefsActivity")
@IsFullScreen(false)
class EncryptionSharedPrefsActivity : BaseActivityFont() {

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

    private lateinit var binding: ADbSharedPrefsEncryptionBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADbSharedPrefsEncryptionBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = EncryptionSharedPrefsActivity::class.java.simpleName
        }

        binding.btClearAll.setSafeOnClickListener {
            this.clearPrefSecurity()
        }

        binding.btPutString.setSafeOnClickListener {
            putPrefSecurity(
                KEY_STRING,
                "This is a string!!! " + System.currentTimeMillis()
            )
        }
        binding.btGetString.setSafeOnClickListener {
            val value = getStringSecurity(KEY_STRING)
            showLongInformation(value)
        }

        binding.btPutStringWithDefaultValue.setSafeOnClickListener {
            putPrefSecurity(
                KEY_STRING_WITH_DEFAULT_VALUE,
                "This is a string!!! " + System.currentTimeMillis()
            )
        }
        binding.btGetStringWithDefaultValue.setSafeOnClickListener {
            val value = getStringSecurity(
                KEY_STRING_WITH_DEFAULT_VALUE,
                "Default value"
            )
            showLongInformation(value)
        }

        binding.btPutBoolean.setSafeOnClickListener {
            putPrefSecurity(KEY_BOOLEAN, true)
        }
        binding.btGetBoolean.setSafeOnClickListener {
            val value = getBooleanSecurity(KEY_BOOLEAN)
            showLongInformation("Value: $value")
        }

        binding.btPutFloat.setSafeOnClickListener {
            putPrefSecurity(KEY_FLOAT, System.currentTimeMillis().toFloat())
        }
        binding.btGetFloat.setSafeOnClickListener {
            val value = getFloatSecurity(KEY_FLOAT)
            showLongInformation("Value: $value")
        }

        binding.btPutInt.setSafeOnClickListener {
            putPrefSecurity(KEY_INT, System.currentTimeMillis().toInt())
        }
        binding.btGetInt.setSafeOnClickListener {
            val value = getIntSecurity(KEY_INT)
            showLongInformation("Value: $value")
        }

        binding.btPutLong.setSafeOnClickListener {
            putPrefSecurity(KEY_LONG, System.currentTimeMillis())
        }
        binding.btGetLong.setSafeOnClickListener {
            val value = getLongSecurity(KEY_LONG)
            showLongInformation("Value: $value")
        }

        binding.btPutObject.setSafeOnClickListener {
            val user = User()
            user.email = "Email ${System.currentTimeMillis()}"
            user.fullName = "Name ${System.currentTimeMillis()}"
            putPrefSecurity(KEY_OBJECT, user)
        }
        binding.btGetObject.setSafeOnClickListener {
            val value = getObjectSecurity(KEY_OBJECT, User::class.java)
            showLongInformation("Value: " + BaseApplication.gson.toJson(value))
        }

        binding.btPutListObject.setSafeOnClickListener {
            val list = ArrayList<User>()
            for (i in 0..10) {
                val user = User()
                user.email = "Email ${System.currentTimeMillis()}"
                user.fullName = "Name ${System.currentTimeMillis()}"
                list.add(user)
            }
            putPrefSecurity(KEY_LIST_OBJECT, list)
        }
        binding.btListGetObject.setSafeOnClickListener {
            val type = object : TypeToken<List<User>>() {
            }.type
            val value: ArrayList<User> = getObjectListSecurity(
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
