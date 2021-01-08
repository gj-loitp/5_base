package vn.loitp.app.activity.demo.ebookwithrealm.realm;

import android.app.Activity;
import android.app.Application;

import androidx.fragment.app.Fragment;

import io.realm.Realm;
import io.realm.RealmResults;
import vn.loitp.app.activity.demo.ebookwithrealm.model.Book;

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    private RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(final Fragment fragment) {
        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(final Activity activity) {
        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(final Application application) {
        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    //Refresh the realm istance
    public void refresh() {
        realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {
        realm.beginTransaction();
        realm.clear(Book.class);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<Book> getBooks() {
        return realm.where(Book.class).findAll();
    }

    //query a single item with the given id
    public Book getBook(String id) {
        return realm.where(Book.class).equalTo("id", id).findFirst();
    }

    //check if Book.class is empty
    public boolean hasBooks() {
        return !realm.allObjects(Book.class).isEmpty();
    }

    //query example
    public RealmResults<Book> queryedBooks() {
        return realm.where(Book.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
}
