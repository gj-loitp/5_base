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

        textView.text = text
    }

    private fun handleBarUtils() {

    }
}
