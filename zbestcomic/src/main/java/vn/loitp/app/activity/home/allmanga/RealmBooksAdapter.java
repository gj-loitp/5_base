package vn.loitp.app.activity.home.allmanga;

import android.content.Context;

import io.realm.RealmResults;
import vn.loitp.app.activity.home.allmanga.model.Book;

public class RealmBooksAdapter extends RealmModelAdapter<Book> {

    public RealmBooksAdapter(Context context, RealmResults<Book> realmResults, boolean automaticUpdate) {

        super(context, realmResults, automaticUpdate);
    }
}