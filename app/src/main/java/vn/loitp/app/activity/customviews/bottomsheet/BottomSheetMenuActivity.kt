package vn.loitp.app.activity.customviews.bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.views.LToast.show
import kotlinx.android.synthetic.main.activity_bottomsheet_menu.*
import kotlinx.android.synthetic.main.bottom_sheet_0.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_bottomsheet_menu)
class BottomSheetMenuActivity : BaseFontActivity() {
    private var sheetBehavior: BottomSheetBehavior<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        click0()
        click1()
        click2()
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    @SuppressLint("SetTextI18n")
    private fun click0() {
        btPayment.setOnClickListener {
            show(activity, "Click layoutBottomSheet R.id.bt_payment")
        }
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet)
        sheetBehavior?.setBottomSheetCallback(object : BottomSheetCallback() {
            @SuppressLint("SetTextI18n")
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        logD("STATE_HIDDEN")
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        logD("STATE_HIDDEN")
                        bt0.text = "Close Sheet"
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        logD("STATE_COLLAPSED")
                        bt0.text = "Expand Sheet"
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        logD("STATE_DRAGGING")
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        logD("STATE_SETTLING")
                    }
                    else -> {

                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                logD("onSlide $slideOffset")
            }
        })
        bt0.setOnClickListener {
            sheetBehavior?.let { bsb ->
                if (bsb.state != BottomSheetBehavior.STATE_EXPANDED) {
                    bsb.state = BottomSheetBehavior.STATE_EXPANDED
                    bt0.text = "Close sheet"
                } else {
                    bsb.state = BottomSheetBehavior.STATE_COLLAPSED
                    bt0.text = "Expand sheet"
                }
            }
        }
    }

    private fun click1() {
        bt1.setOnClickListener {
            @SuppressLint("InflateParams") val view = layoutInflater.inflate(R.layout.fragment_bottom_sheet_dialog, null)
            val dialog = BottomSheetDialog(activity)
            dialog.setContentView(view)
            dialog.show()
        }
    }

    private fun click2() {
        bt2.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }
}
