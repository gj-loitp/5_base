package vn.loitp.app.activity.demo.youtubeparser

import android.os.Bundle

import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import com.function.youtubeparser.ui.FrmYoutubeChannel

import vn.loitp.app.R

class YoutubeParserChannelActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frm = FrmYoutubeChannel()
        val bundle = Bundle()
        bundle.putString(FrmYoutubeChannel.KEY_WATCHER_ACTIVITY, YoutubeParserActivity::class.java.name)
        frm.arguments = bundle
        LScreenUtil.addFragment(activity, R.id.fl_container, frm, false)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_youtube_parser_channel
    }

}
