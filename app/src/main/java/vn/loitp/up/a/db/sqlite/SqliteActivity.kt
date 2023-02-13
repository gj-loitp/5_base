package vn.loitp.up.a.db.sqlite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseApplication.Companion.gson
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ADbSqliteBinding

/**
 * Created by Loitp on 15.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

@LogTag("SqliteActivity")
@IsFullScreen(false)
class SqliteActivity : BaseActivityFont(), View.OnClickListener {

    private var databaseHandler: DatabaseHandler? = null
    private lateinit var binding: ADbSqliteBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADbSqliteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = SqliteActivity::class.java.simpleName
        }
        databaseHandler = DatabaseHandler(this)
        binding.btAdd.setOnClickListener(this)
        binding.btClearAll.setOnClickListener(this)
        binding.btGetContactWithId.setOnClickListener(this)
        binding.btGetContactListPage1.setOnClickListener(this)
        binding.btAdd100.setOnClickListener(this)
        allContact
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btAdd -> addContact()
            binding.btAdd100 -> add100Contact()
            binding.btClearAll -> clearAllContact()
            binding.btGetContactWithId -> getContactWithId(2)
            binding.btGetContactListPage1 -> getContactListPage(1)
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
        binding.ll.addView(button)
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
                binding.ll.addView(button)
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
        binding.ll.removeAllViews()
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
        binding.ll.removeView(button)
    }

    @Suppress("unused")
    private fun getContactListPage(page: Int) {
        val contactList = databaseHandler?.getContactListWithPage(page, 5)
        showDialogMsg("getContactListPage page: $page ${gson.toJson(contactList)}")
    }
}
