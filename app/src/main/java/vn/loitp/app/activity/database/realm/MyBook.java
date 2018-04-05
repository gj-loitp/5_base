package vn.loitp.app.activity.database.realm;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by www.muathu@gmail.com on 1/18/2018.
 */

public class MyBook extends RealmObject {
    @PrimaryKey
    private long id;
    private String title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

}