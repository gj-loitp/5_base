package vn.loitp.app.data;

import java.util.List;

import vn.loitp.app.model.comic.Comic;

/**
 * Created by www.muathu@gmail.com on 1/20/2018.
 */

public class ComicData {
    private static final ComicData ourInstance = new ComicData();

    public static ComicData getInstance() {
        return ourInstance;
    }

    private ComicData() {
    }

    private List<Comic> comicList;

    public List<Comic> getComicList() {
        return comicList;
    }

    public void setComicList(List<Comic> comicList) {
        this.comicList = comicList;
    }

    private List<Comic> comicFavList;

    public List<Comic> getComicFavList() {
        return comicFavList;
    }

    public void setComicFavList(List<Comic> comicFavList) {
        this.comicFavList = comicFavList;
    }
}
