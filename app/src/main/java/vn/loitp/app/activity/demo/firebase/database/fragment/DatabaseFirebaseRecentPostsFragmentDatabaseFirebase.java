package vn.loitp.app.activity.demo.firebase.database.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class DatabaseFirebaseRecentPostsFragmentDatabaseFirebase extends DatabaseFirebasePostListFragment {

    public DatabaseFirebaseRecentPostsFragmentDatabaseFirebase() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        // [END recent_posts_query]

        return databaseReference.child("posts")
                .limitToFirst(100);
    }
}
