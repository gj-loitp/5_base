package vn.loitp.app.activity.api.truyentranhtuan.helper.favlist;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.activity.api.truyentranhtuan.helper.ComicUtils;
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic;
import vn.loitp.app.app.LSApplication;
import vn.loitp.app.common.Constants;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LStoreUtil;

/**
 * Created by www.muathu@gmail.com on 11/2/2017.
 */

public class RemoveComicFavListTask extends AsyncTask<Void, Void, Void> {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity;
    private Comic mComic;
    private List<Comic> comicList = new ArrayList<>();

    private int mResult;
    private final int RESULT_REMOVE_COMIC_SUCCESS = 69;
    private final int RESULT_COMIC_IS_NOT_EXIST = 96;
    private final int RESULT_ADD_COMIC_ERROR = 66;

    public interface Callback {
        public void onRemoveComicSuccess(Comic mComic, List<Comic> comicList);

        public void onComicIsNotExist(Comic mComic, List<Comic> comicList);

        public void onRemoveComicError();
    }

    private Callback callback;

    public RemoveComicFavListTask(Activity activity, Comic comic, Callback callback) {
        this.mActivity = activity;
        this.mComic = comic;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String json = LStoreUtil.readTxtFromFolder(mActivity, LStoreUtil.FOLDER_TRUYENTRANHTUAN, LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_FAVOURITE);

        if (json == null || json.isEmpty()) {
            LLog.d(TAG, "json == null || json.isEmpty()");
            mResult = RESULT_COMIC_IS_NOT_EXIST;
        } else {
            comicList = LSApplication.getInstance().getGson().fromJson(json, new TypeToken<List<Comic>>() {
            }.getType());
            LLog.d(TAG, "comicList size: " + comicList.size());
            int pos = ComicUtils.isComicExistAt(mComic, comicList);
            LLog.d(TAG, "pos " + pos);
            if (pos != Constants.NOT_FOUND) {
                comicList.remove(pos);

                String newJson = LSApplication.getInstance().getGson().toJson(comicList);
                boolean isSaved = LStoreUtil.writeToFile(mActivity, LStoreUtil.FOLDER_TRUYENTRANHTUAN, LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_FAVOURITE, newJson);
                if (isSaved) {
                    mResult = RESULT_REMOVE_COMIC_SUCCESS;
                } else {
                    mResult = RESULT_ADD_COMIC_ERROR;
                }
            } else {
                mResult = RESULT_COMIC_IS_NOT_EXIST;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (mResult == RESULT_COMIC_IS_NOT_EXIST) {
                callback.onComicIsNotExist(mComic, comicList);
            } else if (mResult == RESULT_ADD_COMIC_ERROR) {
                callback.onRemoveComicError();
            } else if (mResult == RESULT_REMOVE_COMIC_SUCCESS) {
                callback.onRemoveComicSuccess(mComic, comicList);
            }
        }
        super.onPostExecute(aVoid);
    }


}
