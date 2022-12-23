package vn.loitp.app.a.demo.ebookWithRealm.adt;

import androidx.recyclerview.widget.RecyclerView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;

//8.1.2021 tried to convert kotlin
public abstract class RealmRecyclerViewAdapter<T extends RealmObject> extends RecyclerView.Adapter {

    private RealmBaseAdapter<T> realmBaseAdapter;

    public T getItem(int position) {
        return realmBaseAdapter.getItem(position);
    }

    RealmBaseAdapter<T> getRealmAdapter() {
        return realmBaseAdapter;
    }

    public void setRealmAdapter(RealmBaseAdapter<T> realmAdapter) {
        realmBaseAdapter = realmAdapter;
    }
}
