package vn.loitp.app.activity.database.sqlitemultitable;

import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.database.sqlitemultitable.helper.DatabaseHelper;
import vn.loitp.app.activity.database.sqlitemultitable.model.Tag;
import vn.loitp.app.activity.database.sqlitemultitable.model.Note;
import vn.loitp.app.app.LApplication;

//https://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
public class SqliteMultiTableActivity extends BaseFontActivity {

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        test();
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_sqlite_multi_table;
    }

    private void test() {
        db = new DatabaseHelper(getApplicationContext());

        // Creating tags
        Tag tag1 = new Tag("Shopping" + System.currentTimeMillis());
        Tag tag2 = new Tag("Important" + System.currentTimeMillis());
        Tag tag3 = new Tag("Watchlist" + System.currentTimeMillis());
        Tag tag4 = new Tag("Androidhive" + System.currentTimeMillis());

        // Inserting tags in db
        long tag1Id = db.createTag(tag1);
        long tag2Id = db.createTag(tag2);
        long tag3Id = db.createTag(tag3);
        long tag4Id = db.createTag(tag4);

        LLog.d(TAG, "tag1Id: " + tag1Id);
        LLog.d(TAG, "tag2Id: " + tag2Id);
        LLog.d(TAG, "tag3Id: " + tag3Id);
        LLog.d(TAG, "tag4Id: " + tag4Id);

        List<Tag> tagList = db.getTagList();
        LLog.d(TAG, "tagList size: " + tagList.size());
        for (int i = 0; i < tagList.size(); i++) {
            Tag t = tagList.get(i);
            LLog.d(TAG, "tagList -> " + i + " -> " + LApplication.Companion.getGson().toJson(t));
        }

        // Creating ToDos
        Note todo1 = new Note("iPhone 5S", 0);
        Note todo2 = new Note("Galaxy Note II", 0);
        Note todo3 = new Note("Whiteboard", 0);

        Note todo4 = new Note("Riddick", 0);
        Note todo5 = new Note("Prisoners", 0);
        Note todo6 = new Note("The Croods", 0);
        Note todo7 = new Note("Insidious: Chapter 2", 0);

        Note todo8 = new Note("Don't forget to call MOM", 0);
        Note todo9 = new Note("Collect money from John", 0);

        Note todo10 = new Note("Post new Article", 0);
        Note todo11 = new Note("Take database backup", 0);

        // Inserting todos in db
        // Inserting todos under "Shopping" Tag
        long todo1Id = db.createToDo(todo1, new long[]{tag1Id});
        long todo2Id = db.createToDo(todo2, new long[]{tag1Id});
        long todo3Id = db.createToDo(todo3, new long[]{tag1Id});

        // Inserting todos under "Important" Tag
        long todo8Id = db.createToDo(todo8, new long[]{tag2Id});
        long todo9Id = db.createToDo(todo9, new long[]{tag2Id});

        // Inserting todos under "Watchlist" Tag
        long todo4Id = db.createToDo(todo4, new long[]{tag3Id});
        long todo5Id = db.createToDo(todo5, new long[]{tag3Id});
        long todo6Id = db.createToDo(todo6, new long[]{tag3Id});
        long todo7Id = db.createToDo(todo7, new long[]{tag3Id});

        // Inserting todos under "Androidhive" Tag
        long todo10Id = db.createToDo(todo10, new long[]{tag4Id});
        long todo11Id = db.createToDo(todo11, new long[]{tag4Id});

        // "Post new Article" - assigning this under "Important" Tag
        // Now this will have - "Androidhive" and "Important" Tags
        db.createTodoTag(todo10Id, tag2Id);

        // Getting all Todos
        int toDoCount = db.getToDoCount();
        LLog.d(TAG, "toDoCount getToDoCount: " + toDoCount);

        List<Note> todoList = db.getToDoList();
        LLog.d(TAG, "todoList size: " + todoList.size());
        for (int i = 0; i < todoList.size(); i++) {
            Note td = todoList.get(i);
            LLog.d(TAG, ">todoList " + i + " -> " + LApplication.Companion.getGson().toJson(td));
        }

        // Getting todos under "Watchlist" tag name
        List<Note> tagsWatchList = db.getAllToDosByTag(tag3.getTagName());
        for (int i = 0; i < tagsWatchList.size(); i++) {
            Note td = tagsWatchList.get(i);
            LLog.d(TAG, ">tagsWatchList " + i + " -> " + LApplication.Companion.getGson().toJson(td));
        }

        // Deleting
        LLog.d(TAG, "Tag Count Before Deleting: " + db.getToDoCount());
        db.deleteToDo(todo8Id);
        LLog.d(TAG, "Tag Count After Deleting: " + db.getToDoCount());

        // Deleting all Todos under "Shopping" tag
        LLog.d(TAG, "Tag Count Before Deleting 'Shopping' Todos: " + db.getToDoCount());
        db.deleteTag(tag1, true);
        LLog.d(TAG, "Tag Count After Deleting 'Shopping' Todos: " + db.getToDoCount());

        // Updating tag name
        tag3.setTagName("Movies to watch");
        db.updateTag(tag3);

        // Don't forget to close database connection
        db.closeDB();
    }

    @Override
    protected void onDestroy() {
        db.deleteAllDatabase();
        db.closeDB();
        super.onDestroy();
    }
}
