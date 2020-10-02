package vn.loitp.app.activity.database.sqlite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.annotation.IsFullScreen;
import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseApplication;
import com.core.base.BaseFontActivity;

import java.util.List;

import vn.loitp.app.R;

@LayoutId(R.layout.activity_sqlite)
@LogTag("SqliteActivity")
@IsFullScreen(false)
public class SqliteActivity extends BaseFontActivity implements View.OnClickListener {
    private DatabaseHandler db;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ll = findViewById(R.id.ll);

        db = new DatabaseHandler(this);

        findViewById(R.id.btAdd).setOnClickListener(this);
        findViewById(R.id.bt_clear_all).setOnClickListener(this);
        findViewById(R.id.bt_getcontact_with_id).setOnClickListener(this);
        findViewById(R.id.btGetContactListPage1).setOnClickListener(this);
        findViewById(R.id.btAdd100).setOnClickListener(this);

        getAllContact();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btAdd:
                addContact();
                break;
            case R.id.btAdd100:
                add100Contact();
                break;
            case R.id.bt_clear_all:
                clearAllContact();
                break;
            case R.id.bt_getcontact_with_id:
                getContactWithId(2);
                break;
            case R.id.btGetContactListPage1:
                getContactListPage(1);
                break;
        }
    }

    private void getAllContact() {
        List<Contact> contactList = db.getAllContacts();
        for (Contact contact : contactList) {
            addButton(contact);
        }
    }

    @SuppressLint("SetTextI18n")
    private void addButton(Contact contact) {
        Button button = new Button(this);
        button.setText(contact.getId() + " " + contact.getName());
        button.setOnClickListener(v -> {
            updateContact(contact, button);
        });
        button.setOnLongClickListener(v -> {
            deleteContact(contact, button);
            return true;
        });
        ll.addView(button);
    }

    @SuppressLint("SetTextI18n")
    private void addButton() {
        Button button = new Button(this);
        Contact contact = db.getContact(db.getContactsCount());
        if (contact != null) {
            button.setText(contact.getId() + " - " + contact.getName());
            button.setOnClickListener(v -> {
                updateContact(contact, button);
            });
            button.setOnLongClickListener(v -> {
                deleteContact(contact, button);
                return true;
            });
            ll.addView(button);
        }
    }

    private void addContact() {
        int size = db.getContactsCount();
        Contact contact = new Contact();
        contact.setName("name " + (size + 1));
        contact.setPhoneNumber("phone: " + (size + 1));
        db.addContact(contact);
        addButton();
    }

    private void add100Contact() {
        for (int i = 0; i < 100; i++) {
            int size = db.getContactsCount();
            Contact contact = new Contact();
            contact.setName("name " + (size + 1));
            contact.setPhoneNumber("phone: " + (size + 1));
            db.addContact(contact);
            addButton();
        }
    }

    private void clearAllContact() {
        ll.removeAllViews();
        db.clearAllContact();
        getAllContact();
    }

    private void getContactWithId(int id) {
        Contact contact = db.getContact(id);
        if (contact == null) {
            showShort("Contact with ID=" + id + " not found", true);
        } else {
            showShort("Found: " + contact.getId() + " " + contact.getName() + " " + contact.getPhoneNumber(), true);
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateContact(Contact contact, Button button) {
        contact.setName("Updated " + contact.getName());
        int result = db.updateContact(contact);
        button.setText(contact.getId() + " " + contact.getName());
    }

    private void deleteContact(Contact contact, Button button) {
        int result = db.deleteContact(contact);
        ll.removeView(button);
    }

    private void getContactListPage(int page) {
        int PAGE_SIZE = 5;
        List<Contact> contactList = db.getContactListWithPage(page, PAGE_SIZE);
        showDialogMsg("getContactListPage page: " + page + "\n" + BaseApplication.Companion.getGson().toJson(contactList), null);
    }
}
