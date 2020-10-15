package com.core.helper.mup.comic.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFragment
import com.core.helper.mup.girl.adapter.GirlAlbumAdapter
import com.core.helper.mup.girl.adapter.GirlTitleAdapter
import com.core.helper.mup.girl.ui.GirlDetailActivity
import com.core.helper.mup.girl.viewmodel.GirlViewModel
import com.core.utilities.LActivityUtil
import com.core.utilities.LAppResource
import com.core.utilities.LUIUtil
import com.utils.util.KeyboardUtils
import kotlinx.android.synthetic.main.l_frm_girl_favourite.*

@LogTag("FrmFavourite")
class FrmFavourite : BaseFragment() {

    private var girlViewModel: GirlViewModel? = null
    private var mergeAdapter: ConcatAdapter? = null
    private var girlAlbumAdapter: GirlAlbumAdapter? = null
    private var currentKeyword: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        frmRootView = inflater.inflate(R.layout.l_frm_comic_favourite, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        getListLikeGirlPage(isDelay = false)
    }

    private fun setupViews() {
        val girlTitleAdapterTopUser = GirlTitleAdapter()
        val marginTop = LAppResource.getDimenValue(R.dimen.w_86)
        val marginStartEnd = LAppResource.getDimenValue(R.dimen.margin_medium)
        girlTitleAdapterTopUser.setMargin(marginStartEnd = marginStartEnd, marginTop = marginTop)
        girlTitleAdapterTopUser.setTitle(getString(R.string.menu_favourite))
        girlAlbumAdapter = GirlAlbumAdapter()

        girlAlbumAdapter?.let {
            it.onClickRootListener = { girlPage, _ ->
                val intent = Intent(activity, GirlDetailActivity::class.java)
                intent.putExtra(GirlDetailActivity.KEY_GIRL_PAGE, girlPage)
                //ko can dung startActivityForResult vi onResume luon load lai data
                startActivity(intent)
                LActivityUtil.tranIn(activity)
            }
            it.onClickLikeListener = { girlPage, _ ->
                girlViewModel?.likeGirlPage(girlPage = girlPage)
            }
        }

        girlTitleAdapterTopUser.let { gtatu ->
            girlAlbumAdapter?.let { gaa ->
                val listOfAdapters = listOf<RecyclerView.Adapter<out RecyclerView.ViewHolder>>(gtatu, gaa)
                mergeAdapter = ConcatAdapter(listOfAdapters)
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mergeAdapter

//        LUIUtil.setScrollChange(
//                recyclerView = recyclerView,
//                callbackRecyclerView = object : CallbackRecyclerView {
//                    override fun onTop() {
//                    }
//
//                    override fun onBottom() {
//                    }
//                })

        ivSearch.setOnClickListener {
            handleSearch(isAutoSearch = false)
        }
        LUIUtil.addTextChangedListener(editText = etSearch, delayInMls = 2000, afterTextChanged = {
            handleSearch(isAutoSearch = true)
        })
        LUIUtil.setImeiActionSearch(editText = etSearch, actionSearch = Runnable {
            ivSearch.performClick()
        })
    }

    private fun setupViewModels() {
        girlViewModel = getViewModel(GirlViewModel::class.java)
        girlViewModel?.let { vm ->
            vm.pageLikedActionLiveData.observe(viewLifecycleOwner, Observer { actionData ->
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    indicatorView.smoothToShow()
                } else {
                    indicatorView.smoothToHide()
                }

                if (isDoing == false && actionData.isSuccess == true) {
                    val listGirlPage = actionData.data
                    if (listGirlPage.isNullOrEmpty()) {
                        tvNoData.visibility = View.VISIBLE
                        girlAlbumAdapter?.setData(listGirlPage = emptyList(), isSwipeToRefresh = true)
                    } else {
                        tvNoData.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        girlAlbumAdapter?.setData(listGirlPage = listGirlPage, isSwipeToRefresh = true)
                    }
                }
            })
            vm.likeGirlPageActionLiveData.observe(this, Observer { actionData ->
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    indicatorView.smoothToShow()
                } else {
                    indicatorView.smoothToHide()
                }
                if (isDoing == false && actionData.isSuccess == true) {
                    val data = actionData.data
                    logD("<<<likeGirlPageActionLiveData observe " + BaseApplication.gson.toJson(data))
                    if (data?.isFavorites == false) {
                        getListLikeGirlPage(isDelay = true)
                    }
                }
            })
        }
    }

    private fun handleSearch(isAutoSearch: Boolean) {
        if (isAutoSearch) {
            //do nothing
        } else {
            KeyboardUtils.hideSoftInput(context, etSearch)
        }
        etSearch.apply {
            currentKeyword = this.text.toString().trim()
        }
        logD("handleSearch currentKeyword $currentKeyword")
        getListLikeGirlPage(isDelay = false)
    }

    private fun getListLikeGirlPage(isDelay: Boolean) {
        girlViewModel?.getListLikeGirlPage(currentKeyword = currentKeyword, isDelay = isDelay)
    }

}
