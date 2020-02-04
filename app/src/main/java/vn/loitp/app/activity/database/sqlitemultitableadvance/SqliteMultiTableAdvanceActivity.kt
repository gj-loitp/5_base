package vn.loitp.app.activity.database.sqlitemultitableadvance

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_sqlite_multi_table_advance.*
import vn.loitp.app.R
import vn.loitp.app.activity.database.sqlitemultitableadvance.helper.InspectionDatabaseHelper
import vn.loitp.app.activity.database.sqlitemultitableadvance.model.Action
import vn.loitp.app.activity.database.sqlitemultitableadvance.model.Inspection
import vn.loitp.app.app.LApplication

class SqliteMultiTableAdvanceActivity : BaseFontActivity(), View.OnClickListener {

    private lateinit var db: InspectionDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = InspectionDatabaseHelper(applicationContext)
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

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_sqlite_multi_table_advance
    }

    private fun showMsg(msg: String) {
        LLog.d(TAG, "\n" + msg)
        val tv = TextView(activity)
        tv.text = msg
        LUIUtil.setTextSize(tv, TypedValue.COMPLEX_UNIT_DIP, 10)
        ll.addView(tv)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btClearUI -> {
                ll.removeAllViews()
            }
            R.id.btDeleteAllDatabase -> {
                db.deleteAllDatabase()
                showMsg("deleteAllDatabase success")
            }
            R.id.btGetInspectionList -> {
                val inspectionList = db.getInspectionList()
                showMsg("inspectionList size: " + inspectionList.size)
                for (i in inspectionList.indices) {
                    val inspection = inspectionList[i]
                    showMsg("inspectionList: -> " + LApplication.gson.toJson(inspection))
                }
            }
            R.id.btGetInspectionCount -> {
                val inspectionCount = db.getInspectionCount()
                showMsg("inspectionCount: $inspectionCount")
            }
            R.id.btGetActionList -> {
                val actionList = db.getActionList()
                showMsg("actionList size: " + actionList.size)
                for (i in actionList.indices) {
                    val action = actionList[i]
                    showMsg("actionList: -> " + LApplication.gson.toJson(action))
                }
            }
            R.id.btCreateInspection -> {
                val inspection = Inspection()
                inspection.inspectionId = "inspectionId " + System.currentTimeMillis()
                inspection.content = "dummy content inspection " + System.currentTimeMillis()
                val id = db.createInspection(inspection)
                showMsg("createInspection id: $id -> " + LApplication.gson.toJson(inspection))
            }
            R.id.btGetInspection -> {
                val inspection = db.getInspection(2)
                showMsg("getInspection id = 2 -> " + LApplication.gson.toJson(inspection))
            }
            R.id.btUpdateInspection -> {
                val inspection = db.getInspection(1)
                if (inspection == null) {
                    showMsg("updateInspection failed because inspection == null")
                } else {
                    inspection.inspectionId = "update inspectionId " + System.currentTimeMillis()
                    inspection.content = "update content inspection " + System.currentTimeMillis()
                    val id = db.updateInspection(inspection)
                    showMsg("updateInspection success with id = $id -> " + LApplication.gson.toJson(inspection))
                }
            }
            R.id.btDeleteInspection -> {
                val id = db.deleteInspection(1)
                showMsg("deleteInspection id: $id")
            }
            R.id.btCreateAction -> {
                val action = Action()
                action.actionType = Action.ACTION_CREATE
                val inspection = Inspection()
                inspection.inspectionId = "inspectionId " + System.currentTimeMillis()
                inspection.content = "content " + System.currentTimeMillis()
                action.inspection = inspection

                val id = db.createAction(action)
                showMsg("createAction with id = $id -> " + LApplication.gson.toJson(action))

                //add to inspection table
                val inspectionId = db.createInspection(inspection)
                showMsg("then add to inspection table -> inspectionId: $inspectionId")
            }
            R.id.btUpdateAction -> {
                val action = db.getAction(1)
                if (action == null) {
                    showMsg("Cannot update action id = 1 because action == null")
                } else {
                    action.actionType = Action.ACTION_EDIT
                    action.inspection?.content = "Update content " + System.currentTimeMillis()
                    val number = db.updateAction(action)
                    showMsg("updateAction number: $number -> " + LApplication.gson.toJson(action))
                }
            }
            R.id.btGetAction -> {
                val action = db.getAction(1)
                showMsg("getAction: " + LApplication.gson.toJson(action))
            }
            R.id.btDeleteAction -> {
                val action = db.getAction(1)
                if (action == null) {
                    showMsg("Cannot delete action id = 1 because action == null")
                } else {
                    val number = db.deleteAction(action)
                    showMsg("deleteAction number: $number")
                }
            }
            R.id.btGetActionListByPage -> {
                val pageSize = 5
                val totalPage = db.getTotalPageAction(pageSize)
                //page must start with index = 0
                showMsg("=========================================")
                showMsg("=========================================")
                showMsg("=========================================")
                for (i in 0 until totalPage) {
                    showMsg("------------$i------------")
                    val list = db.getActionListByPage(i, pageSize)
                    LLog.d(TAG, "$i/$totalPage -> size: ${list.size} -> btGetActionListPage " + LApplication.gson.toJson(list))
                    showMsg("$i/$totalPage -> size: ${list.size} -> list: " + LApplication.gson.toJson(list))
                }
                showMsg("=========================================")
                showMsg("=========================================")
                showMsg("=========================================")
            }
        }
        db.closeDB()
    }

    override fun onDestroy() {
        //db.deleteAllDatabase()
        db.closeDB()
        super.onDestroy()
    }
}
