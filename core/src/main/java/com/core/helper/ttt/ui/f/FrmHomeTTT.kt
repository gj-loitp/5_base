package com.core.helper.ttt.ui.f

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.R
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFragment
import com.core.helper.ttt.helper.ComicUtils
import com.core.helper.ttt.viewmodel.TTTViewModel
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.l_frm_ttt_comic_home.*

@LogTag("loitppFrmHomeTTT")
class FrmHomeTTT : BaseFragment() {
    private var tTTViewModel: TTTViewModel? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_ttt_comic_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewModels()

        tTTViewModel?.setComicType(ComicUtils.getComicTypeAll())
    }

    private fun setupViews() {
        LUIUtil.setSafeOnClickListenerElastic(
                view = btSelectType,
                runnable = {
                    val bottomSheetSelectTypeTTTFragment = BottomSheetSelectTypeTTTFragment()
                    bottomSheetSelectTypeTTTFragment.show(childFragmentManager, bottomSheetSelectTypeTTTFragment.tag)
                })
    }

    private fun setupViewModels() {
        tTTViewModel = getViewModel(TTTViewModel::class.java)
        tTTViewModel?.let { vm ->
            vm.comicTypeLiveEvent.observe(viewLifecycleOwner, { comicType ->
                logD("comicTypeLiveEvent observe comicType " + BaseApplication.gson.toJson(comicType))
                setUIComicType()

                //call api
                vm.getListComic(link = comicType.url)
            })
            vm.listComicActionLiveData.observe(viewLifecycleOwner, { actionData ->
                logD("listComicActionLiveData observe actionData " + BaseApplication.gson.toJson(actionData))
            })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUIComicType() {
        tTTViewModel?.comicTypeLiveEvent?.value?.let { comicType ->
            btSelectType.text = "Thể loại: " + comicType.type
        }
    }

}
