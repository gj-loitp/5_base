package vn.loitp.app.activity.database.sqlitemultitableadvance.helper

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
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_INSPECTION_ID + " TEXT,"
                + KEY_INSPECTION_CONTENT + " TEXT"
                + ")")
        db.execSQL(queryCreateTableInspection)
        // Action table create statement
        val queryCreateTableAction = ("CREATE TABLE " + TABLE_ACTION + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ACTION_TYPE + " INTEGER"
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


    /*private fun getDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    // ------------------------ "note" table methods ----------------//

    fun createNote(inspection: Inspection, tagIdList: LongArray): Long {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_INSPECTION_ID, inspection.note)
        values.put(KEY_INSPECTION_CONTENT, inspection.status)
        values.put(KEY_CREATED_AT, getDateTime())

        // insert row
        val noteId = db.insert(TABLE_INSPECTION, null, values)
        // insert tagId
        for (tagId in tagIdList) {
            createNoteTag(noteId, tagId)
        }
        db.close()
        return noteId
    }

    */
    /**
     * get single note
     *//*
    fun getNote(noteId: Long): Inspection? {
        val db = this.readableDatabase

        val selectQuery = ("SELECT  * FROM " + TABLE_INSPECTION + " WHERE "
                + KEY_ID + " = " + noteId)

        //LLog.d(TAG, selectQuery);

        val c = db.rawQuery(selectQuery, null)
        c?.moveToFirst() ?: return null

        val note = Inspection()
        note.id = c.getInt(c.getColumnIndex(KEY_ID))
        note.note = c.getString(c.getColumnIndex(KEY_INSPECTION_ID))
        note.createdAt = c.getString(c.getColumnIndex(KEY_CREATED_AT))

        c.close()
        return note
    }

    */
    /**
     * getting all note under single tag
     *//*
    fun getAllNoteByTag(tagName: String?): List<Inspection> {
        val noteList = ArrayList<Inspection>()

        val selectQuery = "SELECT  * FROM " + TABLE_INSPECTION + " td, " + TABLE_ACTION + " tg, " + TABLE_INSPECTION_ACTION + " tt " +
                "WHERE tg." + KEY_ACTION_TYPE + " = '" + tagName + "'"
        " AND tg." + KEY_ID + " = " + "tt." + KEY_ACTION_ID_FK +
                " AND td." + KEY_ID + " = " + "tt." + KEY_INSPECTION_ID_FK

        //LLog.d(TAG, selectQuery);

        val db = this.readableDatabase
        val c = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                val td = Inspection()
                td.id = c.getInt(c.getColumnIndex(KEY_ID))
                td.note = c.getString(c.getColumnIndex(KEY_INSPECTION_ID))
                td.createdAt = c.getString(c.getColumnIndex(KEY_CREATED_AT))
                noteList.add(td)
            } while (c.moveToNext())
        }

        c.close()
        return noteList
    }

    */
    /**
     * Updating a inspection
     *//*
    fun updateNote(inspection: Inspection): Int {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_INSPECTION_ID, inspection.note)
        values.put(KEY_INSPECTION_CONTENT, inspection.status)

        // updating row
        return db.update(TABLE_INSPECTION, values, "$KEY_ID = ?",
                arrayOf(inspection.id.toString()))
    }

    fun deleteNote(noteId: Long) {
        val db = this.writableDatabase
        db.delete(TABLE_INSPECTION, "$KEY_ID = ?",
                arrayOf(noteId.toString()))
    }

    // ------------------------ "tags" table methods ----------------//

    */
    /**
     * Creating tag
     * Return id of record
     *//*
    fun createTag(tag: Action): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_ACTION_TYPE, tag.tagName)
        values.put(KEY_CREATED_AT, getDateTime())
        return db.insert(TABLE_ACTION, null, values)
    }

    */
    /**
     * Updating a tag
     *//*
    fun updateTag(tag: Action): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_ACTION_TYPE, tag.tagName)
        // updating row
        return db.update(TABLE_ACTION, values, "$KEY_ID = ?",
                arrayOf(tag.id.toString()))
    }

    */
    /**
     * Deleting a tag
     * return list note deleted
     *//*
    fun deleteTag(tag: Action, shouldDeleteAllTagNotes: Boolean) {
        val db = this.writableDatabase

        // before deleting tag
        // check if todos under this tag should also be deleted
        if (shouldDeleteAllTagNotes) {
            // get all notes under this tag
            val allTagNotes = getAllNoteByTag(tag.tagName)

            // delete all todos
            for (todo in allTagNotes) {
                deleteNote(todo.id.toLong())
            }
        }

        // now delete the tag
        db.delete(TABLE_ACTION, "$KEY_ID = ?",
                arrayOf(tag.id.toString()))
        db.close()
    }

    // ------------------------ "todo_tags" table methods ----------------//

    fun createNoteTag(noteId: Long, tagId: Long): Long {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_INSPECTION_ID_FK, noteId)
        values.put(KEY_ACTION_ID_FK, tagId)
        values.put(KEY_CREATED_AT, getDateTime())

        val id = db.insert(TABLE_INSPECTION_ACTION, null, values)
        db.close()
        return id
    }

    fun updateNoteTag(id: Long, tagId: Long): Int {
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