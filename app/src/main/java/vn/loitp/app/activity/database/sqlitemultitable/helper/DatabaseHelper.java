package vn.loitp.app.activity.database.sqlitemultitable.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.core.utilities.LLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.loitp.app.activity.database.sqlitemultitable.model.Tag;
import vn.loitp.app.activity.database.sqlitemultitable.model.Note;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = DatabaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = DatabaseHelper.class.getName();

    // Table Names
    private static final String TABLE_TODO = "todos";
    private static final String TABLE_TAG = "tags";
    private static final String TABLE_TODO_TAG = "todo_tags";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // NOTES Table - column nmaes
    private static final String KEY_TODO = "todo";
    private static final String KEY_STATUS = "status";

    // TAGS Table - column names
    private static final String KEY_TAG_NAME = "tag_name";

    // NOTE_TAGS Table - column names
    private static final String KEY_TODO_ID = "todo_id";
    private static final String KEY_TAG_ID = "tag_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LLog.d(TAG, "onCreate");
        // Table Create Statements
        String CREATE_TABLE_TODO = "CREATE TABLE "
                + TABLE_TODO + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TODO
                + " TEXT," + KEY_STATUS + " INTEGER," + KEY_CREATED_AT
                + " DATETIME" + ")";
        db.execSQL(CREATE_TABLE_TODO);
        // Tag table create statement
        String CREATE_TABLE_TAG = "CREATE TABLE " + TABLE_TAG
                + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TAG_NAME + " TEXT,"
                + KEY_CREATED_AT + " DATETIME" + ")";
        db.execSQL(CREATE_TABLE_TAG);
        // todo_tag table create statement
        String CREATE_TABLE_TODO_TAG = "CREATE TABLE "
                + TABLE_TODO_TAG + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TODO_ID + " INTEGER," + KEY_TAG_ID + " INTEGER,"
                + KEY_CREATED_AT + " DATETIME" + ")";
        db.execSQL(CREATE_TABLE_TODO_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LLog.d(TAG, "onUpgrade oldVersion: " + oldVersion + ", newVersion: " + newVersion);
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_TAG);

        // create new tables
        onCreate(db);
    }

    public void deleteAllDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TODO);
        db.execSQL("DELETE FROM " + TABLE_TAG);
        db.execSQL("DELETE FROM " + TABLE_TODO_TAG);
        db.close();
    }

    // ------------------------ "todos" table methods ----------------//

    /**
     * Creating
     */
    public long createToDo(Note todo, long[] tagIdList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TODO, todo.getNote());
        values.put(KEY_STATUS, todo.getStatus());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long todoId = db.insert(TABLE_TODO, null, values);
        // insert tagId
        for (long tagId : tagIdList) {
            createTodoTag(todoId, tagId);
        }
        db.close();
        return todoId;
    }

    /**
     * get single todo
     */
    public Note getTodo(long todo_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TODO + " WHERE "
                + KEY_ID + " = " + todo_id;

        Log.e(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Note td = new Note();
        td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
        td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

        return td;
    }

    /**
     * getting all todos
     */
    public List<Note> getToDoList() {
        List<Note> todos = new ArrayList<Note>();
        String selectQuery = "SELECT  * FROM " + TABLE_TODO;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Note td = new Note();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
                td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }
        c.close();
        return todos;
    }

    /**
     * getting all todos under single tag
     */
    public List<Note> getAllToDosByTag(String tag_name) {
        List<Note> todos = new ArrayList<Note>();

        String selectQuery = "SELECT  * FROM " + TABLE_TODO + " td, " + TABLE_TAG + " tg, " + TABLE_TODO_TAG + " tt " +
                "WHERE tg." + KEY_TAG_NAME + " = '" + tag_name + "'" + " " +
                "AND tg." + KEY_ID + " = " + "tt." + KEY_TAG_ID +
                " AND td." + KEY_ID + " = " + "tt." + KEY_TODO_ID;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Note td = new Note();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
                td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
                todos.add(td);
            } while (c.moveToNext());
        }

        c.close();
        return todos;
    }

    /**
     * getting to do count
     */
    public int getToDoCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TODO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    /**
     * Updating a todo
     */
    public int updateToDo(Note todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TODO, todo.getNote());
        values.put(KEY_STATUS, todo.getStatus());

        // updating row
        return db.update(TABLE_TODO, values, KEY_ID + " = ?",
                new String[]{String.valueOf(todo.getId())});
    }

    public void deleteToDo(long todoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_ID + " = ?",
                new String[]{String.valueOf(todoId)});
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

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Tag t = new Tag();
                t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                t.setTagName(c.getString(c.getColumnIndex(KEY_TAG_NAME)));

                // adding to tags list
                tags.add(t);
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
     * return list todo deleted
     */
    public void deleteTag(Tag tag, boolean shouldDeleteAllTagTodos) {
        SQLiteDatabase db = this.getWritableDatabase();

        // before deleting tag
        // check if todos under this tag should also be deleted
        if (shouldDeleteAllTagTodos) {
            // get all todos under this tag
            List<Note> allTagToDos = getAllToDosByTag(tag.getTagName());

            // delete all todos
            for (Note todo : allTagToDos) {
                deleteToDo(todo.getId());
            }
        }

        // now delete the tag
        db.delete(TABLE_TAG, KEY_ID + " = ?",
                new String[]{String.valueOf(tag.getId())});
        db.close();
    }

    // ------------------------ "todo_tags" table methods ----------------//

    /**
     * Creating todo_tag
     */
    public long createTodoTag(long todoId, long tagId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TODO_ID, todoId);
        values.put(KEY_TAG_ID, tagId);
        values.put(KEY_CREATED_AT, getDateTime());

        long id = db.insert(TABLE_TODO_TAG, null, values);
        db.close();
        return id;
    }

    /**
     * Updating a todo tag
     */
    public int updateNoteTag(long id, long tag_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TAG_ID, tag_id);

        // updating row
        return db.update(TABLE_TODO, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    /**
     * Deleting a todo tag
     */
    public void deleteToDoTag(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_ID + " = ?",
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
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}