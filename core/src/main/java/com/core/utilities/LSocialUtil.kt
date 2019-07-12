package com.core.utilities

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

import com.core.common.Constants
import com.core.loitp.facebookcomment.FacebookCommentActivity

import loitp.core.R


/**
 * File created on 11/14/2016.
 *
 * @author loitp
 */
object LSocialUtil {
    private val TAG = LSocialUtil::class.java.simpleName

    /*
     * rate app
     * @param packageName: the packageName
     */
    fun rateApp(activity: Activity, packageName: String) {
        try {
            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
            LActivityUtil.tranIn(activity)
        } catch (anfe: android.content.ActivityNotFoundException) {
            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=$packageName")))
            LActivityUtil.tranIn(activity)
        }
    }

    fun moreApp(activity: Activity) {
        val nameOfDeveloper = "NgonTinh KangKang"
        val uri = "https://play.google.com/store/apps/developer?id=$nameOfDeveloper"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        activity.startActivity(intent)
        LActivityUtil.tranIn(activity)
    }

    fun shareApp(activity: Activity) {
        try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name))
            var sAux = "\nỨng dụng này rất bổ ích, thân mời bạn tải về cài đặt để trải nghiệm\n\n"
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + activity.packageName
            intent.putExtra(Intent.EXTRA_TEXT, sAux)
            activity.startActivity(Intent.createChooser(intent, "Vui lòng chọn"))
            LActivityUtil.tranIn(activity)
        } catch (e: Exception) {
            LLog.d(TAG, "shareApp: $e")
        }
    }

    fun share(activity: Activity, msg: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name))
            //String sAux = "\nỨng dụng này rất bổ ích, thân mời bạn tải về cài đặt để trải nghiệm\n\n";
            //sAux = sAux + "https://play.google.com/store/apps/details?id=" + activity.getPackageName();
            intent.putExtra(Intent.EXTRA_TEXT, msg)
            activity.startActivity(Intent.createChooser(intent, "Share via"))
            LActivityUtil.tranIn(activity)
        } catch (e: Exception) {
            LLog.d(TAG, "shareApp: $e")
        }

    }

    //like fanpage
    fun likeFacebookFanpage(activity: Activity) {
        val facebookIntent = Intent(Intent.ACTION_VIEW)
        val facebookUrl = getFacebookPageURL(activity)
        facebookIntent.data = Uri.parse(facebookUrl)
        activity.startActivity(facebookIntent)
        LActivityUtil.tranIn(activity)
    }

    /*
    get url fb fanpage
     */
    private fun getFacebookPageURL(context: Context): String {
        val FACEBOOK_URL = "https://www.facebook.com/hoidammedocsach"
        val FACEBOOK_PAGE_ID = "hoidammedocsach"
        val packageManager = context.packageManager
        return try {
            val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
            if (versionCode >= 3002850) {
                "fb://facewebmodal/f?href=$FACEBOOK_URL"
            } else {
                "fb://page/$FACEBOOK_PAGE_ID"
            }
        } catch (e: PackageManager.NameNotFoundException) {
            FACEBOOK_URL
        }

    }

    /*
    chat with fanpage Thugiannao
     */
    fun chatMessenger(activity: Activity) {
        val packageManager = activity.packageManager
        var isFBInstalled = false
        try {
            val versionCode = packageManager.getPackageInfo("com.facebook.orca", 0).versionCode
            if (versionCode >= 0) isFBInstalled = true
        } catch (e: PackageManager.NameNotFoundException) {
            LLog.d(TAG, "packageManager com.facebook.orca: $e")
        }

        if (!isFBInstalled) {
            LDialogUtil.showDialog1(activity, activity.getString(R.string.err), activity.getString(R.string.cannot_find_messenger_app), activity.getString(R.string.ok), null)
        } else {
            var uri = Uri.parse("fb-messenger://user/")
            uri = ContentUris.withAppendedId(uri, java.lang.Long.valueOf("947139732073591"))
            val intent = Intent(Intent.ACTION_VIEW, uri)
            try {
                activity.startActivity(intent)
                LActivityUtil.tranIn(activity)
            } catch (e: Exception) {
                LDialogUtil.showDialog1(activity, activity.getString(R.string.err), activity.getString(R.string.cannot_find_messenger_app), activity.getString(R.string.ok), null)

            }

        }
    }

    /*
     * send email support
     */
    fun sendEmail(context: Context) {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto: www.muathu@gmail.com")
        context.startActivity(Intent.createChooser(emailIntent, "Send feedback"))
    }

    fun openBrowserPolicy(context: Context) {
        openUrlInBrowser(context, Constants.URL_POLICY)
    }

    fun openUrlInBrowser(context: Context, url: String) {
        val webpage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
            LActivityUtil.tranIn(context)
        }
    }

    fun openFacebookComment(context: Context, url: String) {
        val intent = Intent(context, FacebookCommentActivity::class.java)
        intent.putExtra(Constants.FACEBOOK_COMMENT_URL, url)
        context.startActivity(intent)
        LActivityUtil.tranIn(context)
    }

    fun openFacebookComment(context: Context, url: String, adUnitId: String) {
        val intent = Intent(context, FacebookCommentActivity::class.java)
        intent.putExtra(Constants.FACEBOOK_COMMENT_URL, url)
        intent.putExtra(Constants.AD_UNIT_ID_BANNER, adUnitId)
        context.startActivity(intent)
        LActivityUtil.tranIn(context)
    }
}
