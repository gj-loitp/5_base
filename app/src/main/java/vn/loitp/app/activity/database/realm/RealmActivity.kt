package vn.loitp.app.activity.database.realm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.views.LToast
import com.views.OnSingleClickListener
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_realm.*
import loitp.basemaster.R
import vn.loitp.app.activity.demo.ebookwithrealm.EbookWithRealmActivity

class RealmActivity : BaseFontActivity() {
    private lateinit var mRealm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRealm = RealmController.with(application).realm

        // refresh the realm instance
        RealmController.with(application).refresh()

        getAllMyBook()

        btRealm.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                val intent = Intent(activity, EbookWithRealmActivity::class.java)
                startActivity(intent)
                LActivityUtil.tranIn(activity)
            }
        })
        btAdd.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                addMyBook()
            }
        })
        btClearAll.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                clearUI()
                RealmController.getInstance().clearAllMyBook()
                getAllMyBook()
            }
        })
        btSearch.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                //TODO
            }
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_realm
    }

    private fun addMyBook() {
        mRealm.beginTransaction()

        val myBook = MyBook()
        myBook.id = System.currentTimeMillis()
        myBook.title = "Title ${System.currentTimeMillis()}"
        myBook.author = "Author ${System.currentTimeMillis()}"

        mRealm.copyToRealm(myBook)
        mRealm.commitTransaction()

        addViewMyBook(myBook)
        printTotalSize()
    }

    private fun addViewMyBook(myBook: MyBook) {
        val button = Button(activity)
        button.text = "${myBook.title} - ${myBook.author}"
        button.textSize = 10f
        button.isAllCaps = false
        button.setOnClickListener { updateMyBook(myBook, button) }
        button.setOnLongClickListener {
            removeMyBook(myBook, button)
            true
        }
        ll.addView(button)
    }

    private fun clearUI() {
        val viewList = ll.touchables
        for (view in viewList) {
            if (view is Button) {
                ll.removeView(view)
            }
        }
    }

    private fun printUI(myBookList: List<MyBook>) {
        for (mb in myBookList) {
            addViewMyBook(mb)
        }
    }

    private fun getAllMyBook() {
        val myBookList = RealmController.getInstance().myBookListSortByID
        printUI(myBookList)
        printTotalSize()
    }

    private fun removeMyBook(myBook: MyBook, button: Button) {
        mRealm.beginTransaction()
        val mb = RealmController.getInstance().getMyBook(myBook)
        if (!mb.isEmpty()) {
            for (i in mb.indices.reversed()) {
                mb[i].removeFromRealm()
            }
        }
        mRealm.commitTransaction()
        ll.removeView(button)
        printTotalSize()
    }

    private fun updateMyBook(myBook: MyBook, button: Button) {
        mRealm.beginTransaction()
        val mb = RealmController.getInstance().getMyBook(myBook.id)
        mb.title = "Title ${System.currentTimeMillis()}"
        mb.author = "Author ${System.currentTimeMillis()}"
        mRealm.commitTransaction()
        button.text = "${mb.title} - ${mb.author}"
    }

    private fun printTotalSize() {
        LToast.showShort(activity, "Total size: " + RealmController.getInstance().myBookList.size)
    }
}
