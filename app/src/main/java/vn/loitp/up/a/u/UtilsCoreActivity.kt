package vn.loitp.up.a.u

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.*
import com.loitp.core.helper.statusbar.StatusBarCompat
import vn.loitp.R
import vn.loitp.databinding.AUtilsCoreBinding
import java.math.BigDecimal

@LogTag("UtilsCoreActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class UtilsCoreActivity : BaseActivityFont() {

    private lateinit var binding: AUtilsCoreBinding
//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AUtilsCoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = UtilsCoreActivity::class.java.simpleName
        }
        binding.btSetStatusBarColorAlpha.setSafeOnClickListener {
            StatusBarCompat.setStatusBarColor(this, Color.RED, 50)
        }
        binding.btSetStatusBarColor.setSafeOnClickListener {
            StatusBarCompat.setStatusBarColor(this, Color.RED)
        }
        binding.btTranslucentStatusBar.setSafeOnClickListener {
            StatusBarCompat.translucentStatusBar(this, true)
        }
        binding.btHideSystemUI.setSafeOnClickListener {
            binding.layoutRootView.hideSystemUI()
        }
        binding.btShowSystemUI.setSafeOnClickListener {
            binding.layoutRootView.showSystemUI()
        }
        binding.btPlayRotate.setSafeOnClickListener {
            it.playRotate(null)
        }
        binding.btSlideInDown.setSafeOnClickListener {
            it.slideInDown()
        }
        binding.btSlideInUp.setSafeOnClickListener {
            it.slideInUp()
        }
        binding.btPlayAnimRandomDuration.setSafeOnClickListener {
            it.playAnimRandomDuration()
        }
        binding.btConvertNumberToStringFormat.setSafeOnClickListener {
            showShortInformation(System.currentTimeMillis().convertNumberToStringFormat())
        }
        binding.btConvertNumberToString.setSafeOnClickListener {
            showShortInformation(System.currentTimeMillis().convertNumberToString())
        }
        binding.btConvertNumberToPercent.setSafeOnClickListener {
            showShortInformation(System.currentTimeMillis().convertNumberToPercent())
        }
        binding.btRoundBigDecimal.setSafeOnClickListener {
            showShortInformation(
                "roundBigDecimal: ${
                    BigDecimal(60.123456).roundBigDecimal(3)
                }"
            )
        }
        binding.btLDateUtil.setSafeOnClickListener {
            val msg =
                "currentDate: $currentDate\ncurrentYearMonth: $currentYearMonth\ncurrentMonth: $currentMonth" + "\nconvertFormatDate: ${
                    "12/03/2022 01:02:03".convertFormatDate(
                        "dd/MM/yyyy Hh:mm:ss", "yyyy/MM/dd"
                    )
                }" + "\nstringToDate: ${
                    "12/03/2022 01:02:03".stringToDate(
                        "dd/MM/yyyy Hh:mm:ss"
                    )
                }" + "\ndateToString: ${
                    getDate(year = 1993, month = 2, day = 4).dateToString(
                        "yyyy/MM/dd"
                    )
                }" + "\nformatDatePicker: ${
                    formatDatePicker(
                        year = 1993, month = 2, day = 4, format = "yyyy/MM/dd"
                    )
                }" + "\ngetDateWithoutTime: ${"04/02/1993".getDateWithoutTime()}" + "\nconvertDateToTimestamp: ${
                    "14-09-2017".convertDateToTimestamp()
                }" + "\nzeroTime: ${
                    getDate(
                        year = 1993, month = 2, day = 4
                    ).zeroTime()
                }" + "\nconvertDateToTimeStamp: ${"1993-02-04'T'11:22:33".convertDateToTimeStamp()}" + "\nconvertStringToCalendar: ${
                    "1993-02-04".convertStringToCalendar()
                }" + "\nconvertStringDate: ${
                    "02/04/1993".convertStringDate("MM/dd/yyyy")
                }"
            showDialogMsg(msg)
        }
        binding.btLDeviceUtil.setSafeOnClickListener {
            showDialogMsg(
                "isNavigationBarAvailable:${isNavigationBarAvailable()}" + "\nisTablet: ${isTablet()}" + "\nisCanOverlay: ${isCanOverlay()}" + "\nisEmulator: ${isEmulator()}" + "\ngetDeviceId: ${
                    this.getDeviceId()
                }"
            )
        }
        binding.btSetClipboard.setSafeOnClickListener {
            showShortInformation(this.setClipboard("setClipboard ${System.currentTimeMillis()}"))
        }
        binding.btLDisplayUtil.setSafeOnClickListener {
            showDialogMsg(
                "px2dip: ${this.px2dip(6.9f)}" + "\npx2sp: ${this.px2sp(6.9f)}" + "\nsp2px: ${
                    this.sp2px(6.9f)
                }" + "\ngetDialogW: ${this.getDialogW()}" + "\ngetScreenW: ${
                    this.getScreenW()
                }" + "\ngetScreenH: ${this.getScreenH()}"
            )
        }
        binding.btToggleKeyboard.setSafeOnClickListener {
            this.toggleKeyboard()
        }
        binding.btLImageUtil.setSafeOnClickListener {
            showDialogMsg("randomUrlFlickr: ${randomUrlFlickr()}")
        }
        binding.btLMathUtil.setSafeOnClickListener {
            showDialogMsg(
                "roundBigDecimal ${
                    BigDecimal(6.123456).roundBigDecimal(
                        newScale = 3
                    )
                }" + "\ngetUSCLN: ${
                    getUSCLN(
                        6,
                        9
                    )
                }" + "\ngetBSCNN: ${getBSCNN(6, 9)}"
            )
        }
        binding.btShowStatusBar.setSafeOnClickListener {
            this.showStatusBar()
        }
        binding.btHideStatusBar.setSafeOnClickListener {
            this.hideStatusBar()
        }
        binding.btHideNavigationBar.setSafeOnClickListener {
            this.hideNavigationBar()
        }
        binding.btShowNavigationBar.setSafeOnClickListener {
            this.showNavigationBar()
        }
        binding.btHideDefaultControls.setSafeOnClickListener {
            this.hideDefaultControls()
        }
        binding.btShowDefaultControls.setSafeOnClickListener {
            this.showDefaultControls()
        }
        binding.btSetBrightness.setSafeOnClickListener {
            this.setBrightness(99)
            showShortInformation("getCurrentBrightness: ${getCurrentBrightness()}")
        }
        binding.btSendSMS.setSafeOnClickListener {
            this.sendSMS("sendSMS from Loitp ${System.currentTimeMillis()}")
        }
        binding.btLStoreUtil.setSafeOnClickListener {
            onClickBtLStoreUtil()
        }
        binding.btLStringUtil.setSafeOnClickListener {
            onClickBtLStringUtil()
        }
        binding.btLValidateUtil.setSafeOnClickListener {
            onClickBtLValidateUtil()
        }
        binding.btLauncher.setSafeOnClickListener {
            onClickBtLauncher()
        }

        binding.tv.setMarquee(getString(com.loitp.R.string.large_dummy_text))
        binding.v1.setBackgroundDrawable(createGradientDrawableWithRandomColor())
        binding.v2.setBackgroundDrawable(createGradientDrawableWithColor(Color.RED, Color.GREEN))
        binding.v3.setCircleViewWithColor(Color.GREEN, Color.CYAN)
        binding.v4.setGradientBackground()
        binding.tv1.setTextFromHTML(
            "<p>This is Loitp<br />\n" + "Dep Trai<br />\n" + "Vip<br />\n" + "Pro No1</p>"
        )
        binding.tv1.setTextBold()
        binding.iv.setImageFromAsset("ic_gift.png")
        binding.srl.setProgressViewOffset(200)
        binding.et1.setImeiActionSearch() { }
        binding.sb1.setColorSeekBar(Color.RED)
        binding.sb1.setMarginsDp(
            leftDp = 16, topDp = 16, rightDp = 16, bottomDp = 16
        )
        binding.layoutRootView.setRandomBackground()
        binding.layoutRootView.getAllChildren().forEach {
            logD(">>>getAllChildren ${it.id}")
        }
        binding.cb1.setCheckBoxColor(Color.GREEN, Color.CYAN)
        this.setChangeStatusBarTintToDark(false)
        binding.btScrollToBottom.setSafeOnClickListener {
            binding.sv.scrollToBottom()
        }
        binding.tv1.setDrawableTintColor(Color.CYAN)
    }

    private fun onClickBtLStoreUtil() {
        showShortInformation("Check logcat")
        logD("isSdPresent $isSdPresent")
        logD("randomColorLight $randomColorLight")
        val file = writeToFile(folder = "test", fileName = "test1.txt", body = "loitp")
        logD("writeToFile file ${file?.path}")
        val text = readTxtFromFolder(folderName = "test", fileName = "test1.txt")
        logD("readTxtFromFolder text $text")
        val textAsset = readTxtFromAsset("txt.txt")
        logD("textAsset $textAsset")
        logD("getAvailableSpaceInMb ${getAvailableSpaceInMb()}")
        logD("getAvailableRAM ${getAvailableRAM()}")
    }

    private fun onClickBtLStringUtil() {
        showShortInformation("Check logcat")
        val s =
            "<p>This is Loitp<br />\n" + "Dep Trai<br />\n" + "Vip<br />\n" + "Pro No1</p>".convertHTMLTextToPlainText()
        logD("s $s")
    }

    private fun onClickBtLValidateUtil() {
        showShortInformation("Check logcat")
        logD("isValidEmail freuss47: ${"freuss47".isValidEmail()}")
        logD("isValidEmail freuss47@gmail.com: ${"freuss47@gmail.com".isValidEmail()}")
        logD("isValidPassword 123456: ${("123456".isValidPassword())}")
        logD("isValidPassword 123456abcd: ${"123456abcd".isValidPassword()}")
        logD("isValidPassword 123456abcdA@: ${"123456abcdA@".isValidPassword()}")
    }

    private fun onClickBtLauncher() {
        val isDefaultLauncher = this.isDefaultLauncher()
        showShortInformation("isDefaultLauncher $isDefaultLauncher")
    }
}
