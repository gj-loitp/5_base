package com.core.helper.ttt.ui.f

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import com.R
import com.annotation.LogTag
import com.core.base.BaseBottomSheetFragment
import com.core.common.Constants
import com.core.helper.ttt.helper.ComicUtils
import com.core.helper.ttt.viewmodel.TTTViewModel
import com.core.utilities.LAppResource
import com.core.utilities.LUIUtil
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
            val margin = LAppResource.getDimenValue(R.dimen.margin_medium)
            listComicType.forEach { comicType ->
                val button = AppCompatButton(c)
                button.apply {
                    text = comicType.type
                    isAllCaps = false
                    LUIUtil.setTextSize(textView = this, size = resources.getDimension(R.dimen.txt_medium))
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_chevron_right_black_48dp, 0)
                    LUIUtil.setTypeface(textView = this, pathFontAsset = Constants.FONT_PATH)

                    val layoutParams = LinearLayoutCompat.LayoutParams(
                            LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                            LinearLayoutCompat.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.setMargins(0, margin, 0, 0)
                    this.layoutParams = layoutParams

                    if (LUIUtil.isDarkTheme()) {
                        setBackgroundColor(LAppResource.getColor(R.color.dark900))
                        setTextColor(LAppResource.getColor(R.color.white))
                        LUIUtil.setDrawableTintColor(this, LAppResource.getColor(R.color.white))
                    } else {
                        setBackgroundColor(LAppResource.getColor(R.color.whiteSmoke))
                        setTextColor(LAppResource.getColor(R.color.black))
                        LUIUtil.setDrawableTintColor(this, LAppResource.getColor(R.color.black))
                    }
                }

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
