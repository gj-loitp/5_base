package com.loitp.core.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.graphics.drawable.Drawable
import com.loitp.core.utilities.LLog
import java.io.File
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class AppUtils private constructor() {

    class AppInfo(
        packageName: String?,
        name: String?,
        icon: Drawable?,
        packagePath: String?,
        versionName: String?,
        versionCode: Int,
        isSystem: Boolean
    ) {
        private var name: String? = null
        private var icon: Drawable? = null
        private var packageName: String? = null
        private var packagePath: String? = null
        private var versionName: String? = null
        private var versionCode = 0
        private var isSystem = false

        override fun toString(): String {
            return """
                 pkg name: $packageName
                 app name: $name
                 app path: $packagePath
                 app v name: $versionName
                 app v code: $versionCode
                 is system: $isSystem
            """.trimIndent()
        }

        init {
            this.name = name
            this.icon = icon
            this.packageName = packageName
            this.packagePath = packagePath
            this.versionName = versionName
            this.versionCode = versionCode
            this.isSystem = isSystem
        }
    }

    companion object {

        fun isInstallApp(
            packageName: String
        ): Boolean {
            return !isSpace(packageName) && IntentUtils.getLaunchAppIntent(packageName) != null
        }

        @Suppress("unused")
        fun installApp(
            filePath: String,
            authority: String
        ) {
            installApp(file = FileUtils.getFileByPath(filePath), authority = authority)
        }

        @Suppress("unused")
        fun installApp(
            file: File,
            authority: String
        ) {
            if (!FileUtils.isFileExists(file)) return
            Utils.getContext()
                ?.startActivity(IntentUtils.getInstallAppIntent(file = file, authority = authority))
        }

        @Suppress("unused")
        fun installApp(
            activity: Activity,
            filePath: String,
            authority: String,
            requestCode: Int
        ) {
            installApp(
                activity = activity,
                file = FileUtils.getFileByPath(filePath),
                authority = authority,
                requestCode = requestCode
            )
        }

        @Suppress("unused")
        fun installApp(
            activity: Activity,
            file: File,
            authority: String,
            requestCode: Int
        ) {
            if (!FileUtils.isFileExists(file)) return
            activity.startActivityForResult(
                IntentUtils.getInstallAppIntent(
                    file = file,
                    authority = authority
                ),
                requestCode
            )
        }

        @Suppress("unused")
        fun installAppSilent(
            filePath: String
        ): Boolean {
            val file = FileUtils.getFileByPath(filePath)
            if (!FileUtils.isFileExists(file)) return false
            val command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install $filePath"
            val commandResult = ShellUtils.execCmd(command, !isSystemApp, true)
            return commandResult.successMsg != null && commandResult.successMsg.lowercase(Locale.getDefault())
                .contains("success")
        }

        @Suppress("unused")
        fun uninstallApp(
            packageName: String
        ) {
            if (isSpace(packageName)) return
            Utils.getContext()?.startActivity(IntentUtils.getUninstallAppIntent(packageName))
        }

        @Suppress("unused")
        fun uninstallApp(
            activity: Activity,
            packageName: String,
            requestCode: Int
        ) {
            if (isSpace(packageName)) return
            activity.startActivityForResult(
                IntentUtils.getUninstallAppIntent(packageName),
                requestCode
            )
        }

        @Suppress("unused")
        fun uninstallAppSilent(
            packageName: String,
            isKeepData: Boolean
        ): Boolean {
            if (isSpace(packageName)) return false
            val command =
                "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall " + (if (isKeepData) "-k " else "") + packageName
            val commandResult = ShellUtils.execCmd(command, !isSystemApp, true)
            return commandResult.successMsg != null && commandResult.successMsg.lowercase(Locale.getDefault())
                .contains("success")
        }

        val isAppRoot: Boolean
            get() {
                val result = ShellUtils.execCmd("echo root", true)
                if (result.result == 0) {
                    return true
                }
                if (result.errorMsg != null) {
                    LLog.d("isAppRoot", result.errorMsg)
                }
                return false
            }

        @Suppress("unused")
        fun launchApp(
            packageName: String
        ) {
            if (isSpace(packageName)) return
            Utils.getContext()?.startActivity(IntentUtils.getLaunchAppIntent(packageName))
        }

        @Suppress("unused")
        fun launchApp(
            activity: Activity,
            packageName: String,
            requestCode: Int
        ) {
            if (isSpace(packageName)) return
            activity.startActivityForResult(
                IntentUtils.getLaunchAppIntent(packageName),
                requestCode
            )
        }

        @JvmStatic
        val appPackageName: String
            get() = Utils.getContext()?.packageName ?: ""

        @Suppress("unused")
        val appDetailsSettings: Unit
            get() {
                Utils.getContext()?.packageName?.let {
                    getAppDetailsSettings(it)
                }
            }

        @Suppress("unused")
        fun getAppDetailsSettings(
            packageName: String
        ) {
            if (isSpace(packageName)) return
            Utils.getContext()?.startActivity(IntentUtils.getAppDetailsSettingsIntent(packageName))
        }

        val appName: String?
            get() = getAppName(Utils.getContext()?.packageName)

        fun getAppName(
            packageName: String?
        ): String? {
            return if (isSpace(packageName)) null else try {
                val pm = Utils.getContext()?.packageManager
                packageName?.let {
                    val pi = pm?.getPackageInfo(it, 0)
                    pi?.applicationInfo?.loadLabel(pm)?.toString()
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                null
            }
        }

        @Suppress("unused")
        val appIcon: Drawable?
            get() = getAppIcon(Utils.getContext()?.packageName)

        @Suppress("unused")
        fun getAppIcon(
            packageName: String?
        ): Drawable? {
            return if (isSpace(packageName)) null else try {
                val pm = Utils.getContext()?.packageManager
                packageName?.let {
                    val pi = pm?.getPackageInfo(it, 0)
                    pi?.applicationInfo?.loadIcon(pm)
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                null
            }
        }

        val appPath: String?
            get() = getAppPath(Utils.getContext()?.packageName)

        fun getAppPath(
            packageName: String?
        ): String? {
            return if (isSpace(packageName)) null else try {
                val pm = Utils.getContext()?.packageManager
                packageName?.let {
                    val pi = pm?.getPackageInfo(it, 0)
                    pi?.applicationInfo?.sourceDir
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                null
            }
        }

        val appVersionName: String?
            get() = getAppVersionName(Utils.getContext()?.packageName)

        fun getAppVersionName(packageName: String?): String? {
            return if (isSpace(packageName)) null else try {
                val pm = Utils.getContext()?.packageManager
                packageName?.let {
                    val pi = pm?.getPackageInfo(it, 0)
                    pi?.versionName
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                null
            }
        }

        val appVersionCode: Int
            get() = getAppVersionCode(Utils.getContext()?.packageName)

        fun getAppVersionCode(packageName: String?): Int {
            if (isSpace(packageName)) {
                return -1
            } else try {
                val pm = Utils.getContext()?.packageManager
                packageName?.let {
                    val pi = pm?.getPackageInfo(it, 0)
                    return pi?.versionCode ?: -1
                }
                return -1
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return -1
            }
        }

        @Suppress("unused")
        val isSystemApp: Boolean
            get() = isSystemApp(Utils.getContext()?.packageName)

        fun isSystemApp(packageName: String?): Boolean {
            if (isSpace(packageName)) {
                return false
            } else try {
                val context = Utils.getContext() ?: return false
                val pm = context.packageManager
                packageName?.let {
                    val ai = pm.getApplicationInfo(packageName, 0)
                    return ai.flags and ApplicationInfo.FLAG_SYSTEM != 0
                }
                return false
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return false
            }
        }

        @Suppress("unused")
        val isAppDebug: Boolean
            get() = isAppDebug(Utils.getContext()?.packageName)

        fun isAppDebug(packageName: String?): Boolean {
            if (isSpace(packageName)) {
                return false
            } else try {
                val context = Utils.getContext() ?: return false
                val pm = context.packageManager
                packageName?.let {
                    val ai = pm.getApplicationInfo(packageName, 0)
                    return ai.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
                }
                return false
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return false
            }
        }

        @Suppress("unused")
        val appSignature: Array<Signature>?
            get() = getAppSignature(Utils.getContext()?.packageName)

        fun getAppSignature(packageName: String?): Array<Signature>? {
            if (isSpace(packageName)) {
                return null
            } else try {
                val pm = Utils.getContext()?.packageManager
                packageName?.let {
                    @SuppressLint("PackageManagerGetSignatures")
                    val pi = pm?.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
                    return pi?.signatures
                }
                return null
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return null
            }
        }

        @Suppress("unused")
        val appSignatureSHA1: String?
            get() = getAppSignatureSHA1(Utils.getContext()?.packageName)

        fun getAppSignatureSHA1(packageName: String?): String? {
            val signature = getAppSignature(packageName) ?: return null
            return EncryptUtils.encryptSHA1ToString(signature[0].toByteArray())
                .replace("(?<=[0-9A-F]{2})[0-9A-F]{2}".toRegex(), ":$0")
        }

        @Suppress("unused")
        val isAppForeground: Boolean
            get() {
                val manager = Utils.getContext()
                    ?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
                val info = manager?.runningAppProcesses
                if (info == null || info.size == 0) return false
                for (aInfo in info) {
                    if (aInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return aInfo.processName == Utils.getContext()?.packageName
                    }
                }
                return false
            }

        fun isAppForeground(packageName: String?): Boolean {
            return !isSpace(packageName) && packageName == ProcessUtils.foregroundProcessName
        }

        @Suppress("unused")
        val appInfo: AppInfo?
            get() = getAppInfo(Utils.getContext()?.packageName)

        fun getAppInfo(packageName: String?): AppInfo? {
            try {
                val pm = Utils.getContext()?.packageManager
                packageName?.let {
                    val pi = pm?.getPackageInfo(packageName, 0)
                    return getBean(pm, pi)
                }
                return null
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return null
            }
        }

        private fun getBean(pm: PackageManager?, pi: PackageInfo?): AppInfo? {
            if (pm == null || pi == null) {
                return null
            }
            val ai = pi.applicationInfo
            val packageName = pi.packageName
            val name = ai.loadLabel(pm).toString()
            val icon = ai.loadIcon(pm)
            val packagePath = ai.sourceDir
            val versionName = pi.versionName
            val versionCode = pi.versionCode
            val isSystem = ApplicationInfo.FLAG_SYSTEM and ai.flags != 0
            return AppInfo(
                packageName = packageName,
                name = name,
                icon = icon,
                packagePath = packagePath,
                versionName = versionName,
                versionCode = versionCode,
                isSystem = isSystem
            )
        }

        val appsInfo: List<AppInfo>
            get() {
                val list: MutableList<AppInfo> = ArrayList()
                val pm = Utils.getContext()?.packageManager
                val installedPackages = pm?.getInstalledPackages(0)
                installedPackages?.let {
                    for (pi in it) {
                        val ai = getBean(pm, pi) ?: continue
                        list.add(ai)
                    }
                }
                return list
            }

        @Suppress("unused")
        fun cleanAppData(vararg dirPaths: String?): Boolean {
            val dirs = arrayOfNulls<File>(dirPaths.size)
            var i = 0
            for (dirPath in dirPaths) {
                dirPath?.let {
                    dirs[i++] = File(it)
                }
            }
            return cleanAppData(*dirs)
        }

        @Suppress("unused")
        fun cleanAppData(vararg dirs: File?): Boolean {
            var isSuccess = CleanUtils.cleanInternalCache()
            isSuccess = isSuccess and CleanUtils.cleanInternalDbs()
            isSuccess = isSuccess and CleanUtils.cleanInternalSP()
            isSuccess = isSuccess and CleanUtils.cleanInternalFiles()
            isSuccess = isSuccess and CleanUtils.cleanExternalCache()
            for (dir in dirs) {
                isSuccess = isSuccess and CleanUtils.cleanCustomCache(dir)
            }
            return isSuccess
        }

        private fun isSpace(s: String?): Boolean {
            if (s == null) return true
            var i = 0
            val len = s.length
            while (i < len) {
                if (!Character.isWhitespace(s[i])) {
                    return false
                }
                ++i
            }
            return true
        }
    }
}
