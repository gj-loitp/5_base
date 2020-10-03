package vn.loitp.app.activity.database.room

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_database_room2.*
import vn.loitp.app.R
import vn.loitp.app.activity.database.room.model.FloorPlan

@LayoutId(R.layout.activity_database_room2)
@LogTag("RoomActivity")
@IsFullScreen(false)
class RoomActivity : BaseFontActivity() {
    private var floorPlanAdapter: FloorPlanAdapter? = null
    private var homeViewModel: HomeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModels()
    }

    private fun setupView() {
        floorPlanAdapter = FloorPlanAdapter()
        floorPlanAdapter?.apply {
            onClickRootView = {
                logD(BaseApplication.gson.toJson(it))
                showShort(BaseApplication.gson.toJson(it))
            }
            onClickUpDate = {
                handleUpdate(it)
            }
            onClickDelete = {
                handleDelete(it)
            }
        }
        rvFloorPlan.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvFloorPlan.layoutManager = LinearLayoutManager(this)
        rvFloorPlan.adapter = floorPlanAdapter

        btInsert.setSafeOnClickListener {
            handleInsert()
        }
        btSaveListFrom0To10.setSafeOnClickListener {
            handleSaveListFrom0To10()
        }
        btSaveListFrom10To20.setSafeOnClickListener {
            handleSaveListFrom10To20()
        }
        btGetList.setSafeOnClickListener {
            handleGetList()
        }
        btGetListFrom3To5.setSafeOnClickListener {
            handleGetListFrom3To5()
        }
        btDeleteAll.setSafeOnClickListener {
            handleDeleteAll()
        }
        btFind1.setSafeOnClickListener {
            handleFind1()
        }
    }

    private fun setupViewModels() {
        homeViewModel = getViewModel(HomeViewModel::class.java)
        homeViewModel?.let { hvm ->
            hvm.saveFloorPlanActionLiveData.observe(this, Observer { actionData ->
                actionData.isDoing?.let {
                    if (it) {
                        progressBar.visibility = View.VISIBLE
                    } else {
                        progressBar.visibility = View.GONE
                    }
                }
                actionData.data?.let {
                    logD("floorPlanActionLiveData observe " + BaseApplication.gson.toJson(it))
                    handleGetList()
                }
            })

            hvm.getFloorPlanActionLiveData.observe(this, Observer { actionData ->
                actionData.isDoing?.let {
                    if (it) {
                        progressBar.visibility = View.VISIBLE
                    } else {
                        progressBar.visibility = View.GONE
                    }
                }
                actionData.data?.let {
                    logD("getFloorPlanActionLiveData observe " + BaseApplication.gson.toJson(it))
                    floorPlanAdapter?.setListFloorPlan(it)
                }
            })

            hvm.getByIndexFloorPlanActionLiveData.observe(this, Observer { actionData ->
                actionData.isDoing?.let {
                    if (it) {
                        progressBar.visibility = View.VISIBLE
                    } else {
                        progressBar.visibility = View.GONE
                    }
                }
                actionData.data?.let {
                    logD("getByIndexFloorPlanActionLiveData observe " + BaseApplication.gson.toJson(it))
                    showShort("getByIndexFloorPlanActionLiveData:\n" + BaseApplication.gson.toJson(it))
                }
            })

            hvm.deleteFloorPlanActionLiveData.observe(this, Observer { actionData ->
                actionData.isDoing?.let {
                    if (it) {
                        progressBar.visibility = View.VISIBLE
                    } else {
                        progressBar.visibility = View.GONE
                    }
                }
                actionData.data?.let {
                    handleGetList()
                }
            })

            hvm.updateFloorPlanActionLiveData.observe(this, Observer { actionData ->
                actionData.isDoing?.let {
                    if (it) {
                        progressBar.visibility = View.VISIBLE
                    } else {
                        progressBar.visibility = View.GONE
                    }
                }
                actionData.data?.let {
                    handleGetList()
                }
            })

            hvm.deleteAllFloorPlanActionLiveData.observe(this, Observer { actionData ->
                actionData.isDoing?.let {
                    if (it) {
                        progressBar.visibility = View.VISIBLE
                    } else {
                        progressBar.visibility = View.GONE
                    }
                }
                actionData.data?.let {
                    if (it) {
                        handleGetList()
                    }
                }
            })

            hvm.insertFloorPlanActionLiveData.observe(this, Observer { actionData ->
                actionData.isDoing?.let {
                    if (it) {
                        progressBar.visibility = View.VISIBLE
                    } else {
                        progressBar.visibility = View.GONE
                    }
                }
                actionData.data?.let {
                    handleGetList()
                }
            })

            hvm.findFloorPlanActionLiveData.observe(this, Observer { actionData ->
                val isDoing = actionData.isDoing
                isDoing?.let {
                    if (it) {
                        progressBar.visibility = View.VISIBLE
                    } else {
                        progressBar.visibility = View.GONE
                    }
                }
//                val data = actionData.data
                if (isDoing == false) {
                    showShort("findFloorPlanActionLiveData observe " + BaseApplication.gson.toJson(actionData.data))
                }
            })
        }
    }

    private fun handleInsert() {
        homeViewModel?.insertFloorPlan()
    }

    private fun handleSaveListFrom0To10() {
        homeViewModel?.saveListFrom0To10()
    }

    private fun handleSaveListFrom10To20() {
        homeViewModel?.saveListFrom10To20()
    }

    private fun handleGetList() {
        homeViewModel?.getList()
    }

    private fun handleGetListFrom3To5() {
        homeViewModel?.getListByIndex(fromIndex = 3, toIndex = 5)
    }

    private fun handleDelete(floorPlan: FloorPlan) {
        homeViewModel?.deleteFloorPlan(floorPlan)
    }

    private fun handleUpdate(floorPlan: FloorPlan) {
        floorPlan.name = "Update Name " + System.currentTimeMillis()
        homeViewModel?.updateFloorPlan(floorPlan)
    }

    private fun handleDeleteAll() {
        homeViewModel?.deleteAll()
    }

    private fun handleFind1() {
        homeViewModel?.findId(id = "1")
    }

}
