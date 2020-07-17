package vn.loitp.app.activity.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utils.util.*
import kotlinx.android.synthetic.main.frm_utils.*
import vn.loitp.app.R

class FrmUtils : Fragment() {

    companion object {
        const val KEY_CLASS = "KEY_CLASS"
    }

    private val line = "*****"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frm_utils, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            when (it.getString(KEY_CLASS)) {
                ActivityUtils::class.java.simpleName -> {
                    handleActivityUtils()
                }
                AppUtils::class.java.simpleName -> {
                    handleAppUtils()
                }
                BarUtils::class.java.simpleName -> {
                    handleBarUtils()
                }
                CacheUtils::class.java.simpleName -> {
                    handleCacheUtils()
                }
                CleanUtils::class.java.simpleName -> {
                    handleCleanUtils()
                }
                ClipboardUtils::class.java.simpleName -> {
                    handleClipboardUtils()
                }
            }
        }
    }

    private fun handleClipboardUtils() {
        var text = ""

        text += "\n$line void copyText(CharSequence text)"
        text += "\n$line CharSequence getText()"
        text += "\n$line void copyUri(Uri uri)"
        text += "\n$line Uri getUri()"
        text += "\n$line void copyIntent(Intent intent)"
        text += "\n$line Intent getIntent()"

        textView.text = text
    }

    private fun handleCleanUtils() {
        var text = ""

        text += "\n$line boolean cleanInternalCache()"
        text += "\n$line boolean cleanInternalFiles()"
        text += "\n$line boolean cleanInternalDbs()"
        text += "\n$line boolean cleanInternalDbByName(String dbName)"
        text += "\n$line boolean cleanInternalSP()"
        text += "\n$line boolean cleanExternalCache()"
        text += "\n$line boolean cleanCustomCache(String dirPath)"
        text += "\n$line boolean cleanCustomCache(File dir)"

        textView.text = text
    }

    private fun handleCacheUtils() {
        textView.text = "CacheUtils"
    }

    private fun handleActivityUtils() {
        var text = ""

        text += "\n$line isActivityExists UtilsActivity: " + ActivityUtils.isActivityExists(activity?.packageName, "UtilsActivity")
        text += "\n$line getLauncherActivity: " + ActivityUtils.getLauncherActivity(activity?.packageName)
        text += "\n$line getTopActivity: " + ActivityUtils.getTopActivity()

        textView.text = text
    }

    private fun handleAppUtils() {
        var text = ""

        text += "\n$line isInstallApp ${activity?.packageName}: " + AppUtils.isInstallApp(activity?.packageName)
        text += "\n$line installApp(String filePath, String authority)"
        text += "\n$line installApp(File file, String authority)"
        text += "\n$line installApp(Activity activity, String filePath, String authority, int requestCode)"
        text += "\n$line installApp(Activity activity, File file, String authority, int requestCode)"
        text += "\n$line installAppSilent(String filePath)"
        text += "\n$line uninstallApp(String packageName)"
        text += "\n$line uninstallApp(Activity activity, String packageName, int requestCode)"
        text += "\n$line uninstallAppSilent(String packageName, boolean isKeepData)"
        text += "\n$line isAppRoot: " + AppUtils.isAppRoot()
        text += "\n$line launchApp(String packageName)"
        text += "\n$line launchApp(Activity activity, String packageName, int requestCode)"
        text += "\n$line getAppPackageName: " + AppUtils.getAppPackageName()
        text += "\n$line getAppDetailsSettings()"
        text += "\n$line getAppDetailsSettings(String packageName)"
        text += "\n$line getAppName: " + AppUtils.getAppName()
        text += "\n$line getAppName com.facebook.katana: " + AppUtils.getAppName("com.facebook.katana")
        text += "\n$line getAppIcon()"
        text += "\n$line getAppIcon(String packageName)"
        text += "\n$line getAppPath: " + AppUtils.getAppPath()
        text += "\n$line getAppPath com.facebook.katana: " + AppUtils.getAppPath("com.facebook.katana")
        text += "\n$line getAppVersionName: " + AppUtils.getAppVersionName()
        text += "\n$line getAppVersionName com.facebook.katana: " + AppUtils.getAppVersionName("com.facebook.katana")
        text += "\n$line getAppVersionCode ${activity?.packageName} " + AppUtils.getAppVersionCode(activity?.packageName)
        text += "\n$line isSystemApp ${activity?.packageName} " + AppUtils.isSystemApp(activity?.packageName)
        text += "\n$line isAppDebug ${activity?.packageName} " + AppUtils.isAppDebug(activity?.packageName)
        text += "\n$line getAppSignature ${activity?.packageName} " + AppUtils.getAppSignature(activity?.packageName)
        text += "\n$line getAppSignatureSHA1 ${activity?.packageName} " + AppUtils.getAppSignatureSHA1(activity?.packageName)
        text += "\n$line isAppForeground ${activity?.packageName} " + AppUtils.isAppForeground(activity?.packageName)
        text += "\n$line getAppInfo ${activity?.packageName} " + AppUtils.getAppInfo(activity?.packageName)
        text += "\n$line getBean(PackageManager pm, PackageInfo pi)"
        text += "\n$line getAppsInfo ${activity?.packageName} " + AppUtils.getAppsInfo()
        text += "\n$line boolean cleanAppData(String... dirPaths)"
        text += "\n$line boolean cleanAppData(File... dirs)"

        textView.text = text
    }

    private fun handleBarUtils() {
        var text = ""

        //text += "\n$LINE setColor(Activity activity, @ColorInt int color): " + BarUtils.setColor(activity, Color.RED)
        text += "\n$line void setColor(Activity activity, @ColorInt int color)"
        text += "\n$line void setColor(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha)"
        text += "\n$line void setColorForSwipeBack(Activity activity, int color)"
        text += "\n$line void setColorForSwipeBack(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha)"
        text += "\n$line void setColorNoTranslucent(Activity activity, @ColorInt int color)"
        text += "\n$line void setColorDiff(Activity activity, @ColorInt int color)"
        text += "\n$line void setTranslucent(Activity activity)"
        text += "\n$line void setTranslucent(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha)"
        text += "\n$line void setTranslucentForCoordinatorLayout(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha)"
        text += "\n$line void setTransparent(Activity activity)"
        text += "\n$line void setTranslucentDiff(Activity activity)"
        text += "\n$line void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color)"
        text += "\n$line void setColorNoTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color)"
        text += "\n$line void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha)"
        text += "\n$line void setDrawerLayoutProperty(DrawerLayout drawerLayout, ViewGroup drawerLayoutContentLayout)"
        text += "\n$line void setColorForDrawerLayoutDiff(Activity activity, DrawerLayout drawerLayout, @ColorInt int color)"
        text += "\n$line void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout)"
        text += "\n$line void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @IntRange(from = 0, to = 255) int statusBarAlpha)"
        text += "\n$line void setTransparentForDrawerLayout(Activity activity, DrawerLayout drawerLayout)"
        text += "\n$line void setTranslucentForDrawerLayoutDiff(Activity activity, DrawerLayout drawerLayout)"
        text += "\n$line void setTransparentForImageView(Activity activity, View needOffsetView)"
        text += "\n$line void setTranslucentForImageView(Activity activity, View needOffsetView)"
        text += "\n$line void setTranslucentForImageView(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha, View needOffsetView)"
        text += "\n$line void setTranslucentForImageViewInFragment(Activity activity, View needOffsetView)"
        text += "\n$line void setTransparentForImageViewInFragment(Activity activity, View needOffsetView)"
        text += "\n$line void setTranslucentForImageViewInFragment(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha, View needOffsetView)"
        text += "\n$line void hideFakeStatusBarView(Activity activity)"
        text += "\n$line void setTransparentStatusBar(Activity activity)"
        text += "\n$line void hideStatusBar(Activity activity)"
        text += "\n$line boolean isStatusBarExists(Activity activity) " + BarUtils.isStatusBarExists(activity)
        text += "\n$line int getActionBarHeight(Activity activity) " + BarUtils.getActionBarHeight(activity)
        text += "\n$line void showNotificationBar(Context context, boolean isSettingPanel)"
        text += "\n$line void hideNotificationBar(Context context)"

        textView.text = text
    }
}
