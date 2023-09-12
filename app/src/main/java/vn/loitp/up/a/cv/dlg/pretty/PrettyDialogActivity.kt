package vn.loitp.up.a.cv.dlg.pretty

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.dlg.prettyDialog.PrettyDialog
import vn.loitp.databinding.ADlgPrettyBinding

@LogTag("PrettyDialogActivity")
@IsFullScreen(false)
class PrettyDialogActivity : BaseActivityFont(), OnClickListener {
    private var prettyDialog: PrettyDialog? = null
    private lateinit var binding: ADlgPrettyBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADlgPrettyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/mjn1369/PrettyDialog"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = PrettyDialogActivity::class.java.simpleName
        }
        binding.btShow1.setOnClickListener(this)
        binding.btShow2.setOnClickListener(this)
        binding.btShow3.setOnClickListener(this)
        binding.btShow4.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btShow1 -> show1()
            binding.btShow2 -> show2()
            binding.btShow3 -> show3()
            binding.btShow4 -> show4()
        }
    }

    private fun show1() {
        prettyDialog = PrettyDialog(this)
        prettyDialog?.setTitle("PrettyDialog Title")?.setMessage("PrettyDialog Message")?.show()
    }

    private fun show2() {
        prettyDialog = PrettyDialog(this)
        prettyDialog?.apply {
            setTitle("PrettyDialog Title")
                .setMessage("PrettyDialog Message")
                .setIcon(com.loitp.R.drawable.ic_info_black_48dp)
                .setIconTint(com.loitp.R.color.green)
                .setIconCallback {
                    showShortInformation("onClick setIconCallback")
                    prettyDialog?.cancel()
                }
                .show()
        }
    }

    private fun show3() {
        prettyDialog = PrettyDialog(this)
        // button OnClick listener
        prettyDialog?.apply {
            setTitle("PrettyDialog Title")
                .setMessage("PrettyDialog Message")
                .setIcon(com.loitp.R.drawable.ic_info_black_48dp)
                .setIconTint(com.loitp.R.color.green)
                .setIconCallback {
                    showShortInformation("onClick setIconCallback")
                }
                // OK button
                .addButton(
                    "OK", // button text
                    com.loitp.R.color.white,
                    com.loitp.R.color.green
                ) {
                    showShortInformation("onClick OK")
                    prettyDialog?.cancel()
                }
                // Cancel button
                .addButton(
                    "Cancel",
                    com.loitp.R.color.white,
                    com.loitp.R.color.red
                ) {
                    showShortInformation("onClick Cancel")
                    prettyDialog?.cancel()
                }
                // 3rd button
                .addButton(
                    "Option 3",
                    com.loitp.R.color.black,
                    com.loitp.R.color.gray
                ) {
                    showShortInformation("onClick Option 3")
                    prettyDialog?.cancel()
                }
                .show()
        }
    }

    private fun show4() {
        prettyDialog = PrettyDialog(this)
        prettyDialog?.apply {
            setTitle("PrettyDialog Title")
                .setTitleColor(com.loitp.R.color.blue)
                .setMessage("By agreeing to our terms and conditions, you agree to our terms and conditions.")
                .setMessageColor(com.loitp.R.color.gray)
                .setTypeface(Typeface.createFromAsset(resources.assets, "fonts/android_font.TTF"))
                .setAnimationEnabled(true)
                .setIcon(com.loitp.R.drawable.ic_info_black_48dp)
                .setIconTint(com.loitp.R.color.green)
                .setTextSizeTitle(resources.getDimension(com.loitp.R.dimen.txt_medium))
                .setTextSizeMsg(resources.getDimension(com.loitp.R.dimen.txt_medium))
                .setIconCallback {
                    showShortInformation("onClick setIconCallback")
                }
                // OK button
                .addButton(
                    "OK", // button text
                    com.loitp.R.color.white,
                    com.loitp.R.color.green
                ) {
                    showShortInformation("onClick OK")
                    prettyDialog?.cancel()
                }
                // Cancel button
                .addButton(
                    "Cancel",
                    com.loitp.R.color.white,
                    com.loitp.R.color.red
                ) {
                    showShortInformation("onClick Cancel")
                    prettyDialog?.cancel()
                }
                // 3rd button
                .addButton(
                    "Option 3",
                    com.loitp.R.color.black,
                    com.loitp.R.color.gray
                ) {
                    showShortInformation("onClick Option 3")
                    prettyDialog?.cancel()
                }
                .setTextSizeButton(resources.getDimension(com.loitp.R.dimen.txt_medium))
                .show()
        }
    }

    override fun onDestroy() {
        prettyDialog?.cancel()
        super.onDestroy()
    }
}
