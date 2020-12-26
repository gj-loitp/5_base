package com.utils.util

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.utils.util.Utils.Companion.getContext
import java.io.File

class IntentUtils {
    companion object {

        fun getInstallAppIntent(filePath: String, authority: String): Intent {
            return getInstallAppIntent(
                    file = FileUtils.getFileByPath(filePath),
                    authority = authority
            )
        }

        fun getInstallAppIntent(
                file: File,
                authority: String
        ): Intent {
            val intent = Intent(Intent.ACTION_VIEW)
            var data: Uri? = null
            val type = "application/vnd.android.package-archive"
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                data = Uri.fromFile(file)
            } else {
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                getContext()?.let {
                    data = FileProvider.getUriForFile(it, authority, file)
                }
            }
            data?.let {
                intent.setDataAndType(it, type)
            }
            return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        fun getUninstallAppIntent(packageName: String): Intent {
            val intent = Intent(Intent.ACTION_DELETE)
            intent.data = Uri.parse("package:$packageName")
            return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        fun getLaunchAppIntent(packageName: String): Intent? {
            return getContext()?.packageManager?.getLaunchIntentForPackage(packageName)
        }

        fun getAppDetailsSettingsIntent(packageName: String): Intent {
            val intent = Intent("android.settings.APPLICATION_DETAILS_SETTINGS")
            intent.data = Uri.parse("package:$packageName")
            return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        fun getShareTextIntent(content: String?): Intent {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, content)
            return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        fun getShareImageIntent(content: String?, imagePath: String?): Intent? {
            return getShareImageIntent(
                    content = content,
                    image = FileUtils.getFileByPath(imagePath)
            )
        }

        fun getShareImageIntent(content: String?, image: File?): Intent? {
            return if (!FileUtils.isFileExists(image)) null else getShareImageIntent(content, Uri.fromFile(image))
        }

        fun getShareImageIntent(content: String?, uri: Uri?): Intent {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, content)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.type = "image/*"
            return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        fun getComponentIntent(packageName: String, className: String): Intent {
            return getComponentIntent(packageName, className, null)
        }

        fun getComponentIntent(packageName: String, className: String, bundle: Bundle?): Intent {
            val intent = Intent(Intent.ACTION_VIEW)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            val componentName = ComponentName(packageName, className)
            intent.component = componentName
            return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        val shutdownIntent: Intent
            get() {
                val intent = Intent(Intent.ACTION_SHUTDOWN)
                return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

        fun getDialIntent(phoneNumber: String): Intent {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        fun getCallIntent(phoneNumber: String): Intent {
            val intent = Intent("android.intent.action.CALL", Uri.parse("tel:$phoneNumber"))
            return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        fun getSendSmsIntent(phoneNumber: String, content: String?): Intent {
            val uri = Uri.parse("smsto:$phoneNumber")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", content)
            return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        fun getCaptureIntent(outUri: Uri?): Intent {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri)
            return intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

}
