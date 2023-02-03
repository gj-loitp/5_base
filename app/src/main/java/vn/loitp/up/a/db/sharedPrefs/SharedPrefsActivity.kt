package vn.loitp.up.a.db.sharedPrefs

import android.os.Bundle
import com.google.gson.reflect.TypeToken
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseApplication
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.*
import vn.loitp.R
import vn.loitp.databinding.ADbSharedPrefsBinding
import vn.loitp.up.a.pattern.mvp.User

@LogTag("SharedPrefsActivity")
@IsFullScreen(false)
class SharedPrefsActivity : BaseActivityFont() {

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

    private lateinit var binding: ADbSharedPrefsBinding


    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADbSharedPrefsBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = SharedPrefsActivity::class.java.simpleName
        }

        binding.btPutString.setSafeOnClickListener {
            putString(
                KEY_STRING,
                "This is a string!!! " + System.currentTimeMillis()
            )
        }
        binding.btGetString.setSafeOnClickListener {
            val value = getString(KEY_STRING)
            showLongInformation(value)
        }

        binding.btPutStringWithDefaultValue.setSafeOnClickListener {
            putString(
                KEY_STRING_WITH_DEFAULT_VALUE,
                "This is a string!!! " + System.currentTimeMillis()
            )
        }
        binding.btGetStringWithDefaultValue.setSafeOnClickListener {
            val value = getString(KEY_STRING_WITH_DEFAULT_VALUE, "Default value")
            showLongInformation(value)
        }

        binding.btPutBoolean.setSafeOnClickListener {
            putBoolean(KEY_BOOLEAN, true)
        }
        binding.btGetBoolean.setSafeOnClickListener {
            val value = getBoolean(KEY_BOOLEAN)
            showLongInformation("Value: $value")
        }

        binding.btPutFloat.setSafeOnClickListener {
            putFloat(KEY_FLOAT, System.currentTimeMillis().toFloat())
        }
        binding.btGetFloat.setSafeOnClickListener {
            val value = getFloat(KEY_FLOAT)
            showLongInformation("Value: $value")
        }

        binding.btPutInt.setSafeOnClickListener {
            putInt(KEY_INT, System.currentTimeMillis().toInt())
        }
        binding.btGetInt.setSafeOnClickListener {
            val value = getInt(KEY_INT)
            showLongInformation("Value: $value")
        }

        binding.btPutLong.setSafeOnClickListener {
            putLong(KEY_LONG, System.currentTimeMillis())
        }
        binding.btGetLong.setSafeOnClickListener {
            val value = getLong(KEY_LONG)
            showLongInformation("Value: $value")
        }

        binding.btPutNumber.setSafeOnClickListener {
            putString(KEY_NUMBER, 123456.789.toString())
        }
        binding.btGetNumber.setSafeOnClickListener {
            try {
                val value = getString(KEY_NUMBER)
                showLongInformation(
                    "Value: $value -> " + value.toBigDecimalOrNull().convertToPrice()
                )
            } catch (e: Exception) {
                showShortError(e.toString())
            }
        }

        binding.btPutObject.setSafeOnClickListener {
            val user = User()
            user.email = "Email ${System.currentTimeMillis()}"
            user.fullName = "Name ${System.currentTimeMillis()}"
            putObject(KEY_OBJECT, user)
        }
        binding.btGetObject.setSafeOnClickListener {
            val value = getObject(KEY_OBJECT, User::class.java)
            showLongInformation("Value: $value")
        }

        binding.btPutListObject.setSafeOnClickListener {
            val list = ArrayList<User>()
            for (i in 0..10) {
                val user = User()
                user.email = "Email ${System.currentTimeMillis()}"
                user.fullName = "Name ${System.currentTimeMillis()}"
                list.add(user)
            }
            putObjectList(KEY_LIST_OBJECT, list)
        }
        binding.btGetListObject.setSafeOnClickListener {
            val type = object : TypeToken<List<User>>() {
            }.type
            val value: ArrayList<User> = getObjectList(KEY_LIST_OBJECT, type)
            logD("list size: " + value.size)
            for (i in value.indices) {
                logD("$i -> ${value[i].fullName}")
            }
            showLongInformation("Value: " + BaseApplication.gson.toJson(value))
        }
    }
}
