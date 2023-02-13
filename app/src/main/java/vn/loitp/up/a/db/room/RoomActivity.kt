package vn.loitp.up.a.db.room

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseApplication
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ADbRoom2Binding
import vn.loitp.up.a.db.room.md.FloorPlan

@LogTag("RoomActivity")
@IsFullScreen(false)
class RoomActivity : BaseActivityFont() {
    private var floorPlanAdapter: FloorPlanAdapter? = null
    private var homeViewModel: HomeViewModel? = null
    private lateinit var binding: ADbRoom2Binding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADbRoom2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModels()
    }

    private fun setupView() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
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
        binding.rvFloorPlan.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvFloorPlan.layoutManager = LinearLayoutManager(this)
        binding.rvFloorPlan.adapter = floorPlanAdapter

        binding.btInsert.setSafeOnClickListener {
            handleInsert()
        }
        binding.btSaveListFrom0To10.setSafeOnClickListener {
            handleSaveListFrom0To10()
        }
        binding.btSaveListFrom10To20.setSafeOnClickListener {
            handleSaveListFrom10To20()
        }
        binding.btGetList.setSafeOnClickListener {
            handleGetList()
        }
        binding.btGetListFrom3To5.setSafeOnClickListener {
            handleGetListFrom3To5()
        }
        binding.btDeleteAll.setSafeOnClickListener {
            handleDeleteAll()
        }
        binding.btFind1.setSafeOnClickListener {
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
                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.progressBar.visibility = View.GONE
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
                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.progressBar.visibility = View.GONE
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
                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.progressBar.visibility = View.GONE
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
                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.progressBar.visibility = View.GONE
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
                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.progressBar.visibility = View.GONE
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
                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.progressBar.visibility = View.GONE
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
                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.progressBar.visibility = View.GONE
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
                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.progressBar.visibility = View.GONE
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
