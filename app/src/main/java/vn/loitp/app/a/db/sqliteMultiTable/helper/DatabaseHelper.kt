package vn.loitp.app.a.db.sqliteMultiTable.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.loitp.core.utils.AppUtils
import vn.loitp.app.a.db.sqliteMultiTable.md.Note
import vn.loitp.app.a.db.sqliteMultiTable.md.Tag
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Loitp on 15.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // Database Version
        private val DATABASE_VERSION = AppUtils.appVersionCode

        // Database Name
        private val DATABASE_NAME = DatabaseHelper::class.java.name

        // Table Names
        private const val TABLE_NOTE = "notes"
        private const val TABLE_TAG = "tags"
        private const val TABLE_NOTE_TAG = "note_tags"

        // Common column names
        private const val KEY_ID = "id"
        private const val KEY_CREATED_AT = "created_at"

        // NOTES Table - column nmaes
        private const val KEY_NOTE = "note"
        private const val KEY_STATUS = "status"

        // TAGS Table - column names
        private const val KEY_TAG_NAME = "tag_name"

        // NOTE_TAGS Table - column names
        private const val KEY_NOTE_ID = "note_id"
        private const val KEY_TAG_ID = "tag_id"
    }

    /**
     * getting all note
     */
    // looping through all rows and adding to list
    fun getNoteList(): List<Note> {
        val noteList = ArrayList<Note>()
        val selectQuery = "SELECT  * FROM $TABLE_NOTE"

        val db = this.readableDatabase
        val c = db.rawQuery(selectQuery, null)
        if (c.moveToFirst()) {
            do {
                val note = Note()

                getColumnIndexInt(c, KEY_ID)?.let {
                    note.id = it
                }
                getColumnIndexString(c, KEY_NOTE)?.let {
                    note.note = it
                }
                getColumnIndexString(c, KEY_CREATED_AT)?.let {
                    note.createdAt = it
                }
                noteList.add(note)
            } while (c.moveToNext())
        }
        c.close()
        return noteList
    }

    /**
     * getting note list count
     */
    fun getNoteCount(): Int {
        val countQuery = "SELECT  * FROM $TABLE_NOTE"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)
        val count = cursor.count
        cursor.close()
        return count
    }

    /**
     * getting all tags
     */
    fun getTagList(): List<Tag> {
        val tagList = ArrayList<Tag>()
        val selectQuery = "SELECT  * FROM $TABLE_TAG"

        val db = this.readableDatabase
        val c = db.rawQuery(selectQuery, null)
        if (c.moveToFirst()) {
            do {
                val tag = Tag()
                getColumnIndexInt(c, KEY_ID)?.let {
                    tag.id = it
                }
                getColumnIndexString(c, KEY_TAG_NAME)?.let {
                    tag.tagName = it
                }
                tagList.add(tag)
            } while (c.moveToNext())
        }
        c.close()
        return tagList
    }

    @Suppress("unused")
    private fun getColumnIndexInt(cursor: Cursor, key: String): Int? {
        val i = cursor.getColumnIndex(key)
        if (i >= 0) {
            return cursor.getInt(i)
        }
        return null
    }

    private fun getColumnIndexString(cursor: Cursor, key: String): String? {
        val i = cursor.getColumnIndex(key)
        if (i >= 0) {
            return cursor.getString(i)
        }
        return null
    }

    /**
     * get datetime
     */
    private fun getDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Table Create Statements
        val createTableToDo = (
                "CREATE TABLE " + TABLE_NOTE + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY," +
                        KEY_NOTE + " TEXT," +
                        KEY_STATUS + " INTEGER," +
                        KEY_CREATED_AT + " DATETIME" +
                        ")"
                )
        db.execSQL(createTableToDo)
        // Tag table create statement
        val createTableTag = (
                "CREATE TABLE " + TABLE_TAG + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY," +
                        KEY_TAG_NAME + " TEXT," +
                        KEY_CREATED_AT + " DATETIME" +
                        ")"
                )
        db.execSQL(createTableTag)
        // todo_tag table create statement
        val createTableToDoTag = (
                "CREATE TABLE " + TABLE_NOTE_TAG + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY," +
                        KEY_NOTE_ID + " INTEGER," +
                        KEY_TAG_ID + " INTEGER," +
                        KEY_CREATED_AT + " DATETIME" +
                        ")"
                )
        db.execSQL(createTableToDoTag)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NOTE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TAG")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NOTE_TAG")

        // create new tables
        onCreate(db)
    }

    fun deleteAllDatabase() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NOTE")
        db.execSQL("DELETE FROM $TABLE_TAG")
        db.execSQL("DELETE FROM $TABLE_NOTE_TAG")
        db.close()
    }

    // ------------------------ "note" table methods ----------------//

    fun createNote(note: Note, tagIdList: LongArray): Long {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_NOTE, note.note)
        values.put(KEY_STATUS, note.status)
        values.put(KEY_CREATED_AT, getDateTime())

        // insert row
        val noteId = db.insert(TABLE_NOTE, null, values)
        // insert tagId
        for (tagId in tagIdList) {
            createNoteTag(noteId, tagId)
        }
        db.close()
        return noteId
    }

    /**
     * get single note
     */
    @Suppress("unused")
    fun getNote(noteId: Long): Note? {
        val db = this.readableDatabase

        val selectQuery = (
                "SELECT  * FROM " + TABLE_NOTE + " WHERE " +
                        KEY_ID + " = " + noteId
                )

        val c = db.rawQuery(selectQuery, null)
        c?.moveToFirst() ?: return null

        val note = Note()
        getColumnIndexInt(c, KEY_ID)?.let {
            note.id = it
        }
        getColumnIndexString(c, KEY_NOTE)?.let {
            note.note = it
        }
        getColumnIndexString(c, KEY_CREATED_AT)?.let {
            note.createdAt = it
        }

        c.close()
        return note
    }

    /**
     * getting all note under single tag
     */
    fun getAllNoteByTag(tagName: String?): List<Note> {
        val noteList = ArrayList<Note>()

        val selectQuery =
            "SELECT  * FROM " + TABLE_NOTE + " td, " + TABLE_TAG + " tg, " + TABLE_NOTE_TAG + " tt " +
                    "WHERE tg." + KEY_TAG_NAME + " = '" + tagName + "'"
        " AND tg." + KEY_ID + " = " + "tt." + KEY_TAG_ID +
                " AND td." + KEY_ID + " = " + "tt." + KEY_NOTE_ID

        val db = this.readableDatabase
        val c = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                val td = Note()
                getColumnIndexInt(c, KEY_ID)?.let {
                    td.id = it
                }
                getColumnIndexString(c, KEY_NOTE)?.let {
                    td.note = it
                }
                getColumnIndexString(c, KEY_CREATED_AT)?.let {
                    td.createdAt = it
                }
                noteList.add(td)
            } while (c.moveToNext())
        }

        c.close()
        return noteList
    }

    /**
     * Updating a note
     */
    @Suppress("unused")
    fun updateNote(note: Note): Int {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_NOTE, note.note)
        values.put(KEY_STATUS, note.status)

        // updating row
        return db.update(
            TABLE_NOTE, values, "$KEY_ID = ?",
            arrayOf(note.id.toString())
        )
    }

    fun deleteNote(noteId: Long) {
        val db = this.writableDatabase
        db.delete(
            TABLE_NOTE, "$KEY_ID = ?",
            arrayOf(noteId.toString())
        )
    }

    // ------------------------ "tags" table methods ----------------//

    /**
     * Creating tag
     * Return id of record
     */
    fun createTag(tag: Tag): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TAG_NAME, tag.tagName)
        values.put(KEY_CREATED_AT, getDateTime())
        return db.insert(TABLE_TAG, null, values)
    }

    /**
     * Updating a tag
     */
    fun updateTag(tag: Tag): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TAG_NAME, tag.tagName)
        // updating row
        return db.update(
            TABLE_TAG, values, "$KEY_ID = ?",
            arrayOf(tag.id.toString())
        )
    }

    /**
     * Deleting a tag
     * return list note deleted
     */
    fun deleteTag(tag: Tag, shouldDeleteAllTagNotes: Boolean) {
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
        db.delete(
            TABLE_TAG, "$KEY_ID = ?",
            arrayOf(tag.id.toString())
        )
        db.close()
    }

    // ------------------------ "todo_tags" table methods ----------------//

    fun createNoteTag(noteId: Long, tagId: Long): Long {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_NOTE_ID, noteId)
        values.put(KEY_TAG_ID, tagId)
        values.put(KEY_CREATED_AT, getDateTime())

        val id = db.insert(TABLE_NOTE_TAG, null, values)
        db.close()
        return id
    }

    @Suppress("unused")
    fun updateNoteTag(id: Long, tagId: Long): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TAG_ID, tagId)
        // updating row
        return db.update(
            TABLE_NOTE, values, "$KEY_ID = ?",
            arrayOf(id.toString())
        )
    }

    @Suppress("unused")
    fun deleteNoteTag(id: Long) {
        val db = this.writableDatabase
        db.delete(
            TABLE_NOTE, "$KEY_ID = ?",
            arrayOf(id.toString())
        )
    }

    // closing database
    fun closeDB() {
        val db = this.readableDatabase
        if (db != null && db.isOpen) {
            db.close()
        }
    }
}
