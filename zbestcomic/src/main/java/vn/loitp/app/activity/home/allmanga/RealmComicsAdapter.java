package vn.loitp.app.activity.home.allmanga;

import android.content.Context;

import io.realm.RealmResults;
import vn.loitp.app.model.comic.Comic;

public class RealmComicsAdapter extends RealmModelAdapter<Comic> {

    public RealmComicsAdapter(Context context, RealmResults<Comic> realmResults, boolean automaticUpdate) {

        super(context, realmResults, automaticUpdate);
    }
}