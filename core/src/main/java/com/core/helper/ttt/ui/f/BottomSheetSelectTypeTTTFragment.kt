package com.core.helper.ttt.ui.f

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatButton
import com.R
import com.annotation.LogTag
import com.core.base.BaseBottomSheetFragment
import com.core.helper.ttt.helper.ComicUtils
import com.core.helper.ttt.viewmodel.TTTViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.l_bottom_sheet_ttt_select_type_fragment.*

@LogTag("loitppBottomSheetSelectTypeTTTFragment")
class BottomSheetSelectTypeTTTFragment : BaseBottomSheetFragment(
        layoutId = R.layout.l_bottom_sheet_ttt_select_type_fragment,
        height = WindowManager.LayoutParams.WRAP_CONTENT,
        isDraggable = true,
        firstBehaviourState = BottomSheetBehavior.STATE_EXPANDED
) {

    private var tTTViewModel: TTTViewModel? = null
    private val listComicType = ComicUtils.comicTypeList

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewModels()
    }

    private fun setupViews() {
        context?.let { c ->
            listComicType.forEach { comicType ->
                val button = AppCompatButton(c)
                button.text = comicType.type
                button.isAllCaps = false

                button.setSafeOnClickListener {
                    tTTViewModel?.setComicType(comicType)
                    dismiss()
                }
                layoutComicType.addView(button)
            }
        }
    }

    private fun setupViewModels() {
        tTTViewModel = getViewModel(TTTViewModel::class.java)
        tTTViewModel?.let { vm ->
            //do sth
        }
    }

}
