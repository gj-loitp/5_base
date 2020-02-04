package vn.loitp.app.activity.database.sqlitemultitable

import android.os.Bundle
import android.util.TypedValue
import android.widget.TextView
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_sqlite_multi_table.*
import vn.loitp.app.R
import vn.loitp.app.activity.database.sqlitemultitable.helper.DatabaseHelper
import vn.loitp.app.activity.database.sqlitemultitable.model.Note
import vn.loitp.app.activity.database.sqlitemultitable.model.Tag
import vn.loitp.app.app.LApplication

//https://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
class SqliteMultiTableActivity : BaseFontActivity() {
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        test()
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_sqlite_multi_table
    }

    private fun test() {
        db = DatabaseHelper(applicationContext)

        // Creating tags
        val tag1 = Tag("Shopping" + System.currentTimeMillis())
        val tag2 = Tag("Important" + System.currentTimeMillis())
        val tag3 = Tag("Watchlist" + System.currentTimeMillis())
        val tag4 = Tag("Androidhive" + System.currentTimeMillis())

        // Inserting tags in db
        val tag1Id = db.createTag(tag1)
        val tag2Id = db.createTag(tag2)
        val tag3Id = db.createTag(tag3)
        val tag4Id = db.createTag(tag4)

        showMsg("tag1Id: $tag1Id")
        showMsg("tag2Id: $tag2Id")
        showMsg("tag3Id: $tag3Id")
        showMsg("tag4Id: $tag4Id")

        val tagList = db.getTagList()
        showMsg("tagList size: " + tagList.size)
        for (i in tagList.indices) {
            val t = tagList[i]
            showMsg("tagList -> " + i + " -> " + LApplication.gson.toJson(t))
        }

        // Creating note
        val note1 = Note("iPhone 5S", 0)
        val note2 = Note("Galaxy Note II", 0)
        val note3 = Note("Whiteboard", 0)

        val note4 = Note("Riddick", 0)
        val note5 = Note("Prisoners", 0)
        val note6 = Note("The Croods", 0)
        val note7 = Note("Insidious: Chapter 2", 0)

        val note8 = Note("Don't forget to call MOM", 0)
        val note9 = Note("Collect money from John", 0)

        val note10 = Note("Post new Article", 0)
        val note11 = Note("Take database backup", 0)

        // Inserting note in db
        // Inserting note under "Shopping" Tag
        val note1Id = db.createNote(note1, longArrayOf(tag1Id))
        val note2Id = db.createNote(note2, longArrayOf(tag1Id))
        val note3Id = db.createNote(note3, longArrayOf(tag1Id))

        // Inserting note under "Important" Tag
        val note8Id = db.createNote(note8, longArrayOf(tag2Id))
        val note9Id = db.createNote(note9, longArrayOf(tag2Id))

        // Inserting note under "Watchlist" Tag
        val note4Id = db.createNote(note4, longArrayOf(tag3Id))
        val note5Id = db.createNote(note5, longArrayOf(tag3Id))
        val note6Id = db.createNote(note6, longArrayOf(tag3Id))
        val note7Id = db.createNote(note7, longArrayOf(tag3Id))

        // Inserting note under "Androidhive" Tag
        val note10Id = db.createNote(note10, longArrayOf(tag4Id))
        val note11Id = db.createNote(note11, longArrayOf(tag4Id))

        // "Post new Article" - assigning this under "Important" Tag
        // Now this will have - "Androidhive" and "Important" Tags
        db.createNoteTag(note10Id, tag2Id)

        val noteCount = db.getNoteCount()
        showMsg("getNoteCount: $noteCount")

        val noteList = db.getNoteList()
        showMsg("noteList size: " + noteList.size)
        for (i in noteList.indices) {
            val td = noteList[i]
            showMsg(">noteList " + i + " -> " + LApplication.gson.toJson(td))
        }

        // Getting note under "Watchlist" tag name
        val tagsWatchList = db.getAllNoteByTag(tag3.tagName)
        for (i in tagsWatchList.indices) {
            val td = tagsWatchList[i]
            showMsg(">tagsWatchList " + i + " -> " + LApplication.gson.toJson(td))
        }

        // Deleting
        showMsg("Tag Count Before Deleting: " + db.getNoteCount())
        db.deleteNote(note8Id)
        showMsg("Tag Count After Deleting: " + db.getNoteCount())

        // Deleting all note under "Shopping" tag
        showMsg("Tag Count Before Deleting 'Shopping' note: " + db.getNoteCount())
        db.deleteTag(tag1, true)
        showMsg("Tag Count After Deleting 'Shopping' note: " + db.getNoteCount())

        // Updating tag name
        tag3.tagName = "Movies to watch"
        db.updateTag(tag3)

        // Don't forget to close database connection
        db.closeDB()
    }

    override fun onDestroy() {
        db.deleteAllDatabase()
        db.closeDB()
        super.onDestroy()
    }

    private fun showMsg(msg: String) {
        LLog.d(TAG, msg)
        val tv = TextView(activity)
        tv.text = msg
        LUIUtil.setTextSize(tv, TypedValue.COMPLEX_UNIT_DIP, 10)
        ll.addView(tv)
    }
}
