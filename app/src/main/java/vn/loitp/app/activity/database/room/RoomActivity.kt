package vn.loitp.app.activity.database.room

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseApplication
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_database_room2.*
import vn.loitp.app.R
import vn.loitp.app.activity.database.room.model.FloorPlan

@LogTag("RoomActivity")
@IsFullScreen(false)
class RoomActivity : BaseFontActivity() {
    private var floorPlanAdapter: FloorPlanAdapter? = null
    private var homeViewModel: HomeViewModel? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_database_room2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModels()
    }

    private fun setupView() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = RoomActivity::class.java.simpleName
        }

        floorPlanAdapter = FloorPlanAdapter()
        floorPlanAdapter?.apply {
            onClickRootView = {
                logD(BaseApplication.gson.toJson(it))
                showShortInformation(BaseApplication.gson.toJson(it))
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
            hvm.saveFloorPlanActionLiveData.observe(
                owner = this,
                observer = { actionData ->
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
                }
            )

            hvm.getFloorPlanActionLiveData.observe(
                owner = this,
                observer = { actionData ->
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
                }
            )

            hvm.getByIndexFloorPlanActionLiveData.observe(
                owner = this,
                observer = { actionData ->
                    actionData.isDoing?.let {
                        if (it) {
                            progressBar.visibility = View.VISIBLE
                        } else {
                            progressBar.visibility = View.GONE
                        }
                    }
                    actionData.data?.let {
                        logD(
                            "getByIndexFloorPlanActionLiveData observe " + BaseApplication.gson.toJson(
                                it
                            )
                        )
                        showShortInformation(
                            "getByIndexFloorPlanActionLiveData:\n" + BaseApplication.gson.toJson(
                                it
                            )
                        )
                    }
                }
            )

            hvm.deleteFloorPlanActionLiveData.observe(
                owner = this,
                observer = { actionData ->
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
                }
            )

            hvm.updateFloorPlanActionLiveData.observe(
                owner = this,
                observer = { actionData ->
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
                }
            )

            hvm.deleteAllFloorPlanActionLiveData.observe(
                owner = this,
                observer = { actionData ->
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
                }
            )

            hvm.insertFloorPlanActionLiveData.observe(
                owner = this,
                observer = { actionData ->
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
                }
            )

            hvm.findFloorPlanActionLiveData.observe(
                owner = this,
                observer = { actionData ->
                    val isDoing = actionData.isDoing
                    isDoing?.let {
                        if (it) {
                            progressBar.visibility = View.VISIBLE
                        } else {
                            progressBar.visibility = View.GONE
                        }
                    }
                    // val data = actionData.data
                    if (isDoing == false) {
                        showShortInformation(
                            "findFloorPlanActionLiveData observe " + BaseApplication.gson.toJson(
                                actionData.data
                            )
                        )
                    }
                }
            )
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
