package vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;

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
