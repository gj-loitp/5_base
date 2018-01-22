package vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerviewyayandroid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www.muathu@gmail.com on 1/22/2018.
 */

public class FakeData {
    private static final FakeData ourInstance = new FakeData();

    public static FakeData getInstance() {
        return ourInstance;
    }

    private FakeData() {
    }

    private List<String> stringList = new ArrayList<>();

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }
}
