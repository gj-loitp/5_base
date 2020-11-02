package com.core.helper.mup.girl.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFragment
import com.core.helper.mup.girl.adapter.*
import com.core.helper.mup.girl.model.GirlTopUser
import com.core.helper.mup.girl.model.GirlTopVideo
import com.core.helper.mup.girl.viewmodel.GirlViewModel
import com.core.utilities.LActivityUtil
import com.core.utilities.LScreenUtil
import com.core.utilities.LUIUtil
import com.interfaces.CallbackRecyclerView
import com.utils.util.KeyboardUtils
import kotlinx.android.synthetic.main.l_frm_girl_home.*
import kotlinx.android.synthetic.main.l_frm_girl_home.recyclerView

@LogTag("FrmHome")
class FrmHome : BaseFragment() {
    private var girlViewModel: GirlViewModel? = null
    private var concatAdapter: ConcatAdapter? = null
    private var girlHeaderAdapter: GirlHeaderAdapter? = null
    private var girlTopUserAdapter: GirlTopUserAdapter? = null
    private var girlTopVideoAdapter: GirlTopVideoAdapter? = null
    private var girlAlbumAdapter: GirlAlbumAdapter? = null
    private var girlProgressAdapter: GirlProgressAdapter? = null
    private var currentPageIndex = 0
    private var totalPage = Int.MAX_VALUE
    private var currentKeyword: String = ""

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_girl_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewModels()
        getPage(isSwipeToRefresh = false)
        getListGirlTopUser()
        getListGirlTopVideo()
    }

    override fun onResume() {
        super.onResume()
        logD("onResume")

        girlViewModel?.getListLikeGirlPage(currentKeyword = "", isDelay = false)
    }

    private fun getPage(isSwipeToRefresh: Boolean) {
        girlViewModel?.getPage(pageIndex = currentPageIndex, keyWord = currentKeyword, isSwipeToRefresh = isSwipeToRefresh)
    }

    private fun getListGirlTopUser() {
        val listGirlTopUser = ArrayList<GirlTopUser>()

        val girlTopUserLoi = GirlTopUser(
                avatar = "https://live.staticflickr.com/8612/28213046065_796c678f46_q.jpg",
                name = "Lợi Dubai"
        )
        listGirlTopUser.add(element = girlTopUserLoi)

        val girlTopUserToai = GirlTopUser(
                avatar = "https://live.staticflickr.com/5712/30595575736_2f7825cdec_q.jpg",
                name = "Toại Titu"
        )
        listGirlTopUser.add(element = girlTopUserToai)

        val girlTopUserHung = GirlTopUser(
                avatar = "https://live.staticflickr.com/65535/49424976107_5e9e680df8_q.jpg",
                name = "Hưng Cu Đen"
        )
        listGirlTopUser.add(element = girlTopUserHung)

//        val girlTopUserTuan = GirlTopUser(
//                avatar = "https://live.staticflickr.com/65535/49424972522_254064a97d_q.jpg",
//                name = "Tuấn Liệt"
//        )
//        listGirlTopUser.add(element = girlTopUserTuan)

//        val girlTopUserTien = GirlTopUser(
//                avatar = "https://live.staticflickr.com/65535/48111902461_f316050394_q.jpg",
//                name = "Tiên Giả"
//        )
//        listGirlTopUser.add(element = girlTopUserTien)

        girlTopUserAdapter?.setListGirlTopUser(listGirlTopUser)
    }

    private fun getListGirlTopVideo() {
        val listGirlTopVideo = ArrayList<GirlTopVideo>()

        var girlTopVideo = GirlTopVideo(
                cover = "https://live.staticflickr.com/65535/49458467042_782fe58a37.jpg",
                title = "'24/365 with BLACKPINK' EP.12",
                link = "https://www.youtube.com/watch?v=Is0iob8lz4w"
        )
        listGirlTopVideo.add(element = girlTopVideo)

//        girlTopVideo = GirlTopVideo(
//                cover = "https://live.staticflickr.com/65535/49458467042_782fe58a37.jpg",
//                title = "'How You Like That' M/V",
//                link = "https://www.youtube.com/watch?v=ioNng23DkIM"
//        )
//        listGirlTopVideo.add(element = girlTopVideo)

//        girlTopVideo = GirlTopVideo(
//                cover = "https://live.staticflickr.com/65535/49458467042_782fe58a37.jpg",
//                title = "LIKEY M/V",
//                link = "https://www.youtube.com/watch?v=V2hlQkVJZhE"
//        )
//        listGirlTopVideo.add(element = girlTopVideo)

//        girlTopVideo = GirlTopVideo(
//                cover = "https://live.staticflickr.com/65535/49458467042_782fe58a37.jpg",
//                title = "'붐바야'(BOOMBAYAH) M/V",
//                link = "https://www.youtube.com/watch?v=bwmSjveL3Lc"
//        )
//        listGirlTopVideo.add(element = girlTopVideo)

        girlTopVideoAdapter?.setListGirlTopUser(listGirlTopVideo)
    }

    private fun setupViews() {
        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout = swipeRefreshLayout)
        LUIUtil.setProgressViewOffset(swipeRefreshLayout = swipeRefreshLayout, topMargin = LScreenUtil.screenHeight / 6)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            currentPageIndex = 0
            getPage(isSwipeToRefresh = true)
        }
        girlHeaderAdapter = GirlHeaderAdapter()
        val girlTitleAdapterTopUser = GirlTitleAdapter()
        girlTitleAdapterTopUser.setTitle(getString(R.string.top_user))
        girlTopUserAdapter = GirlTopUserAdapter()
        val girlTitleAdapterVideo = GirlTitleAdapter()
        girlTitleAdapterVideo.setTitle(getString(R.string.video))
        girlTopVideoAdapter = GirlTopVideoAdapter()
        val girlTitleAdapterAlbum = GirlTitleAdapter()
        girlTitleAdapterAlbum.setTitle(getString(R.string.album))
        girlAlbumAdapter = GirlAlbumAdapter()
        girlProgressAdapter = GirlProgressAdapter()

        girlAlbumAdapter?.let {
            it.onClickRootListener = { girlPage, _ ->
                val intent = Intent(activity, GirlDetailActivity::class.java)
                intent.putExtra(GirlDetailActivity.KEY_GIRL_PAGE, girlPage)
                startActivity(intent)
                LActivityUtil.tranIn(activity)
            }
            it.onClickLikeListener = { girlPage, _ ->
                girlViewModel?.likeGirlPage(girlPage = girlPage)
            }
        }

        girlTopUserAdapter?.onClickRootView = { _ ->
            //TODO loitpp do sth
        }
        girlTopVideoAdapter?.onClickRootView = { girlTopVideo ->
            LUIUtil.playYoutube(activity = activity, url = girlTopVideo.link)
        }

        girlHeaderAdapter?.let { gha ->
            girlTitleAdapterTopUser.let { gtatu ->
                girlTopUserAdapter?.let { gtua ->
                    girlTitleAdapterVideo.let { gtav ->
                        girlTopVideoAdapter?.let { gtva ->
                            girlTitleAdapterAlbum.let { gtaa ->
                                girlAlbumAdapter?.let { gaa ->
                                    val listOfAdapters = listOf<RecyclerView.Adapter<out RecyclerView.ViewHolder>>(gha, gtatu, gtua, gtav, gtva, gtaa, gaa)
                                    concatAdapter = ConcatAdapter(listOfAdapters)
                                }
                            }
                        }
                    }
                }
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = concatAdapter

        LUIUtil.setScrollChange(
                recyclerView = recyclerView,
                callbackRecyclerView = object : CallbackRecyclerView {
                    override fun onTop() {
                        logD("onTop")
                    }

                    override fun onBottom() {
                        logD("onBottom $currentPageIndex/$totalPage")
                        if (currentPageIndex < totalPage) {
                            currentPageIndex++
                            girlProgressAdapter?.let { gpa ->
                                concatAdapter?.let { ma ->
                                    ma.addAdapter(gpa)
                                    recyclerView.smoothScrollToPosition(ma.itemCount - 1)
                                }
                            }
                            getPage(isSwipeToRefresh = false)
                        }
                    }

                    override fun onScrolled(isScrollDown: Boolean) {
                    }
                })

        ivSearch.setOnClickListener {
            handleSearch(isAutoSearch = false)
        }
        LUIUtil.addTextChangedListener(editText = etSearch, delayInMls = 2000, afterTextChanged = {
//            logD("addTextChangedListener $it")
            handleSearch(isAutoSearch = true)
        })
        LUIUtil.setImeiActionSearch(editText = etSearch, actionSearch = {
            ivSearch.performClick()
        })
    }

    private fun setupViewModels() {
        girlViewModel = getViewModel(GirlViewModel::class.java)
        girlViewModel?.let { vm ->
            vm.pageActionLiveData.observe(viewLifecycleOwner, { actionData ->
//                logD("userActionLiveData $actionData")
                val isDoing = actionData.isDoing
//                swipeRefreshLayout.isRefreshing = isDoing == true

                if (isDoing == true) {
                    indicatorView.smoothToShow()
                } else {
                    indicatorView.smoothToHide()
                }

                if (isDoing == false && actionData.isSuccess == true) {
                    val listGirlPage = actionData.data
                    if (listGirlPage.isNullOrEmpty()) {
                        tvNoData.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    } else {
                        totalPage = actionData.totalPages ?: 0

                        tvNoData.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        girlProgressAdapter?.let {
                            concatAdapter?.removeAdapter(it)
                        }
                        girlHeaderAdapter?.setData(girlPage = listGirlPage.random())
                        girlAlbumAdapter?.setData(listGirlPage = listGirlPage, isSwipeToRefresh = actionData.isSwipeToRefresh
                                ?: false)

                    }
                }
            })
            vm.likeGirlPageActionLiveData.observe(this, { actionData ->
//                logD("<<<likeGirlPageActionLiveData observe " + BaseApplication.gson.toJson(actionData))
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    indicatorView.smoothToShow()
                } else {
                    indicatorView.smoothToHide()
                }
                if (isDoing == false && actionData.isSuccess == true) {
                    logD("<<<likeGirlPageActionLiveData observe " + BaseApplication.gson.toJson(actionData.data))
//                    girlPage = actionData.data
//                    if (girlPage?.isFavorites == true) {
//                        showLong(getString(R.string.added_to_favorites))
//                    } else {
//                        showLong(getString(R.string.removed_from_favorites))
//                    }
                }
            })
            vm.pageLikedActionLiveData.observe(viewLifecycleOwner, { actionData ->
                if (actionData.isDoing == false && actionData.isSuccess == true) {
                    val listGirlPageLiked = actionData.data
                    logD("<<<pageLikedActionLiveData observe " + BaseApplication.gson.toJson(listGirlPageLiked))
                    listGirlPageLiked?.let {
                        girlAlbumAdapter?.updateData(listGirlPage = it)
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
        getPage(isSwipeToRefresh = true)
    }
}
