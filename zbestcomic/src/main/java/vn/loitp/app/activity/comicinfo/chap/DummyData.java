package vn.loitp.app.activity.comicinfo.chap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www.muathu@gmail.com on 1/22/2018.
 */

public class DummyData {
    private static final DummyData ourInstance = new DummyData();

    public static DummyData getInstance() {
        return ourInstance;
    }

    private DummyData() {
    }

    private List<Movie> movieList = new ArrayList<>();

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
