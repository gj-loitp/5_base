package vn.loitp.app.activity.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utils.util.ActivityUtils
import com.utils.util.AppUtils
import com.utils.util.BarUtils
import kotlinx.android.synthetic.main.frm_utils.*
import vn.loitp.app.R

class FrmUtils : Fragment() {

    companion object {
        const val KEY_CLASS = "KEY_CLASS"
    }

    private val LINE = "*****"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frm_utils, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val classUtil = it.getString(KEY_CLASS)
            when (classUtil) {
                ActivityUtils::class.java.simpleName -> {
                    handleActivityUtils()
                }
                AppUtils::class.java.simpleName -> {
                    handleAppUtils()
                }
                BarUtils::class.java.simpleName -> {
                    handleBarUtils()
                }
            }
        }
    }

    private fun handleActivityUtils() {
        var text = ""

        text += "\n$LINE isActivityExists UtilsActivity: " + ActivityUtils.isActivityExists(activity?.packageName, "UtilsActivity")
        text += "\n$LINE getLauncherActivity: " + ActivityUtils.getLauncherActivity(activity?.packageName)
        text += "\n$LINE getTopActivity: " + ActivityUtils.getTopActivity()

        textView.text = text
    }

    private fun handleAppUtils() {
        var text = ""

        text += "\n$LINE isInstallApp ${activity?.packageName}: " + AppUtils.isInstallApp(activity?.packageName)
        text += "\n$LINE installApp(String filePath, String authority)"
        text += "\n$LINE installApp(File file, String authority)"
        text += "\n$LINE installApp(Activity activity, String filePath, String authority, int requestCode)"
        text += "\n$LINE installApp(Activity activity, File file, String authority, int requestCode)"
        text += "\n$LINE installAppSilent(String filePath)"
        text += "\n$LINE uninstallApp(String packageName)"
        text += "\n$LINE uninstallApp(Activity activity, String packageName, int requestCode)"
        text += "\n$LINE uninstallAppSilent(String packageName, boolean isKeepData)"
        text += "\n$LINE isAppRoot: " + AppUtils.isAppRoot()
        text += "\n$LINE launchApp(String packageName)"
        text += "\n$LINE launchApp(Activity activity, String packageName, int requestCode)"
        text += "\n$LINE getAppPackageName: " + AppUtils.getAppPackageName()
        text += "\n$LINE getAppDetailsSettings()"
        text += "\n$LINE getAppDetailsSettings(String packageName)"
        text += "\n$LINE getAppName: " + AppUtils.getAppName()
        text += "\n$LINE getAppName com.facebook.katana: " + AppUtils.getAppName("com.facebook.katana")
        text += "\n$LINE getAppIcon()"
        text += "\n$LINE getAppIcon(String packageName)"
        text += "\n$LINE getAppPath: " + AppUtils.getAppPath()
        text += "\n$LINE getAppPath com.facebook.katana: " + AppUtils.getAppPath("com.facebook.katana")
        text += "\n$LINE getAppVersionName: " + AppUtils.getAppVersionName()
        text += "\n$LINE getAppVersionName com.facebook.katana: " + AppUtils.getAppVersionName("com.facebook.katana")
        text += "\n$LINE getAppVersionCode ${activity?.packageName} " + AppUtils.getAppVersionCode(activity?.packageName)
        text += "\n$LINE isSystemApp ${activity?.packageName} " + AppUtils.isSystemApp(activity?.packageName)
        text += "\n$LINE isAppDebug ${activity?.packageName} " + AppUtils.isAppDebug(activity?.packageName)
        text += "\n$LINE getAppSignature ${activity?.packageName} " + AppUtils.getAppSignature(activity?.packageName)
        text += "\n$LINE getAppSignatureSHA1 ${activity?.packageName} " + AppUtils.getAppSignatureSHA1(activity?.packageName)
        text += "\n$LINE isAppForeground ${activity?.packageName} " + AppUtils.isAppForeground(activity?.packageName)
        text += "\n$LINE getAppInfo ${activity?.packageName} " + AppUtils.getAppInfo(activity?.packageName)
        text += "\n$LINE getBean(PackageManager pm, PackageInfo pi)"
        text += "\n$LINE getAppsInfo ${activity?.packageName} " + AppUtils.getAppsInfo()
        text += "\n$LINE boolean cleanAppData(String... dirPaths)"
        text += "\n$LINE boolean cleanAppData(File... dirs)"

        textView.text = text
    }

    private fun handleBarUtils() {
        var text = ""

        //text += "\n$LINE setColor(Activity activity, @ColorInt int color): " + BarUtils.setColor(activity, Color.RED)
        text += "\n$LINE void setColor(Activity activity, @ColorInt int color)"
        text += "\n$LINE void setColor(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha)"
        text += "\n$LINE void setColorForSwipeBack(Activity activity, int color)"
        text += "\n$LINE void setColorForSwipeBack(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha)"
        text += "\n$LINE void setColorNoTranslucent(Activity activity, @ColorInt int color)"
        text += "\n$LINE void setColorDiff(Activity activity, @ColorInt int color)"
        text += "\n$LINE void setTranslucent(Activity activity)"
        text += "\n$LINE void setTranslucent(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha)"
        text += "\n$LINE void setTranslucentForCoordinatorLayout(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha)"
        text += "\n$LINE void setTransparent(Activity activity)"
        text += "\n$LINE void setTranslucentDiff(Activity activity)"
        text += "\n$LINE void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color)"
        text += "\n$LINE void setColorNoTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color)"
        text += "\n$LINE void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha)"
        text += "\n$LINE void setDrawerLayoutProperty(DrawerLayout drawerLayout, ViewGroup drawerLayoutContentLayout)"
        text += "\n$LINE void setColorForDrawerLayoutDiff(Activity activity, DrawerLayout drawerLayout, @ColorInt int color)"
        text += "\n$LINE void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout)"
        text += "\n$LINE void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @IntRange(from = 0, to = 255) int statusBarAlpha)"
        text += "\n$LINE void setTransparentForDrawerLayout(Activity activity, DrawerLayout drawerLayout)"
        text += "\n$LINE void setTranslucentForDrawerLayoutDiff(Activity activity, DrawerLayout drawerLayout)"
        text += "\n$LINE void setTransparentForImageView(Activity activity, View needOffsetView)"
        text += "\n$LINE void setTranslucentForImageView(Activity activity, View needOffsetView)"
        text += "\n$LINE void setTranslucentForImageView(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha, View needOffsetView)"
        text += "\n$LINE void setTranslucentForImageViewInFragment(Activity activity, View needOffsetView)"
        text += "\n$LINE void setTransparentForImageViewInFragment(Activity activity, View needOffsetView)"
        text += "\n$LINE void setTranslucentForImageViewInFragment(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha, View needOffsetView)"
        text += "\n$LINE void hideFakeStatusBarView(Activity activity)"
        text += "\n$LINE void setTransparentStatusBar(Activity activity)"
        text += "\n$LINE void hideStatusBar(Activity activity)"
        text += "\n$LINE boolean isStatusBarExists(Activity activity) " + BarUtils.isStatusBarExists(activity)
        text += "\n$LINE int getActionBarHeight(Activity activity) " + BarUtils.getActionBarHeight(activity)
        text += "\n$LINE void showNotificationBar(Context context, boolean isSettingPanel)"
        text += "\n$LINE void hideNotificationBar(Context context)"

        textView.text = text
    }
}
