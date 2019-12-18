package vn.loitp.app.activity.api.truyentranhtuan.helper.favlist;

import android.app.Activity;
import android.os.AsyncTask;

import com.core.utilities.LLog;
import com.core.utilities.LStoreUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.activity.api.truyentranhtuan.helper.ComicUtils;
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic;
import vn.loitp.app.app.LApplication;

/**
 * Created by www.muathu@gmail.com on 11/2/2017.
 */

//TODO convert rx
public class AddComicFavListTask extends AsyncTask<Void, Void, Void> {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity;
    private Comic mComic;
    private List<Comic> comicList = new ArrayList<>();

    private int mResult;
    private final int RESULT_ADD_COMIC_SUCCESS = 69;
    private final int RESULT_COMIC_IS_EXIST = 96;
    private final int RESULT_ADD_COMIC_ERROR = 66;

    public interface Callback {
        void onAddComicSuccess(Comic mComic, List<Comic> comicList);

        void onComicIsExist(Comic mComic, List<Comic> comicList);

        void onAddComicError();
    }

    private Callback callback;

    public AddComicFavListTask(Activity activity, Comic comic, Callback callback) {
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
        LLog.d(TAG, "doInBackground");
        String json = LStoreUtil.INSTANCE.readTxtFromFolder(mActivity, LStoreUtil.INSTANCE.getFOLDER_TRUYENTRANHTUAN(), LStoreUtil.INSTANCE.getFILE_NAME_MAIN_COMICS_LIST_FAVOURITE());

        if (json == null || json.isEmpty()) {
            LLog.d(TAG, "json == null || json.isEmpty()");
            comicList.add(mComic);

            String newJson = LApplication.Companion.getGson().toJson(comicList);
            boolean isSaved = LStoreUtil.INSTANCE.writeToFile(mActivity, LStoreUtil.INSTANCE.getFOLDER_TRUYENTRANHTUAN(), LStoreUtil.INSTANCE.getFILE_NAME_MAIN_COMICS_LIST_FAVOURITE(), newJson);
            if (isSaved) {
                mResult = RESULT_ADD_COMIC_SUCCESS;
            } else {
                mResult = RESULT_ADD_COMIC_ERROR;
            }
        } else {
            comicList = LApplication.Companion.getGson().fromJson(json, new TypeToken<List<Comic>>() {
            }.getType());
            LLog.d(TAG, "comicList size: " + comicList);
            boolean isExist = ComicUtils.isComicExistIn(mComic, comicList);
            if (!isExist) {
                comicList.add(mComic);

                String newJson = LApplication.Companion.getGson().toJson(comicList);
                boolean isSaved = LStoreUtil.INSTANCE.writeToFile(mActivity, LStoreUtil.INSTANCE.getFOLDER_TRUYENTRANHTUAN(), LStoreUtil.INSTANCE.getFILE_NAME_MAIN_COMICS_LIST_FAVOURITE(), newJson);
                if (isSaved) {
                    mResult = RESULT_ADD_COMIC_SUCCESS;
                } else {
                    mResult = RESULT_ADD_COMIC_ERROR;
                }
            } else {
                mResult = RESULT_COMIC_IS_EXIST;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (mResult == RESULT_COMIC_IS_EXIST) {
                callback.onComicIsExist(mComic, comicList);
            } else if (mResult == RESULT_ADD_COMIC_ERROR) {
                callback.onAddComicError();
            } else if (mResult == RESULT_ADD_COMIC_SUCCESS) {
                callback.onAddComicSuccess(mComic, comicList);
            }
        }
        super.onPostExecute(aVoid);
    }
}
