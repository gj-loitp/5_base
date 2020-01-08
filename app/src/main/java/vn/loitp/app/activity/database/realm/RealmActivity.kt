package vn.loitp.app.activity.database.realm

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LLog
import com.views.LToast
import com.views.setSafeOnClickListener
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

        btRealm.setSafeOnClickListener {
            val intent = Intent(activity, EbookWithRealmActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        btAdd.setSafeOnClickListener {
            addMyBook()
        }
        btClearAll.setSafeOnClickListener {
            clearUI()
            RealmController.getInstance(activity).clearAllMyBook()
            getAllMyBook()
        }
        btSearchById.setSafeOnClickListener {
            searchById()
        }
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
        val myBookList = RealmController.getInstance(activity).myBookListSortByID
        printUI(myBookList)
        printTotalSize()
    }

    private fun removeMyBook(myBook: MyBook, button: Button) {
        mRealm.beginTransaction()
        val mb = RealmController.getInstance(activity).getMyBook(myBook)
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
        val mb = RealmController.getInstance(activity).getMyBook(myBook.id)
        mb.title = "Title ${System.currentTimeMillis()}"
        mb.author = "Author ${System.currentTimeMillis()}"
        mRealm.commitTransaction()
        button.text = "${mb.title} - ${mb.author}"
    }

    private fun printTotalSize() {
        LToast.showShort(activity, "Total size: " + RealmController.getInstance(activity).myBookList.size)
    }

    private fun showInputDialog(ipb: InputCallback) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Title")

        val input = EditText(activity)
        //input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, which ->
            val text = input.text.toString()
            ipb.onText(text)
            dialog.cancel()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun searchById() {
        showInputDialog(object : InputCallback {
            override fun onText(text: String?) {
                text?.let {
                    LLog.d(TAG, "searchById $it")
                    try {
                        val id: Long = it.toLong()
                        val mb = RealmController.getInstance(activity).getMyBook(id)
                        LToast.showLong(activity, "searchById -> ${mb.title}")
                    } catch (e: Exception) {
                        LToast.showShort(activity, "searchById $e")
                    }
                }
            }
        })
    }
}
