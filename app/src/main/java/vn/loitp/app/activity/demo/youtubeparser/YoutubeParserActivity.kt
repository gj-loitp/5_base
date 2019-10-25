package vn.loitp.app.activity.demo.youtubeparser

import android.os.Bundle

import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import com.function.youtubeparser.ui.FrmYoutubeParser

import loitp.basemaster.R

class YoutubeParserActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val channelId = intent.getStringExtra(FrmYoutubeParser.KEY_CHANNEL_ID)
        val frmYoutubeParser = FrmYoutubeParser()
        val bundle = Bundle()
        bundle.putBoolean(FrmYoutubeParser.KEY_IS_ENGLISH_MSG, true)
        //bundle.putString(FrmYoutubeParser.KEY_CHANNEL_ID, "UCdDnufSiFrEvS7jqcKOkN0Q");//loitp channel
        bundle.putString(FrmYoutubeParser.KEY_CHANNEL_ID, channelId)
        frmYoutubeParser.arguments = bundle
        LScreenUtil.addFragment(activity, R.id.fl_container, frmYoutubeParser, false)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_youtube_parser
    }


}
