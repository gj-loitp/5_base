package vn.loitp.a.db.sqlite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseApplication.Companion.gson
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_db_sqlite.*
import vn.loitp.R

/**
 * Created by Loitp on 15.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

@LogTag("SqliteActivity")
@IsFullScreen(false)
class SqliteActivityFont : BaseActivityFont(), View.OnClickListener {

    private var databaseHandler: DatabaseHandler? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.a_db_sqlite
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            this.tvTitle?.text = SqliteActivityFont::class.java.simpleName
        }
        databaseHandler = DatabaseHandler(this)
        btAdd.setOnClickListener(this)
        btClearAll.setOnClickListener(this)
        btGetContactWithId.setOnClickListener(this)
        btGetContactListPage1.setOnClickListener(this)
        btAdd100.setOnClickListener(this)
        allContact
    }

    override fun onClick(v: View) {
        when (v) {
            btAdd -> addContact()
            btAdd100 -> add100Contact()
            btClearAll -> clearAllContact()
            btGetContactWithId -> getContactWithId(2)
            btGetContactListPage1 -> getContactListPage(1)
        }
    }

    private val allContact: Unit
        get() {
            val contactList = databaseHandler?.allContacts
            contactList?.forEach {
                addButton(it)
            }
        }

    @SuppressLint("SetTextI18n")
    private fun addButton(contact: Contact) {
        val button = Button(this)
        button.text = contact.id.toString() + " " + contact.name
        button.setOnClickListener {
            updateContact(contact = contact, button = button)
        }
        button.setOnLongClickListener {
            deleteContact(contact, button)
            true
        }
        ll.addView(button)
    }

    @SuppressLint("SetTextI18n")
    private fun addButton() {
        val button = Button(this)
        databaseHandler?.contactsCount?.let { id ->
            val contact = databaseHandler?.getContact(id)
            if (contact != null) {
                button.text = contact.id.toString() + " - " + contact.name
                button.setOnClickListener {
                    updateContact(contact, button)
                }
                button.setOnLongClickListener {
                    deleteContact(contact, button)
                    true
                }
                ll.addView(button)
            }
        }
    }

    private fun addContact() {
        val size = databaseHandler?.contactsCount
        size?.let {
            val contact = Contact()
            contact.name = "name " + (it + 1)
            contact.phoneNumber = "phone: " + (it + 1)
            databaseHandler?.addContact(contact)
            addButton()
        }
    }

    private fun add100Contact() {
        for (i in 0..99) {
            val size = databaseHandler?.contactsCount
            size?.let {
                val contact = Contact()
                contact.name = "name " + (it + 1)
                contact.phoneNumber = "phone: " + (it + 1)
                databaseHandler?.addContact(contact)
                addButton()
            }
        }
    }

    private fun clearAllContact() {
        ll.removeAllViews()
        databaseHandler?.clearAllContact()
        allContact
    }

    @Suppress("unused")
    private fun getContactWithId(id: Int) {
        val contact = databaseHandler?.getContact(id)
        if (contact == null) {
            showShortInformation("Contact with ID=$id not found", true)
        } else {
            showShortInformation(
                msg = "Found: " + contact.id + " " + contact.name + " " + contact.phoneNumber,
                isTopAnchor = true
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateContact(contact: Contact, button: Button) {
        contact.name = "Updated " + contact.name
        val result = databaseHandler?.updateContact(contact)
        logD("updateContact $result")
        button.text = contact.id.toString() + " " + contact.name
    }

    private fun deleteContact(contact: Contact, button: Button) {
        val result = databaseHandler?.deleteContact(contact)
        logD("deleteContact $result")
        ll.removeView(button)
    }

    @Suppress("unused")
    private fun getContactListPage(page: Int) {
        val contactList = databaseHandler?.getContactListWithPage(page, 5)
        showDialogMsg("getContactListPage page: $page ${gson.toJson(contactList)}")
    }
}
