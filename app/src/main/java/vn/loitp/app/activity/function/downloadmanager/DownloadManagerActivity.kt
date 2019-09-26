package vn.loitp.app.activity.function.downloadmanager

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.utils.util.AppUtils
import com.views.OnSingleClickListener
import kotlinx.android.synthetic.main.activity_download_manager.*

class DownloadManagerActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowAdWhenExit = false

        btDownload.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                download()
            }
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return loitp.basemaster.R.layout.activity_download_manager
    }

    private fun download() {
        val url = "https://images.unsplash.com/photo-1474690870753-1b92efa1f2d8?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80"
        val token = ""
        val downloadManager = activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(url))
        request.addRequestHeader("Authorization", "Token $token")
        //request.addRequestHeader("Accept-Language", "en-US,en;q=0.5")
        //request.addRequestHeader("Content-Type", "application/json; charset=UTF-8")
        //request.addRequestHeader("Accept-Encoding", "identity")
        //request.addRequestHeader("User-Agent", "USER_AGENT")

        val fileName = "fileName.jpg"
        request.setDestinationInExternalPublicDir("/." + AppUtils.getAppName(), fileName)
        request.setTitle("titleDownloadManager" + System.currentTimeMillis())

        downloadManager.enqueue(request)
    }
}
