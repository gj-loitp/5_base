package vn.loitp.app.activity.database.sqlitemultitableadvance

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_sqlite_multi_table_advance.*
import loitp.basemaster.R
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
        }
        db.closeDB()
    }

    private fun test() {


        // Creating tags
//        val tag1 = Action("Shopping" + System.currentTimeMillis())
//        val tag2 = Action("Important" + System.currentTimeMillis())
//        val tag3 = Action("Watchlist" + System.currentTimeMillis())
//        val tag4 = Action("Androidhive" + System.currentTimeMillis())
//
//        // Inserting tags in db
//        val tag1Id = db.createTag(tag1)
//        val tag2Id = db.createTag(tag2)
//        val tag3Id = db.createTag(tag3)
//        val tag4Id = db.createTag(tag4)
//
//        showMsg("tag1Id: $tag1Id")
//        showMsg("tag2Id: $tag2Id")
//        showMsg("tag3Id: $tag3Id")
//        showMsg("tag4Id: $tag4Id")
//
//        val tagList = db.getTagList()
//        showMsg("tagList size: " + tagList.size)
//        for (i in tagList.indices) {
//            val t = tagList[i]
//            showMsg("tagList -> " + i + " -> " + LApplication.gson.toJson(t))
//        }
//
//        // Creating note
//        val note1 = Inspection("iPhone 5S", 0)
//        val note2 = Inspection("Galaxy Inspection II", 0)
//        val note3 = Inspection("Whiteboard", 0)
//
//        val note4 = Inspection("Riddick", 0)
//        val note5 = Inspection("Prisoners", 0)
//        val note6 = Inspection("The Croods", 0)
//        val note7 = Inspection("Insidious: Chapter 2", 0)
//
//        val note8 = Inspection("Don't forget to call MOM", 0)
//        val note9 = Inspection("Collect money from John", 0)
//
//        val note10 = Inspection("Post new Article", 0)
//        val note11 = Inspection("Take database backup", 0)
//
//        // Inserting note in db
//        // Inserting note under "Shopping" Action
//        val note1Id = db.createNote(note1, longArrayOf(tag1Id))
//        val note2Id = db.createNote(note2, longArrayOf(tag1Id))
//        val note3Id = db.createNote(note3, longArrayOf(tag1Id))
//
//        // Inserting note under "Important" Action
//        val note8Id = db.createNote(note8, longArrayOf(tag2Id))
//        val note9Id = db.createNote(note9, longArrayOf(tag2Id))
//
//        // Inserting note under "Watchlist" Action
//        val note4Id = db.createNote(note4, longArrayOf(tag3Id))
//        val note5Id = db.createNote(note5, longArrayOf(tag3Id))
//        val note6Id = db.createNote(note6, longArrayOf(tag3Id))
//        val note7Id = db.createNote(note7, longArrayOf(tag3Id))
//
//        // Inserting note under "Androidhive" Action
//        val note10Id = db.createNote(note10, longArrayOf(tag4Id))
//        val note11Id = db.createNote(note11, longArrayOf(tag4Id))
//
//        // "Post new Article" - assigning this under "Important" Action
//        // Now this will have - "Androidhive" and "Important" Tags
//        db.createNoteTag(note10Id, tag2Id)
//
//        val noteCount = db.getNoteCount()
//        showMsg("getNoteCount: $noteCount")
//
//        val noteList = db.getNoteList()
//        showMsg("noteList size: " + noteList.size)
//        for (i in noteList.indices) {
//            val td = noteList[i]
//            showMsg(">noteList " + i + " -> " + LApplication.gson.toJson(td))
//        }
//
//        // Getting note under "Watchlist" tag name
//        val tagsWatchList = db.getAllNoteByTag(tag3.tagName)
//        for (i in tagsWatchList.indices) {
//            val td = tagsWatchList[i]
//            showMsg(">tagsWatchList " + i + " -> " + LApplication.gson.toJson(td))
//        }
//
//        // Deleting
//        showMsg("Action Count Before Deleting: " + db.getNoteCount())
//        db.deleteNote(note8Id)
//        showMsg("Action Count After Deleting: " + db.getNoteCount())
//
//        // Deleting all note under "Shopping" tag
//        showMsg("Action Count Before Deleting 'Shopping' note: " + db.getNoteCount())
//        db.deleteTag(tag1, true)
//        showMsg("Action Count After Deleting 'Shopping' note: " + db.getNoteCount())
//
//        // Updating tag name
//        tag3.tagName = "Movies to watch"
//        db.updateTag(tag3)

        // Don't forget to close database connection
        db.closeDB()
    }

    override fun onDestroy() {
        //db.deleteAllDatabase()
        db.closeDB()
        super.onDestroy()
    }
}
