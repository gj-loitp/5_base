package vn.loitp.app.activity.customviews.layout.sequencelayout

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("SequenceLayoutActivity")
@IsFullScreen(false)
// https://github.com/transferwise/sequence-layout
class SequenceLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_sequence_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        // do sth
    }
}
