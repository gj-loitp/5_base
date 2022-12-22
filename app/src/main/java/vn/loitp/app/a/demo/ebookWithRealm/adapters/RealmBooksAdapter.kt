package vn.loitp.app.a.demo.ebookWithRealm.adapters

import android.content.Context
import io.realm.RealmResults
import vn.loitp.app.a.demo.ebookWithRealm.model.Book

class RealmBooksAdapter(
    context: Context,
    realmResults: RealmResults<Book>,
    automaticUpdate: Boolean
) : RealmModelAdapter<Book>(
    context,
    realmResults,
    automaticUpdate
)
