package vn.loitp.app.activity.database.sqliteMultiTableAdvance

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseApplication
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_sqlite_multi_table_advance.*
import vn.loitp.app.R
import vn.loitp.app.activity.database.sqliteMultiTableAdvance.helper.InspectionDatabaseHelper
import vn.loitp.app.activity.database.sqliteMultiTableAdvance.model.Action
import vn.loitp.app.activity.database.sqliteMultiTableAdvance.model.Inspection

/**
 * Created by Loitp on 15.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

@LogTag("SqliteMultiTableAdvanceActivity")
@IsFullScreen(false)
class SqliteMultiTableAdvanceActivity : BaseFontActivity(), View.OnClickListener {

    private lateinit var inspectionDatabaseHelper: InspectionDatabaseHelper

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_sqlite_multi_table_advance
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inspectionDatabaseHelper = InspectionDatabaseHelper(applicationContext)
        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = SqliteMultiTableAdvanceActivity::class.java.simpleName
        }

        btClearUI.setOnClickListener(this)
        btDeleteAllDatabase.setOnClickListener(this)
        btGetInspectionList.setOnClickListener(this)
        btGetInspectionCount.setOnClickListener(this)
        btGetActionList.setOnClickListener(this)
        btCreateInspection.setOnClickListener(this)
        btGetInspection.setOnClickListener(this)
        btUpdateInspection.setOnClickListener(this)
        btDeleteInspection.setOnClickListener(this)
        btCreateAction.setOnClickListener(this)
        btUpdateAction.setOnClickListener(this)
        btGetAction.setOnClickListener(this)
        btDeleteAction.setOnClickListener(this)
        btGetActionListByPage.setOnClickListener(this)
    }

    private fun showMsg(msg: String) {
        logD("\n" + msg)
        val tv = TextView(this)
        tv.text = msg
        LUIUtil.setTextSize(textView = tv, size = resources.getDimension(R.dimen.txt_small))
        tv.setTextColor(Color.RED)
        ll.addView(tv)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btClearUI -> {
                ll.removeAllViews()
            }
            R.id.btDeleteAllDatabase -> {
                inspectionDatabaseHelper.deleteAllDatabase()
                showMsg("deleteAllDatabase success")
            }
            R.id.btGetInspectionList -> {
                val inspectionList = inspectionDatabaseHelper.getInspectionList()
                showMsg("inspectionList size: " + inspectionList.size)
                for (i in inspectionList.indices) {
                    val inspection = inspectionList[i]
                    showMsg("inspectionList: -> " + BaseApplication.gson.toJson(inspection))
                }
            }
            R.id.btGetInspectionCount -> {
                val inspectionCount = inspectionDatabaseHelper.getInspectionCount()
                showMsg("inspectionCount: $inspectionCount")
            }
            R.id.btGetActionList -> {
                val actionList = inspectionDatabaseHelper.getActionList()
                showMsg("actionList size: " + actionList.size)
                for (i in actionList.indices) {
                    val action = actionList[i]
                    showMsg("actionList: -> " + BaseApplication.gson.toJson(action))
                }
            }
            R.id.btCreateInspection -> {
                val inspection = Inspection()
                inspection.inspectionId = "inspectionId " + System.currentTimeMillis()
                inspection.content = "dummy content inspection " + System.currentTimeMillis()
                val id = inspectionDatabaseHelper.createInspection(inspection)
                showMsg("createInspection id: $id -> " + BaseApplication.gson.toJson(inspection))
            }
            R.id.btGetInspection -> {
                val inspection = inspectionDatabaseHelper.getInspection(2)
                showMsg("getInspection id = 2 -> " + BaseApplication.gson.toJson(inspection))
            }
            R.id.btUpdateInspection -> {
                val inspection = inspectionDatabaseHelper.getInspection(1)
                if (inspection == null) {
                    showMsg("updateInspection failed because inspection == null")
                } else {
                    inspection.inspectionId = "update inspectionId " + System.currentTimeMillis()
                    inspection.content = "update content inspection " + System.currentTimeMillis()
                    val id = inspectionDatabaseHelper.updateInspection(inspection)
                    showMsg(
                        "updateInspection success with id = $id -> " + BaseApplication.gson.toJson(
                            inspection
                        )
                    )
                }
            }
            R.id.btDeleteInspection -> {
                val id = inspectionDatabaseHelper.deleteInspection(1)
                showMsg("deleteInspection id: $id")
            }
            R.id.btCreateAction -> {
                val action = Action()
                action.actionType = Action.ACTION_CREATE
                val inspection = Inspection()
                inspection.inspectionId = "inspectionId " + System.currentTimeMillis()
                inspection.content = "content " + System.currentTimeMillis()
                action.inspection = inspection

                val id = inspectionDatabaseHelper.createAction(action)
                showMsg("createAction with id = $id -> " + BaseApplication.gson.toJson(action))

                // add to inspection table
                val inspectionId = inspectionDatabaseHelper.createInspection(inspection)
                showMsg("then add to inspection table -> inspectionId: $inspectionId")
            }
            R.id.btUpdateAction -> {
                val action = inspectionDatabaseHelper.getAction(1)
                if (action == null) {
                    showMsg("Cannot update action id = 1 because action == null")
                } else {
                    action.actionType = Action.ACTION_EDIT
                    action.inspection?.content = "Update content " + System.currentTimeMillis()
                    val number = inspectionDatabaseHelper.updateAction(action)
                    showMsg("updateAction number: $number -> " + BaseApplication.gson.toJson(action))
                }
            }
            R.id.btGetAction -> {
                val action = inspectionDatabaseHelper.getAction(1)
                showMsg("getAction: " + BaseApplication.gson.toJson(action))
            }
            R.id.btDeleteAction -> {
                val action = inspectionDatabaseHelper.getAction(1)
                if (action == null) {
                    showMsg("Cannot delete action id = 1 because action == null")
                } else {
                    val number = inspectionDatabaseHelper.deleteAction(action)
                    showMsg("deleteAction number: $number")
                }
            }
            R.id.btGetActionListByPage -> {
                val pageSize = 5
                val totalPage = inspectionDatabaseHelper.getTotalPageAction(pageSize)
                // page must start with index = 0
                showMsg("=========================================")
                showMsg("=========================================")
                showMsg("=========================================")
                for (i in 0 until totalPage) {
                    showMsg("------------$i------------")
                    val list = inspectionDatabaseHelper.getActionListByPage(i, pageSize)
                    logD(
                        "$i/$totalPage -> size: ${list.size} -> btGetActionListPage " + BaseApplication.gson.toJson(
                            list
                        )
                    )
                    showMsg(
                        "$i/$totalPage -> size: ${list.size} -> list: " + BaseApplication.gson.toJson(
                            list
                        )
                    )
                }
                showMsg("=========================================")
                showMsg("=========================================")
                showMsg("=========================================")
            }
        }
        inspectionDatabaseHelper.closeDB()
    }

    override fun onDestroy() {
        // db.deleteAllDatabase()
        inspectionDatabaseHelper.closeDB()
        super.onDestroy()
    }
}
