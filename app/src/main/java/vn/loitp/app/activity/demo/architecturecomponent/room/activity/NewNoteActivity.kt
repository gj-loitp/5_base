package vn.loitp.app.activity.demo.architecturecomponent.room.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_room_new_note.*
import loitp.basemaster.R

class NewNoteActivity : BaseFontActivity() {

    companion object {
        const val EXTRA_REPLY = "REPLY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        buttonSave.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWord.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editWord.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
            LActivityUtil.tranOut(activity)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_room_new_note
    }
}
