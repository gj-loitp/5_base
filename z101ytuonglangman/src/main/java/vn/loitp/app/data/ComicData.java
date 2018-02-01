package vn.loitp.app.data;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.model.comic.Comic;
import vn.loitp.core.utilities.LLog;

/**
 * Created by www.muathu@gmail.com on 1/20/2018.
 */

public class ComicData {
    private static final String TAG = ComicData.class.getSimpleName();
    private static final ComicData ourInstance = new ComicData();

    public static ComicData getInstance() {
        return ourInstance;
    }

    private ComicData() {
    }

    public void clearAll() {
        comicList = null;
        comicFavList = null;
    }

    private List<Comic> comicList;

    public List<Comic> getComicList() {
        return comicList;
    }

    private List<Comic> comicListClone;

    public void filterComicListWithKeyword(String keyword) {
        LLog.d(TAG, "filterComicListWithKeyword " + keyword);
        if (keyword == null || keyword.isEmpty()) {
            LLog.d(TAG, ">>>if");
            comicList = comicListClone;
        } else {
            LLog.d(TAG, ">>>else");
            comicList = new ArrayList<>();
            LLog.d(TAG, "filterComicListWithKeyword comicList " + comicList.size());
            LLog.d(TAG, "filterComicListWithKeyword comicListClone " + comicListClone.size());

            for (int i = 0; i < comicListClone.size(); i++) {
                if (comicListClone.get(i).getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                    comicList.add(comicListClone.get(i));
                }
            }
        }
        LLog.d(TAG, "size after searching: " + comicList.size());
    }

    public void setComicList(List<Comic> comics) {
        this.comicList = comics;
        this.comicListClone = comics;
        LLog.d(TAG, "setComicList comicList " + comicList.size());
        LLog.d(TAG, "setComicList comicListClone " + comicListClone.size());
    }

    private List<Comic> comicFavList;
    private List<Comic> comicFavListClone;

    public List<Comic> getComicFavList() {
        return comicFavList;
    }

    public void setComicFavList(List<Comic> comics) {
        this.comicFavList = comics;
        this.comicFavListClone = comics;
    }

    public void filterFavComicListWithKeyword(String keyword) {
        LLog.d(TAG, "filterComicListWithKeyword " + keyword);
        if (keyword == null || keyword.isEmpty()) {
            LLog.d(TAG, ">>>if");
            comicFavList = comicFavListClone;
        } else {
            LLog.d(TAG, ">>>else");
            comicFavList = new ArrayList<>();
            LLog.d(TAG, "filterComicListWithKeyword comicFavList " + comicFavListClone.size());
            LLog.d(TAG, "filterComicListWithKeyword comicFavListClone " + comicFavListClone.size());

            for (int i = 0; i < comicFavListClone.size(); i++) {
                if (comicFavListClone.get(i).getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                    comicFavList.add(comicFavListClone.get(i));
                }
            }
        }
        LLog.d(TAG, "size after searching: " + comicFavList.size());
    }
}
