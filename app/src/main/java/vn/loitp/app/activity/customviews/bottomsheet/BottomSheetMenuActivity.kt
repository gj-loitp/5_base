package vn.loitp.app.activity.customviews.bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.views.LToast.show
import kotlinx.android.synthetic.main.activity_bottomsheet_menu.*
import kotlinx.android.synthetic.main.bottom_sheet_0.*
import vn.loitp.app.R

@LogTag("BottomSheetMenuActivity")
@IsFullScreen(false)
class BottomSheetMenuActivity : BaseFontActivity() {
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_bottomsheet_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        btPayment.setOnClickListener {
            show("Click layoutBottomSheet R.id.bt_payment")
        }
        bottomSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet)
        bottomSheetBehavior?.addBottomSheetCallback (object : BottomSheetCallback() {
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
            bottomSheetBehavior?.let { bsb ->
                if (bsb.state != BottomSheetBehavior.STATE_EXPANDED) {
                    bsb.state = BottomSheetBehavior.STATE_EXPANDED
                    bt0.text = "Close sheet"
                } else {
                    bsb.state = BottomSheetBehavior.STATE_COLLAPSED
                    bt0.text = "Expand sheet"
                }
            }
        }
        bt1.setOnClickListener {
            @SuppressLint("InflateParams") val view = layoutInflater.inflate(R.layout.fragment_bottom_sheet_dialog, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()
        }
        bt2.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }

}
