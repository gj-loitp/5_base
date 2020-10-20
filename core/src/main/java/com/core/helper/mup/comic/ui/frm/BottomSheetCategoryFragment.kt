package com.core.helper.mup.comic.ui.frm

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import com.R
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.helper.mup.comic.viewmodel.ComicViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.views.bottomsheet.LBottomSheetFragment

@LogTag("loitppBottomSheetSettingFragment")
class BottomSheetCategoryFragment : LBottomSheetFragment(
        layoutId = R.layout.l_bottom_sheet_category_fragment,
        height = WindowManager.LayoutParams.WRAP_CONTENT,
        isDraggable = true,
        firstBehaviourState = BottomSheetBehavior.STATE_EXPANDED
) {

    private var comicViewModel: ComicViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewModels()

        comicViewModel?.getListCategory()
    }

    private fun setupViews() {

    }

    private fun setupViewModels() {
        comicViewModel = getViewModel(ComicViewModel::class.java)
        comicViewModel?.let { vm ->
            vm.listCategoryActionLiveData.observe(this, Observer { actionData ->
                logD("<<<listCategoryActionLiveData observe " + BaseApplication.gson.toJson(actionData))
            })
        }
    }

}
