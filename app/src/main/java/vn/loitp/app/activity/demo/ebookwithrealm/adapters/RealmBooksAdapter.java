package vn.loitp.app.activity.demo.ebookwithrealm.adapters;

import android.content.Context;

import io.realm.RealmResults;
import vn.loitp.app.activity.demo.ebookwithrealm.model.Book;

public class RealmBooksAdapter extends RealmModelAdapter<Book> {

    public RealmBooksAdapter(Context context, RealmResults<Book> realmResults, boolean automaticUpdate) {

        super(context, realmResults, automaticUpdate);
    }
}