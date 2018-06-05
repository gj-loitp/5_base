package vn.loitp.app.activity.demo.firebase.database.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class DatabaseFirebaseMyPostsFragmentDatabaseFirebase extends DatabaseFirebasePostListFragment {

    public DatabaseFirebaseMyPostsFragmentDatabaseFirebase() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("user-posts")
                .child(getUid());
    }
}
