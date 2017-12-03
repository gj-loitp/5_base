package vn.loitp.app.activity.api.truyentranhtuan.helper.favlist;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic;
import vn.loitp.app.app.LSApplication;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

/**
 * Created by www.muathu@gmail.com on 11/2/2017.
 */

public class GetFavListTask extends AsyncTask<Void, Void, Void> {
    private final String TAG = getClass().getSimpleName();

    private Activity mActivity;
    private AVLoadingIndicatorView avi;

    private List<Comic> comicList = new ArrayList<>();

    public interface Callback {
        public void onSuccess(List<Comic> comicList);
    }

    private Callback callback;

    public GetFavListTask(Activity activity, AVLoadingIndicatorView avi, Callback callback) {
        this.mActivity = activity;
        this.avi = avi;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        avi.smoothToShow();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        LLog.d(TAG, "doInBackground");
        String json = LStoreUtil.readTxtFromFolder(mActivity, LStoreUtil.FOLDER_TRUYENTRANHTUAN, LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_FAVOURITE);
        if (json == null || json.isEmpty()) {
            LLog.d(TAG, "json == null || json.isEmpty()");
        } else {
            comicList = LSApplication.getInstance().getGson().fromJson(json, new TypeToken<List<Comic>>() {
            }.getType());
        }
        LLog.d(TAG, "comicList size: " + comicList);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        avi.smoothToHide();
        if (callback != null) {
            callback.onSuccess(comicList);
        }
        super.onPostExecute(aVoid);
    }
}
