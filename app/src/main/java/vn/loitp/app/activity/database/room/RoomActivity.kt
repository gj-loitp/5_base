package vn.loitp.app.activity.database.room

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.base.BaseFontActivity
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_database_room2.*
import vn.loitp.app.R
import vn.loitp.app.activity.database.room.model.FloorPlan
import vn.loitp.app.app.LApplication

class RoomActivity : BaseFontActivity() {
    private var floorPlanAdapter: FloorPlanAdapter? = null
    private var homeViewModel: HomeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModels()
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return "loitpp" + javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_database_room2
    }

    private fun setupView() {
        floorPlanAdapter = FloorPlanAdapter()
        floorPlanAdapter?.apply {
            onClickRootView = {
                logD(LApplication.gson.toJson(it))
                showShort(LApplication.gson.toJson(it))
            }
            onClickUpDate = {
                handleUpdate(it)
            }
            onClickDelete = {
                handleDelete(it)
            }
        }
        rvFloorPlan.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        rvFloorPlan.layoutManager = LinearLayoutManager(activity)
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
        btDeleteAll.setSafeOnClickListener {
            handleDeleteAll()
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
                    logD("floorPlanActionLiveData observe " + LApplication.gson.toJson(it))
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
                    logD("getFloorPlanActionLiveData observe " + LApplication.gson.toJson(it))
                    floorPlanAdapter?.setListFloorPlan(it)
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

}
