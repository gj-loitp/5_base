package vn.loitp.app.activity.database.sqliteencryption

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import kotlinx.android.synthetic.main.activity_sqlite_encryption.*
import loitp.basemaster.R
import vn.loitp.app.activity.database.sqlite.Contact
import vn.loitp.app.activity.database.sqlite.DatabaseHandler

class SqliteEncryptionActivity : BaseFontActivity(), View.OnClickListener {
    private lateinit var db: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DatabaseHandler(this)
        btAdd.setOnClickListener(this)
        btClearAll.setOnClickListener(this)
        btGetContactWithId.setOnClickListener(this)

        //getAllContact();
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_sqlite_encryption
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btAdd -> {
                //addContact();
            }
            R.id.btClearAll -> {
                //clearAllContact();
            }
            R.id.btGetContactWithId -> {
                //getContactWithId(2);
            }
        }
    }

    private fun getAllContact() {
        val contactList = db.allContacts
        for (contact in contactList) {
            addButton(contact)
        }
    }

    private fun addButton(contact: Contact) {
        val button = Button(activity)
        button.text = contact.id.toString() + " " + contact.name
        button.setOnClickListener { v ->
            //LLog.d(TAG, "onClick " + button.getText().toString());
            updateContact(contact, button)
        }
        button.setOnLongClickListener { v ->
            deleteContact(contact, button)
            true
        }
        ll.addView(button)
    }

    private fun addButton() {
        val button = Button(activity)
        val contact = db.getContact(db.contactsCount)
        if (contact != null) {
            button.text = contact.id.toString() + " - " + contact.name
            button.setOnClickListener { v ->
                //LLog.d(TAG, "onClick " + button.getText().toString());
                updateContact(contact, button)
            }
            button.setOnLongClickListener { v ->
                deleteContact(contact, button)
                true
            }
            ll.addView(button)
        }
    }

    private fun addContact() {
        val size = db.contactsCount
        LLog.d(TAG, "size: $size")
        val contact = Contact()
        contact.name = "name " + (size + 1)
        contact.phoneNumber = "phone: " + (size + 1)
        db.addContact(contact)
        addButton()
    }

    private fun clearAllContact() {
        LLog.d(TAG, "clearAllContact")
        ll.removeAllViews()
        db.clearAllContact()
        getAllContact()
    }

    private fun getContactWithId(id: Int) {
        val contact = db.getContact(id)
        if (contact == null) {
            showShort("Contact with ID=$id not found")
        } else {
            showShort("Found: " + contact.id + " " + contact.name + " " + contact.phoneNumber)
        }
    }

    private fun updateContact(contact: Contact, button: Button) {
        contact.name = "Updated " + contact.name
        val result = db.updateContact(contact)
        LLog.d(TAG, "updateContact result $result")
        button.text = contact.id.toString() + " " + contact.name
    }

    private fun deleteContact(contact: Contact?, button: Button) {
        val result = db.deleteContact(contact)
        LLog.d(TAG, "deleteContact result $result")
        ll.removeView(button)
    }
}
