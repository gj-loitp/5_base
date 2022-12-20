package com.loitpcore.core.helper.ttt.ui.f

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.loitpcore.R
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseBottomSheetFragment
import com.loitpcore.core.helper.ttt.adapter.TTTTypeAdapter
import com.loitpcore.core.helper.ttt.helper.ComicUtils
import com.loitpcore.core.helper.ttt.viewmodel.TTTViewModel
import kotlinx.android.synthetic.main.l_bottom_sheet_ttt_select_type_fragment.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("BottomSheetSelectTypeTTTFragment")
class BottomSheetSelectTypeTTTFragment : BaseBottomSheetFragment(
    layoutId = R.layout.l_bottom_sheet_ttt_select_type_fragment,
    height = WindowManager.LayoutParams.WRAP_CONTENT,
    isDraggable = true,
    firstBehaviourState = BottomSheetBehavior.STATE_HALF_EXPANDED
) {

    private var tTTViewModel: TTTViewModel? = null
    private var tTTypeAdapter = TTTTypeAdapter()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewModels()

        val currentComicType = tTTViewModel?.comicTypeLiveEvent?.value
        tTTypeAdapter.setData(
            listComicType = ComicUtils.comicTypeList,
            currentComicType = currentComicType
        )
    }

    private fun setupViews() {
        tTTypeAdapter.onClickRootListener = { comicType, _ ->
            tTTViewModel?.setComicType(comicType)
            dismiss()
        }
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = tTTypeAdapter
    }

    private fun setupViewModels() {
        tTTViewModel = getViewModel(TTTViewModel::class.java)
        tTTViewModel?.let { _ ->
            // do sth
        }
    }
}
