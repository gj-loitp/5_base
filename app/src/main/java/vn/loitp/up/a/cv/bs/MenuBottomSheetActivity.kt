package vn.loitp.up.a.cv.bs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.toast.LToast.show
import vn.loitp.R
import vn.loitp.databinding.AMenuBottomSheetBinding

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("MenuBottomSheetActivity")
@IsFullScreen(false)
class MenuBottomSheetActivity : BaseActivityFont() {
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private lateinit var binding: AMenuBottomSheetBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuBottomSheetBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = MenuBottomSheetActivity::class.java.simpleName
        }
        binding.layout0.btPayment.setSafeOnClickListener {
            show("Click layoutBottomSheet R.id.bt_payment")
        }
        bottomSheetBehavior = BottomSheetBehavior.from(binding.layout0.layoutBottomSheet)
        bottomSheetBehavior?.addBottomSheetCallback(object : BottomSheetCallback() {
            @SuppressLint("SetTextI18n")
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        logD("STATE_HIDDEN")
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        logD("STATE_HIDDEN")
                        binding.bt0.text = "Close Sheet"
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        logD("STATE_COLLAPSED")
                        binding.bt0.text = "Expand Sheet"
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
        binding.bt0.setSafeOnClickListener {
            bottomSheetBehavior?.let { bsb ->
                if (bsb.state != BottomSheetBehavior.STATE_EXPANDED) {
                    bsb.state = BottomSheetBehavior.STATE_EXPANDED
                    binding.bt0.text = "Close sheet"
                } else {
                    bsb.state = BottomSheetBehavior.STATE_COLLAPSED
                    binding.bt0.text = "Expand sheet"
                }
            }
        }
        binding.bt1.setSafeOnClickListener {
            @SuppressLint("InflateParams") val view =
                layoutInflater.inflate(R.layout.f_bottom_sheet_dialog, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()
        }
        binding.bt2.setSafeOnClickListener {
            val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
        binding.bt1Option.setSafeOnClickListener {
            showBottomSheetOptionFragment(isCancelableFragment = true,
                isShowIvClose = true,
                title = "Title",
                message = "Hellllllllllllllllllllllllllllloooooooooooo",
                textButton1 = "OK",
                onClickButton1 = {
                    showShortInformation("onClickButton1")
                    logD("onClickButton1")
                },
                onDismiss = {
                    showShortInformation("onDismiss")
                    logD("onDismiss")
                })
        }
        binding.bt2Option.setSafeOnClickListener {
            showBottomSheetOptionFragment(isCancelableFragment = true,
                isShowIvClose = true,
                title = "Title",
                message = "Hellllllllllllllllllllllllllllloooooooooooo",
                textButton1 = "OK",
                textButton2 = "Cancel",
                onClickButton1 = {
                    showShortInformation("onClickButton1")
                    logD("onClickButton1")
                },
                onClickButton2 = {
                    showShortInformation("onClickButton2")
                    logD("onClickButton2")
                },
                onDismiss = {
                    showShortInformation("onDismiss")
                    logD("onDismiss")
                })
        }
        binding.bt3Option.setSafeOnClickListener {
            showBottomSheetOptionFragment(isCancelableFragment = true,
                isShowIvClose = true,
                title = "Title",
                message = "Hellllllllllllllllllllllllllllloooooooooooo",
                textButton1 = "OK",
                textButton2 = "Cancel",
                textButton3 = "No",
                onClickButton1 = {
                    showShortInformation("onClickButton1")
                    logD("onClickButton1")
                },
                onClickButton2 = {
                    showShortInformation("onClickButton2")
                    logD("onClickButton2")
                },
                onClickButton3 = {
                    showShortInformation("onClickButton3")
                    logD("onClickButton3")
                },
                onDismiss = {
                    showShortInformation("onDismiss")
                    logD("onDismiss")
                })
        }
        binding.btBaseBottomSheet.setSafeOnClickListener {
            val bottomSheet = SampleBaseBottomSheet()
            bottomSheet.show(
                supportFragmentManager, bottomSheet.tag
            )
        }
    }
}
