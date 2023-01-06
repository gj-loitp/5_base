package vn.loitp.a.u

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.*
import com.loitp.core.helper.statusbar.StatusBarCompat
import com.loitp.core.utilities.*
import com.loitp.core.utilities.LUIUtil.Companion.scrollToBottom
import kotlinx.android.synthetic.main.a_utils_core.*
import vn.loitp.R
import java.math.BigDecimal

@LogTag("UtilsCoreActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class UtilsCoreActivity : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_utils_core
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = UtilsCoreActivity::class.java.simpleName
        }
        btSetStatusBarColorAlpha.setSafeOnClickListener {
            StatusBarCompat.setStatusBarColor(this, Color.RED, 50)
        }
        btSetStatusBarColor.setSafeOnClickListener {
            StatusBarCompat.setStatusBarColor(this, Color.RED)
        }
        btTranslucentStatusBar.setSafeOnClickListener {
            StatusBarCompat.translucentStatusBar(this, true)
        }
        btHideSystemUI.setSafeOnClickListener {
            layoutRootView.hideSystemUI()
        }
        btShowSystemUI.setSafeOnClickListener {
            layoutRootView.showSystemUI()
        }
        btPlayRotate.setSafeOnClickListener {
            it.playRotate(null)
        }
        btSlideInDown.setSafeOnClickListener {
            it.slideInDown()
        }
        btSlideInUp.setSafeOnClickListener {
            it.slideInUp()
        }
        btPlayAnimRandomDuration.setSafeOnClickListener {
            it.playAnimRandomDuration()
        }
        btConvertNumberToStringFormat.setSafeOnClickListener {
            showShortInformation(System.currentTimeMillis().convertNumberToStringFormat())
        }
        btConvertNumberToString.setSafeOnClickListener {
            showShortInformation(System.currentTimeMillis().convertNumberToString())
        }
        btConvertNumberToPercent.setSafeOnClickListener {
            showShortInformation(System.currentTimeMillis().convertNumberToPercent())
        }
        btRoundBigDecimal.setSafeOnClickListener {
            showShortInformation(
                "roundBigDecimal: ${
                    BigDecimal(60.123456).roundBigDecimal(3)
                }"
            )
        }
        btLDateUtil.setSafeOnClickListener {
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
        btLDeviceUtil.setSafeOnClickListener {
            showDialogMsg(
                "isNavigationBarAvailable:${isNavigationBarAvailable()}" + "\nisTablet: ${isTablet()}" + "\nisCanOverlay: ${isCanOverlay()}" + "\nisEmulator: ${isEmulator()}" + "\ngetDeviceId: ${
                    this.getDeviceId()
                }"
            )
        }
        btSetClipboard.setSafeOnClickListener {
            showShortInformation(this.setClipboard("setClipboard ${System.currentTimeMillis()}"))
        }
        btLDisplayUtil.setSafeOnClickListener {
            showDialogMsg(
                "px2dip: ${this.px2dip(6.9f)}" + "\npx2sp: ${this.px2sp(6.9f)}" + "\nsp2px: ${
                    this.sp2px(6.9f)
                }" + "\ngetDialogW: ${this.getDialogW()}" + "\ngetScreenW: ${
                    this.getScreenW()
                }" + "\ngetScreenH: ${this.getScreenH()}"
            )
        }
        btToggleKeyboard.setSafeOnClickListener {
            this.toggleKeyboard()
        }
        btLImageUtil.setSafeOnClickListener {
            showDialogMsg("randomUrlFlickr: ${randomUrlFlickr()}")
        }
        btLMathUtil.setSafeOnClickListener {
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
        btShowStatusBar.setSafeOnClickListener {
            LScreenUtil.showStatusBar(this)
        }
        btHideStatusBar.setSafeOnClickListener {
            LScreenUtil.hideStatusBar(this)
        }
        btHideNavigationBar.setSafeOnClickListener {
            LScreenUtil.hideNavigationBar(this)
        }
        btShowNavigationBar.setSafeOnClickListener {
            LScreenUtil.showNavigationBar(this)
        }
        btHideDefaultControls.setSafeOnClickListener {
            LScreenUtil.hideDefaultControls(this)
        }
        btShowDefaultControls.setSafeOnClickListener {
            LScreenUtil.showDefaultControls(this)
        }
        btSetBrightness.setSafeOnClickListener {
            LScreenUtil.setBrightness(this, 99)
            showShortInformation("getCurrentBrightness: ${LScreenUtil.getCurrentBrightness()}")
        }
        btSendSMS.setSafeOnClickListener {
            this.sendSMS("sendSMS from Loitp ${System.currentTimeMillis()}")
        }
        btLStoreUtil.setSafeOnClickListener {
            onClickBtLStoreUtil()
        }
        btLStringUtil.setSafeOnClickListener {
            onClickBtLStringUtil()
        }
        btLValidateUtil.setSafeOnClickListener {
            onClickBtLValidateUtil()
        }
        btLauncher.setSafeOnClickListener {
            onClickBtLauncher()
        }

        tv.setMarquee(getString(R.string.large_dummy_text))
        v1.setBackgroundDrawable(createGradientDrawableWithRandomColor())
        v2.setBackgroundDrawable(createGradientDrawableWithColor(Color.RED, Color.GREEN))
        v3.setCircleViewWithColor(Color.GREEN, Color.CYAN)
        v4.setGradientBackground()
        tv1.setTextFromHTML(
            "<p>This is Loitp<br />\n" + "Dep Trai<br />\n" + "Vip<br />\n" + "Pro No1</p>"
        )
        tv1.setTextBold()
        iv.setImageFromAsset("ic_gift.png")
        srl.setProgressViewOffset(200)
        et1.setImeiActionSearch() { }
        LUIUtil.setColorSeekBar(sb1, Color.RED)
        LUIUtil.setMarginsDp(view = sb1, leftDp = 16, topDp = 16, rightDp = 16, bottomDp = 16)
        LUIUtil.setRandomBackground(layoutRootView)
        LUIUtil.getAllChildren(layoutRootView).forEach {
            logD(">>>getAllChildren ${it.id}")
        }
        LUIUtil.setCheckBoxColor(cb1, Color.GREEN, Color.CYAN)
        LUIUtil.setChangeStatusBarTintToDark(window, false)
        btScrollToBottom.setSafeOnClickListener {
            sv.scrollToBottom()
        }
        LUIUtil.setDrawableTintColor(tv1, Color.CYAN)
    }

    private fun onClickBtLStoreUtil() {
        showShortInformation("Check logcat")
        logD("isSdPresent ${LStoreUtil.isSdPresent}")
        logD("randomColorLight ${LStoreUtil.randomColorLight}")
        val file = LStoreUtil.writeToFile(folder = "test", fileName = "test1.txt", body = "loitp")
        logD("writeToFile file ${file?.path}")
        val text = LStoreUtil.readTxtFromFolder(folderName = "test", fileName = "test1.txt")
        logD("readTxtFromFolder text $text")
        val textAsset = LStoreUtil.readTxtFromAsset("txt.txt")
        logD("textAsset $textAsset")
        logD("getAvailableSpaceInMb ${LStoreUtil.getAvailableSpaceInMb()}")
        logD("getAvailableRAM ${LStoreUtil.getAvailableRAM()}")
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
