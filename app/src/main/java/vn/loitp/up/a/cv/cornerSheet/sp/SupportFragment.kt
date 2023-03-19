package vn.loitp.up.a.cv.cornerSheet.sp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.github.heyalex.CornerDrawer
import com.github.heyalex.cornersheet.behavior.CornerSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior
import vn.loitp.R
import vn.loitp.databinding.FSupportBinding

class SupportFragment : Fragment() {
    private lateinit var binding: FSupportBinding

    private lateinit var backPressedCallback: OnBackPressedCallback
    lateinit var behavior: CornerSheetBehavior<CornerDrawer>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FSupportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        behavior =
            BottomSheetBehavior.from(view.findViewById<CornerDrawer>(R.id.corner_drawer)) as CornerSheetBehavior<CornerDrawer>
        behavior.halfExpandedRatio = 0.7f

        backPressedCallback = object :
            OnBackPressedCallback(behavior.state != BottomSheetBehavior.STATE_COLLAPSED) {
            override fun handleOnBackPressed() {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, backPressedCallback)

        binding.cornerDrawer.findViewById<View>(R.id.header_root).setOnClickListener {
            if (behavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        binding.cornerDrawer.findViewById<Toolbar>(R.id.support_toolbar)
            .setNavigationOnClickListener {
                backPressedCallback.handleOnBackPressed()
            }

        changeStatusBarIconColor(behavior.state == BottomSheetBehavior.STATE_EXPANDED)

        var isLight = false
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset < 0.98) {
                    if (!isLight) {
                        changeStatusBarIconColor(false)
                        isLight = true
                    }
                } else if (isLight) {
                    changeStatusBarIconColor(true)
                    isLight = false
                }
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                backPressedCallback.isEnabled = newState != BottomSheetBehavior.STATE_COLLAPSED
            }
        })

    }

    fun changeStatusBarIconColor(isLight: Boolean) {
        activity?.window?.let {
            var flags = it.decorView.systemUiVisibility
            flags = if (isLight) {
                flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            it.decorView.systemUiVisibility = flags
        }
    }
}
