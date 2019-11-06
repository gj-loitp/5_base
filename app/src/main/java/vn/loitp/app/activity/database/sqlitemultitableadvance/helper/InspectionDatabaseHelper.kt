package vn.loitp.app.activity.database.sqlitemultitableadvance.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.core.utilities.LLog
import com.utils.util.AppUtils
import vn.loitp.app.activity.database.sqlitemultitableadvance.model.Action
import vn.loitp.app.activity.database.sqlitemultitableadvance.model.Inspection
import vn.loitp.app.app.LApplication

class InspectionDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val TAG = InspectionDatabaseHelper::class.java.name

    companion object {
        // Database Version
        private val DATABASE_VERSION = AppUtils.getAppVersionCode()

        // Database Name
        private val DATABASE_NAME = InspectionDatabaseHelper::class.java.name

        // Table Names
        private const val TABLE_INSPECTION = "tb_inspections"
        private const val TABLE_ACTION = "tb_action"

        // Common column names
        private const val KEY_ID = "id"

        // INSPECTION Table - column nmaes
        private const val KEY_INSPECTION_ID = "inspection_id"
        private const val KEY_INSPECTION_CONTENT = "inspection_content"

        // ACTION Table - column names
        private const val KEY_ACTION_TYPE = "action_type"
        private const val KEY_ACTION_INSPECTION = "action_inspection"
    }

    override fun onCreate(db: SQLiteDatabase) {
        LLog.d(TAG, "onCreate")
        // Table Create Statements
        val queryCreateTableInspection = ("CREATE TABLE " + TABLE_INSPECTION + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_INSPECTION_ID + " TEXT, "
                + KEY_INSPECTION_CONTENT + " TEXT "
                + ")")
        db.execSQL(queryCreateTableInspection)
        // Action table create statement
        val queryCreateTableAction = ("CREATE TABLE " + TABLE_ACTION + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_ACTION_TYPE + " INTEGER, "
                + KEY_ACTION_INSPECTION + " TEXT "
                + ")")
        db.execSQL(queryCreateTableAction)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        LLog.d(TAG, "onUpgrade oldVersion: $oldVersion, newVersion: $newVersion")
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS $TABLE_INSPECTION")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ACTION")

        // create new tables
        onCreate(db)
    }

    fun deleteAllDatabase() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_INSPECTION")
        db.execSQL("DELETE FROM $TABLE_ACTION")
        db.close()
    }

    fun getInspectionList(): List<Inspection> {
        val inspectionList = ArrayList<Inspection>()
        val selectQuery = "SELECT  * FROM $TABLE_INSPECTION"

        val db = this.readableDatabase
        val c = db.rawQuery(selectQuery, null)
        if (c.moveToFirst()) {
            do {
                val inspection = Inspection()
                inspection.id = c.getInt(c.getColumnIndex(KEY_ID))
                inspection.inspectionId = c.getString(c.getColumnIndex(KEY_INSPECTION_ID))
                inspection.content = c.getString(c.getColumnIndex(KEY_INSPECTION_CONTENT))
                inspectionList.add(inspection)
            } while (c.moveToNext())
        }
        c.close()
        return inspectionList
    }

    fun getInspectionCount(): Int {
        val countQuery = "SELECT  * FROM $TABLE_INSPECTION"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)
        val count = cursor.count
        cursor.close()
        return count
    }

    fun getActionList(): List<Action> {
        val actionList = ArrayList<Action>()
        val selectQuery = "SELECT  * FROM $TABLE_ACTION"

        val db = this.readableDatabase
        val c = db.rawQuery(selectQuery, null)
        if (c.moveToFirst()) {
            do {
                val action = Action()
                action.id = c.getInt(c.getColumnIndex(KEY_ID))
                action.actionType = c.getInt(c.getColumnIndex(KEY_ACTION_TYPE))
                val sInspection = c.getString(c.getColumnIndex(KEY_ACTION_INSPECTION))
                val inspection = LApplication.gson.fromJson(sInspection, Inspection::class.java)
                action.inspection = inspection
                actionList.add(action)
            } while (c.moveToNext())
        }
        c.close()
        return actionList
    }

    fun createInspection(inspection: Inspection): Long {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_INSPECTION_ID, inspection.inspectionId)
        values.put(KEY_INSPECTION_CONTENT, inspection.content)

        val noteId = db.insert(TABLE_INSPECTION, null, values)

        db.close()
        return noteId
    }

    fun getInspection(inspectionId: Long): Inspection? {
        val db = this.readableDatabase

        val selectQuery = ("SELECT  * FROM " + TABLE_INSPECTION + " WHERE "
                + KEY_ID + " = " + inspectionId)

        val c = db.rawQuery(selectQuery, null)
        if (c != null && c.moveToFirst()) {
            val inspection = Inspection()
            inspection.id = c.getInt(c.getColumnIndex(KEY_ID))
            inspection.inspectionId = c.getString(c.getColumnIndex(KEY_INSPECTION_ID))
            inspection.content = c.getString(c.getColumnIndex(KEY_INSPECTION_CONTENT))
            c.close()
            return inspection
        }
        return null
    }

    /*fun getAllInspectionByAction(actionType: String?): List<Inspection> {
        val noteList = ArrayList<Inspection>()

        val selectQuery = "SELECT  * FROM " + TABLE_ACTION +
                "WHERE " + KEY_ACTION_TYPE + " = '" + actionType + "'"

        val db = this.readableDatabase
        val c = db.rawQuery(selectQuery, null)

        if (c.moveToFirst()) {
            do {
                val td = Inspection()
                td.id = c.getInt(c.getColumnIndex(KEY_ID))
                td.inspectionId = c.getString(c.getColumnIndex(KEY_INSPECTION_ID))
                td.content = c.getString(c.getColumnIndex(KEY_INSPECTION_CONTENT))
                noteList.add(td)
            } while (c.moveToNext())
        }

        c.close()
        return noteList
    }*/


    fun updateInspection(inspection: Inspection): Int {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_INSPECTION_ID, inspection.inspectionId)
        values.put(KEY_INSPECTION_CONTENT, inspection.content)

        return db.update(TABLE_INSPECTION, values, "$KEY_ID = ?",
                arrayOf(inspection.id.toString()))
    }

    //This function returns the number of rows modified
    fun deleteInspection(id: Long): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_INSPECTION, "$KEY_ID = ?",
                arrayOf(id.toString()))
    }

    fun createAction(action: Action): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_ACTION_TYPE, action.actionType)
        values.put(KEY_ACTION_INSPECTION, LApplication.gson.toJson(action.inspection))
        return db.insert(TABLE_ACTION, null, values)
    }

    fun getAction(actionId: Long): Action? {
        val db = this.readableDatabase
        val selectQuery = ("SELECT  * FROM " + TABLE_ACTION + " WHERE "
                + KEY_ID + " = " + actionId)
        val c = db.rawQuery(selectQuery, null)
        if (c != null && c.moveToFirst()) {
            val action = Action()
            action.id = c.getInt(c.getColumnIndex(KEY_ID))
            action.actionType = c.getInt(c.getColumnIndex(KEY_ACTION_TYPE))
            val sInspection = c.getString(c.getColumnIndex(KEY_ACTION_INSPECTION))
            action.inspection = LApplication.gson.fromJson(sInspection, Inspection::class.java)
            c.close()
            return action
        }
        return null
    }

    fun updateAction(action: Action): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_ACTION_TYPE, action.actionType)
        val sInspection = LApplication.gson.toJson(action.inspection)
        values.put(KEY_ACTION_INSPECTION, sInspection)
        return db.update(TABLE_ACTION, values, "$KEY_ID = ?",
                arrayOf(action.id.toString()))
    }

    //This function returns the number of rows modified
    fun deleteAction(action: Action): Int {
        val db = this.writableDatabase
        val number = db.delete(TABLE_ACTION, "$KEY_ID = ?",
                arrayOf(action.id.toString()))
        db.close()
        return number
    }


    /*fun updateNoteTag(id: Long, tagId: Long): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_ACTION_ID_FK, tagId)
        // updating row
        return db.update(TABLE_INSPECTION, values, "$KEY_ID = ?",
                arrayOf(id.toString()))
    }

    fun deleteNoteTag(id: Long) {
        val db = this.writableDatabase
        db.delete(TABLE_INSPECTION, "$KEY_ID = ?",
                arrayOf(id.toString()))
    }*/

    fun closeDB() {
        val db = this.readableDatabase
        if (db != null && db.isOpen) {
            db.close()
        }
    }
}