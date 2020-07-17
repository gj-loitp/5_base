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

        text += "\nisActivityExists UtilsActivity: " + ActivityUtils.isActivityExists(activity?.packageName, "UtilsActivity")
        text += "\ngetLauncherActivity: " + ActivityUtils.getLauncherActivity(activity?.packageName)
        text += "\ngetTopActivity: " + ActivityUtils.getTopActivity()

        textView.text = text
    }

    private fun handleAppUtils() {
        var text = ""

        text += "\nisInstallApp ${activity?.packageName}: " + AppUtils.isInstallApp(activity?.packageName)
        text += "\ninstallApp(String filePath, String authority)"
        text += "\ninstallApp(File file, String authority)"
        text += "\ninstallApp(Activity activity, String filePath, String authority, int requestCode)"
        text += "\ninstallApp(Activity activity, File file, String authority, int requestCode)"
        text += "\ninstallAppSilent(String filePath)"
        text += "\nuninstallApp(String packageName)"
        text += "\nuninstallApp(Activity activity, String packageName, int requestCode)"
        text += "\nuninstallAppSilent(String packageName, boolean isKeepData)"
        text += "\nisAppRoot: " + AppUtils.isAppRoot()
        text += "\nlaunchApp(String packageName)"
        text += "\nlaunchApp(Activity activity, String packageName, int requestCode)"
        text += "\ngetAppPackageName: " + AppUtils.getAppPackageName()
        text += "\ngetAppDetailsSettings()"
        text += "\ngetAppDetailsSettings(String packageName)"
        text += "\ngetAppName: " + AppUtils.getAppName()
        text += "\ngetAppName com.facebook.katana: " + AppUtils.getAppName("com.facebook.katana")
        text += "\ngetAppIcon()"
        text += "\ngetAppIcon(String packageName)"
        text += "\ngetAppPath: " + AppUtils.getAppPath()
        text += "\ngetAppPath com.facebook.katana: " + AppUtils.getAppPath("com.facebook.katana")
        text += "\ngetAppVersionName: " + AppUtils.getAppVersionName()
        text += "\ngetAppVersionName com.facebook.katana: " + AppUtils.getAppVersionName("com.facebook.katana")
        text += "\ngetAppVersionCode ${activity?.packageName} " + AppUtils.getAppVersionCode(activity?.packageName)
        text += "\nisSystemApp ${activity?.packageName} " + AppUtils.isSystemApp(activity?.packageName)
        text += "\nisAppDebug ${activity?.packageName} " + AppUtils.isAppDebug(activity?.packageName)
        text += "\ngetAppSignature ${activity?.packageName} " + AppUtils.getAppSignature(activity?.packageName)
        text += "\ngetAppSignatureSHA1 ${activity?.packageName} " + AppUtils.getAppSignatureSHA1(activity?.packageName)

        textView.text = text
    }

    private fun handleBarUtils() {

    }
}
