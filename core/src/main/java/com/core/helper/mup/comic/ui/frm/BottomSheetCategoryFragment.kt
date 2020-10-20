package com.core.helper.mup.comic.ui.frm

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.helper.mup.comic.adapter.CategoryAdapter
import com.core.helper.mup.comic.viewmodel.ComicViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.views.bottomsheet.LBottomSheetFragment
import kotlinx.android.synthetic.main.l_bottom_sheet_category_fragment.*

@LogTag("loitppBottomSheetSettingFragment")
class BottomSheetCategoryFragment : LBottomSheetFragment(
        layoutId = R.layout.l_bottom_sheet_category_fragment,
        height = WindowManager.LayoutParams.WRAP_CONTENT,
        isDraggable = true,
        firstBehaviourState = BottomSheetBehavior.STATE_EXPANDED
) {

    private var comicViewModel: ComicViewModel? = null
    private var concatAdapter: ConcatAdapter? = null
    private var categoryAdapter: CategoryAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewModels()

        comicViewModel?.getListCategory()
    }

    private fun setupViews() {
        categoryAdapter = CategoryAdapter()
        categoryAdapter?.let { categoryA ->
            val listOfAdapters = listOf<RecyclerView.Adapter<out RecyclerView.ViewHolder>>(categoryA)
            concatAdapter = ConcatAdapter(listOfAdapters)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = concatAdapter
    }

    private fun setupViewModels() {
        comicViewModel = getViewModel(ComicViewModel::class.java)
        comicViewModel?.let { vm ->
            vm.listCategoryActionLiveData.observe(this, Observer { actionData ->
//                logD("<<<listCategoryActionLiveData observe " + BaseApplication.gson.toJson(actionData))

                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess

                if (isDoing == true) {
                    indicatorView.smoothToShow()
                } else {
                    indicatorView.smoothToHide()
                }

                if (isDoing == false && isSuccess == true) {
                    val listCategory = actionData.data
                    logD("<<<listCategory " + BaseApplication.gson.toJson(listCategory))

                    if (listCategory.isNullOrEmpty()) {
                        tvNoData.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    } else {
                        tvNoData.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        categoryAdapter?.setListData(listCategory = listCategory)
                    }
                }
            })
        }
    }

}
