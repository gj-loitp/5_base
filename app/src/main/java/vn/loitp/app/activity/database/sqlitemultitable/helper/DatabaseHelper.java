package vn.loitp.app.activity.database.sqlitemultitable.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.core.utilities.LLog;
import com.utils.util.AppUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.loitp.app.activity.database.sqlitemultitable.model.Note;
import vn.loitp.app.activity.database.sqlitemultitable.model.Tag;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = DatabaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = AppUtils.getAppVersionCode();

    // Database Name
    private static final String DATABASE_NAME = DatabaseHelper.class.getName();

    // Table Names
    private static final String TABLE_NOTE = "notes";
    private static final String TABLE_TAG = "tags";
    private static final String TABLE_NOTE_TAG = "note_tags";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // NOTES Table - column nmaes
    private static final String KEY_NOTE = "note";
    private static final String KEY_STATUS = "status";

    // TAGS Table - column names
    private static final String KEY_TAG_NAME = "tag_name";

    // NOTE_TAGS Table - column names
    private static final String KEY_NOTE_ID = "note_id";
    private static final String KEY_TAG_ID = "tag_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LLog.d(TAG, "onCreate");
        // Table Create Statements
        String CREATE_TABLE_TODO = "CREATE TABLE " + TABLE_NOTE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NOTE + " TEXT,"
                + KEY_STATUS + " INTEGER,"
                + KEY_CREATED_AT + " DATETIME"
                + ")";
        db.execSQL(CREATE_TABLE_TODO);
        // Tag table create statement
        String CREATE_TABLE_TAG = "CREATE TABLE " + TABLE_TAG + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TAG_NAME + " TEXT,"
                + KEY_CREATED_AT + " DATETIME"
                + ")";
        db.execSQL(CREATE_TABLE_TAG);
        // todo_tag table create statement
        String CREATE_TABLE_TODO_TAG = "CREATE TABLE " + TABLE_NOTE_TAG + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NOTE_ID + " INTEGER,"
                + KEY_TAG_ID + " INTEGER,"
                + KEY_CREATED_AT + " DATETIME"
                + ")";
        db.execSQL(CREATE_TABLE_TODO_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LLog.d(TAG, "onUpgrade oldVersion: " + oldVersion + ", newVersion: " + newVersion);
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE_TAG);

        // create new tables
        onCreate(db);
    }

    public void deleteAllDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NOTE);
        db.execSQL("DELETE FROM " + TABLE_TAG);
        db.execSQL("DELETE FROM " + TABLE_NOTE_TAG);
        db.close();
    }

    // ------------------------ "note" table methods ----------------//

    public long createNote(Note note, long[] tagIdList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE, note.getNote());
        values.put(KEY_STATUS, note.getStatus());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long noteId = db.insert(TABLE_NOTE, null, values);
        // insert tagId
        for (long tagId : tagIdList) {
            createNoteTag(noteId, tagId);
        }
        db.close();
        return noteId;
    }

    /**
     * get single note
     */
    public Note getNote(long noteId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NOTE + " WHERE "
                + KEY_ID + " = " + noteId;

        //LLog.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
        } else {
            return null;
        }

        Note note = new Note();
        note.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        note.setNote((c.getString(c.getColumnIndex(KEY_NOTE))));
        note.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

        c.close();
        return note;
    }

    /**
     * getting all note
     */
    public List<Note> getNoteList() {
        List<Note> noteList = new ArrayList<Note>();
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;

        //LLog.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                note.setNote((c.getString(c.getColumnIndex(KEY_NOTE))));
                note.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
                noteList.add(note);
            } while (c.moveToNext());
        }
        c.close();
        return noteList;
    }

    /**
     * getting all note under single tag
     */
    public List<Note> getAllNoteByTag(String tagName) {
        List<Note> noteList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NOTE + " td, " + TABLE_TAG + " tg, " + TABLE_NOTE_TAG + " tt " +
                "WHERE tg." + KEY_TAG_NAME + " = '" + tagName + "'" + " " +
                "AND tg." + KEY_ID + " = " + "tt." + KEY_TAG_ID +
                " AND td." + KEY_ID + " = " + "tt." + KEY_NOTE_ID;

        //LLog.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Note td = new Note();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                td.setNote((c.getString(c.getColumnIndex(KEY_NOTE))));
                td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
                noteList.add(td);
            } while (c.moveToNext());
        }

        c.close();
        return noteList;
    }

    /**
     * getting note list count
     */
    public int getNoteCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    /**
     * Updating a note
     */
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE, note.getNote());
        values.put(KEY_STATUS, note.getStatus());

        // updating row
        return db.update(TABLE_NOTE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(long noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, KEY_ID + " = ?",
                new String[]{String.valueOf(noteId)});
    }

    // ------------------------ "tags" table methods ----------------//

    /**
     * Creating tag
     * Return id of record
     */
    public long createTag(Tag tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TAG_NAME, tag.getTagName());
        values.put(KEY_CREATED_AT, getDateTime());
        return db.insert(TABLE_TAG, null, values);
    }

    /**
     * getting all tags
     */
    public List<Tag> getTagList() {
        List<Tag> tags = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TAG;

        //LLog.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Tag tag = new Tag();
                tag.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                tag.setTagName(c.getString(c.getColumnIndex(KEY_TAG_NAME)));

                // adding to tags list
                tags.add(tag);
            } while (c.moveToNext());
        }
        c.close();
        return tags;
    }

    /**
     * Updating a tag
     */
    public int updateTag(Tag tag) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TAG_NAME, tag.getTagName());

        // updating row
        return db.update(TABLE_TAG, values, KEY_ID + " = ?",
                new String[]{String.valueOf(tag.getId())});
    }

    /**
     * Deleting a tag
     * return list note deleted
     */
    public void deleteTag(Tag tag, boolean shouldDeleteAllTagNotes) {
        SQLiteDatabase db = this.getWritableDatabase();

        // before deleting tag
        // check if todos under this tag should also be deleted
        if (shouldDeleteAllTagNotes) {
            // get all notes under this tag
            List<Note> allTagNotes = getAllNoteByTag(tag.getTagName());

            // delete all todos
            for (Note todo : allTagNotes) {
                deleteNote(todo.getId());
            }
        }

        // now delete the tag
        db.delete(TABLE_TAG, KEY_ID + " = ?",
                new String[]{String.valueOf(tag.getId())});
        db.close();
    }

    // ------------------------ "todo_tags" table methods ----------------//

    public long createNoteTag(long noteId, long tagId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE_ID, noteId);
        values.put(KEY_TAG_ID, tagId);
        values.put(KEY_CREATED_AT, getDateTime());

        long id = db.insert(TABLE_NOTE_TAG, null, values);
        db.close();
        return id;
    }

    public int updateNoteTag(long id, long tagId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TAG_ID, tagId);

        // updating row
        return db.update(TABLE_NOTE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public void deleteToDoTag(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * get datetime
     */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}