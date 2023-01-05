package vn.loitp.a.db.realm

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LUIUtil
import io.realm.Realm
import kotlinx.android.synthetic.main.a_db_realm.*
import vn.loitp.R
import vn.loitp.a.demo.ebookWithRealm.EbookWithRealmActivityFont

@LogTag("RealmActivity")
@IsFullScreen(false)
class RealmActivityFont : BaseActivityFont() {
    private lateinit var mRealm: Realm

    override fun setLayoutResourceId(): Int {
        return R.layout.a_db_realm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mRealm = RealmController.with(application).realm

        // refresh the realm instance
        RealmController.with(application).refresh()

        getAllMyBook()
        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = RealmActivityFont::class.java.simpleName
        }
        btRealm.setSafeOnClickListener {
            val intent = Intent(this, EbookWithRealmActivityFont::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btAdd.setSafeOnClickListener {
            addMyBook()
        }
        btClearAll.setSafeOnClickListener {
            clearUI()
            RealmController.getInstance(this).clearAllMyBook()
            getAllMyBook()
        }
        btSearchById.setSafeOnClickListener {
            searchById()
        }
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

    @SuppressLint("SetTextI18n")
    private fun addViewMyBook(myBook: MyBook) {
        val button = Button(this)
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
        val myBookList = RealmController.getInstance(this).myBookListSortByID
        printUI(myBookList)
        printTotalSize()
    }

    private fun removeMyBook(myBook: MyBook, button: Button) {
        mRealm.beginTransaction()
        val mb = RealmController.getInstance(this).getMyBook(myBook)
        if (!mb.isEmpty()) {
            for (i in mb.indices.reversed()) {
                mb[i].removeFromRealm()
            }
        }
        mRealm.commitTransaction()
        ll.removeView(button)
        printTotalSize()
    }

    @SuppressLint("SetTextI18n")
    private fun updateMyBook(myBook: MyBook, button: Button) {
        mRealm.beginTransaction()
        val mb = RealmController.getInstance(this).getMyBook(myBook.id)
        mb.title = "Title ${System.currentTimeMillis()}"
        mb.author = "Author ${System.currentTimeMillis()}"
        mRealm.commitTransaction()
        button.text = "${mb.title} - ${mb.author}"
    }

    private fun printTotalSize() {
        showShortInformation("Total size: " + RealmController.getInstance(this).myBookList.size)
    }

    private fun showInputDialog(ipb: InputCallback) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Title")

        val input = EditText(this)
        // input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, _ ->
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
                    logD("searchById $it")
                    try {
                        val id: Long = it.toLong()
                        val mb = RealmController.getInstance(this@RealmActivityFont).getMyBook(id)
                        showShortInformation("searchById -> ${mb.title}")
                    } catch (e: Exception) {
                        showShortError("searchById $e")
                    }
                }
            }
        })
    }
}
