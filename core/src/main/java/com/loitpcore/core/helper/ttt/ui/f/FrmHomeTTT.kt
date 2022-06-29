package com.loitpcore.core.helper.ttt.ui.f

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitpcore.R
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseApplication
import com.loitpcore.core.base.BaseFragment
import com.loitpcore.core.helper.ttt.adapter.TTTListComicAdapter
import com.loitpcore.core.helper.ttt.helper.ComicUtils
import com.loitpcore.core.helper.ttt.viewmodel.TTTViewModel
import com.loitpcore.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.l_frm_ttt_comic_home.*

@LogTag("FrmHomeTTT")
class FrmHomeTTT : BaseFragment() {

    private var tTTViewModel: TTTViewModel? = null
    private var tTTListComicAdapter = TTTListComicAdapter()

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
        btSelectType.setSafeOnClickListener {
            val bottomSheetSelectTypeTTTFragment = BottomSheetSelectTypeTTTFragment()
            bottomSheetSelectTypeTTTFragment.show(
                childFragmentManager,
                bottomSheetSelectTypeTTTFragment.tag
            )
        }
        tTTListComicAdapter.onClickRootListener = { comic, _ ->
            comic.urlImg =
                "https://kenh14cdn.com/thumb_w/660/203336854389633024/2021/1/28/photo-1-16118275741881533707171.jpg"
            tTTViewModel?.updateComic(comic)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = tTTListComicAdapter
    }

    private fun setupViewModels() {
        tTTViewModel = getViewModel(TTTViewModel::class.java)
        tTTViewModel?.let { vm ->
            vm.comicTypeLiveEvent.observe(viewLifecycleOwner, { comicType ->
                logD("comicTypeLiveEvent observe comicType " + BaseApplication.gson.toJson(comicType))
                setUIComicType()

                // call api
                vm.getListComic(link = comicType.url)
            })
            vm.listComicActionLiveData.observe(viewLifecycleOwner, { actionData ->
//                logD("listComicActionLiveData observe actionData " + BaseApplication.gson.toJson(actionData))
                if (actionData.isDoing == true) {
                    showDialogProgress()
                } else {
                    if (actionData.isSuccess == true) {
                        val listComic = actionData.data
                        if (listComic != null && listComic is ArrayList) {
                            tTTListComicAdapter.setData(listComic = listComic)
                            recyclerView.smoothScrollToPosition(0)
                        }
                        hideDialogProgress()
                    }
                }
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
