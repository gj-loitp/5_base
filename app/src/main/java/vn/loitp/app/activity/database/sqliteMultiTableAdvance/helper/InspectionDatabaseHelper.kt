package vn.loitp.app.activity.database.sqliteMultiTableAdvance.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.loitp.core.base.BaseApplication
import com.loitp.core.utils.AppUtils
import vn.loitp.app.activity.database.sqliteMultiTableAdvance.model.Action
import vn.loitp.app.activity.database.sqliteMultiTableAdvance.model.Inspection

/**
 * Created by Loitp on 15.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

class InspectionDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // Database Version
        private val DATABASE_VERSION = AppUtils.appVersionCode

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
        // Table Create Statements
        val queryCreateTableInspection = (
                "CREATE TABLE " + TABLE_INSPECTION + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY, " +
                        KEY_INSPECTION_ID + " TEXT, " +
                        KEY_INSPECTION_CONTENT + " TEXT " +
                        ")"
                )
        db.execSQL(queryCreateTableInspection)
        // Action table create statement
        val queryCreateTableAction = (
                "CREATE TABLE " + TABLE_ACTION + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY, " +
                        KEY_ACTION_TYPE + " INTEGER, " +
                        KEY_ACTION_INSPECTION + " TEXT " +
                        ")"
                )
        db.execSQL(queryCreateTableAction)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
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
                val inspection = BaseApplication.gson.fromJson(sInspection, Inspection::class.java)
                action.inspection = inspection
                actionList.add(action)
            } while (c.moveToNext())
        }
        c.close()
        return actionList
    }

    private fun getActionCount(): Int {
        val countQuery = "SELECT  * FROM $TABLE_ACTION"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)
        val count = cursor.count
        cursor.close()
        return count
    }

    fun getTotalPageAction(pageSize: Int): Int {
        val count = getActionCount()
        return if (count % pageSize == 0) {
            count / pageSize
        } else {
            count / pageSize + 1
        }
    }

    // page 1 -> getActionListByPage(0, 50)
    // page 2 -> getActionListByPage(1, 50)
    fun getActionListByPage(page: Int, pageSize: Int): List<Action> {
//        SELECT * FROM table limit 100` -- get 1st 100 records
//        SELECT * FROM table limit 100, 200` -- get 200 records beginning with row 101

        val startIndexByPage = page * pageSize
        val actionList = ArrayList<Action>()
        val selectQuery = "SELECT  * FROM $TABLE_ACTION LIMIT $startIndexByPage, $pageSize "

        val db = this.readableDatabase
        val c = db.rawQuery(selectQuery, null)
        if (c.moveToFirst()) {
            do {
                val action = Action()
                action.id = c.getInt(c.getColumnIndex(KEY_ID))
                action.actionType = c.getInt(c.getColumnIndex(KEY_ACTION_TYPE))
                val sInspection = c.getString(c.getColumnIndex(KEY_ACTION_INSPECTION))
                val inspection = BaseApplication.gson.fromJson(sInspection, Inspection::class.java)
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

        val selectQuery = (
                "SELECT  * FROM " + TABLE_INSPECTION + " WHERE " +
                        KEY_ID + " = " + inspectionId
                )

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

    fun updateInspection(inspection: Inspection): Int {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_INSPECTION_ID, inspection.inspectionId)
        values.put(KEY_INSPECTION_CONTENT, inspection.content)

        return db.update(
            TABLE_INSPECTION, values, "$KEY_ID = ?",
            arrayOf(inspection.id.toString())
        )
    }

    // This function returns the number of rows modified
    fun deleteInspection(id: Long): Int {
        val db = this.writableDatabase
        return db.delete(
            TABLE_INSPECTION, "$KEY_ID = ?",
            arrayOf(id.toString())
        )
    }

    fun createAction(action: Action): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_ACTION_TYPE, action.actionType)
        values.put(KEY_ACTION_INSPECTION, BaseApplication.gson.toJson(action.inspection))
        return db.insert(TABLE_ACTION, null, values)
    }

    fun getAction(actionId: Long): Action? {
        val db = this.readableDatabase
        val selectQuery = (
                "SELECT  * FROM " + TABLE_ACTION + " WHERE " +
                        KEY_ID + " = " + actionId
                )
        val c = db.rawQuery(selectQuery, null)
        if (c != null && c.moveToFirst()) {
            val action = Action()
            action.id = c.getInt(c.getColumnIndex(KEY_ID))
            action.actionType = c.getInt(c.getColumnIndex(KEY_ACTION_TYPE))
            val sInspection = c.getString(c.getColumnIndex(KEY_ACTION_INSPECTION))
            action.inspection = BaseApplication.gson.fromJson(sInspection, Inspection::class.java)
            c.close()
            return action
        }
        return null
    }

    fun updateAction(action: Action): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_ACTION_TYPE, action.actionType)
        val sInspection = BaseApplication.gson.toJson(action.inspection)
        values.put(KEY_ACTION_INSPECTION, sInspection)
        return db.update(
            TABLE_ACTION, values, "$KEY_ID = ?",
            arrayOf(action.id.toString())
        )
    }

    // This function returns the number of rows modified
    fun deleteAction(action: Action): Int {
        val db = this.writableDatabase
        val number = db.delete(
            TABLE_ACTION, "$KEY_ID = ?",
            arrayOf(action.id.toString())
        )
        db.close()
        return number
    }

    fun closeDB() {
        val db = this.readableDatabase
        if (db != null && db.isOpen) {
            db.close()
        }
    }
}
