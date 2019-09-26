package vn.loitp.app.activity.function.downloadmanager

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.core.base.BaseFontActivity
import com.views.OnSingleClickListener
import kotlinx.android.synthetic.main.activity_download_manager.*
import java.io.File

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
//        val url = ""
//        val token = ""
//        val downloadManager = activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//        val request = DownloadManager.Request(Uri.parse(url))
//        request.addRequestHeader("Authorization", "Token $token")
//        request.addRequestHeader("Accept-Language", "en-US,en;q=0.5")
//        request.addRequestHeader("Content-Type", "application/json; charset=UTF-8")
//        request.addRequestHeader("Accept-Encoding", "identity")
//        request.addRequestHeader("User-Agent", "USER_AGENT")
//
//        //val direct = File(Environment.getExternalStorageDirectory() + "/Abeti")
//        //if (!direct.exists()) {
//        //    direct.mkdirs()
//        //}
//
//        request.setDestinationInExternalPublicDir("/Abeti", filenamex)
//        request.setTitle("titleDownloadManager" + System.currentTimeMillis())
//
//        downloadManager.enqueue(request)
    }
}
